package com.StudentLibrary.Studentlibrary.Services;

import com.StudentLibrary.Studentlibrary.DTO.PaymentVerifyRequest;
import com.razorpay.Utils;
import com.StudentLibrary.Studentlibrary.Model.Payment;
import com.StudentLibrary.Studentlibrary.Model.PaymentStatus;
import com.StudentLibrary.Studentlibrary.Repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.StudentLibrary.Studentlibrary.DTO.PaymentStartRequest;
import com.StudentLibrary.Studentlibrary.DTO.PaymentStartResponse;
import com.razorpay.Order;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import com.StudentLibrary.Studentlibrary.Model.Book;
import com.StudentLibrary.Studentlibrary.Repositories.BookRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.StudentLibrary.Studentlibrary.Services.EmailService;
import com.StudentLibrary.Studentlibrary.Model.Transaction;
import com.StudentLibrary.Studentlibrary.Model.TransactionStatus;
import com.StudentLibrary.Studentlibrary.Repositories.TransactionRepository;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RazorpayClient razorpayClient;

    @Value("${razorpay.key.id}")
    private String razorpayKey;

    @Value("${razorpay.key.secret}")
    private String razorpaySecret;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private EmailService emailService;



    public Payment createPayment(String username,
                                 Integer bookId,
                                 Double amount) {


        Payment payment = new Payment();


        payment.setUsername(username);
        payment.setBookId(bookId);

        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    public List<Payment> getMyPayments(String username) {
        return paymentRepository.findByUsername(username);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }



        //
    public String createRazorpayOrder(Double amount) throws Exception {

        JSONObject options = new JSONObject();

        options.put("amount", (int)(amount * 100)); // paise
        options.put("currency", "INR");
        options.put("receipt", "receipt_" + System.currentTimeMillis());

        Order order = razorpayClient.orders.create(options);

        return order.toString();
    }
    public PaymentStartResponse startPayment(
            String username,
            PaymentStartRequest request) throws Exception {

        // Step 1: Transaction ID required hai
        if (request.getTransactionId() == null) {
            throw new RuntimeException("Transaction ID is required");
        }

        // Step 2: Fine wali transaction database se fetch karo
        Transaction transaction = transactionRepository
                .findById(request.getTransactionId())
                .orElseThrow(() ->
                        new RuntimeException("Transaction not found"));

        // Step 3: Sirf successful ISSUE transaction ka fine pay hoga
        if (!Boolean.TRUE.equals(transaction.getIssueOperation())
                || transaction.getTransactionStatus()
                != TransactionStatus.SUCCESSFUL) {

            throw new RuntimeException(
                    "Invalid transaction for fine payment"
            );
        }

        // Step 4: Check fine actually pending hai ya nahi
        if (transaction.getFineAmount() <= 0) {
            throw new RuntimeException(
                    "No fine is pending for this transaction"
            );
        }

        // Step 5: Already paid fine ko dobara pay mat hone do
        if (Boolean.TRUE.equals(transaction.getFinePaid())) {
            throw new RuntimeException(
                    "Fine has already been paid"
            );
        }

        // Step 6: Backend se actual amount lo
        double actualFineAmount = transaction.getFineAmount();

        // Step 7: Payment object create karo
        Payment payment = new Payment();

        payment.setUsername(username);
        payment.setBookId(transaction.getBook().getId());
        payment.setTransactionId(transaction.getId());
        payment.setAmount(actualFineAmount);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());

        payment = paymentRepository.save(payment);

        // Step 8: Razorpay order create karo
        JSONObject options = new JSONObject();

        options.put(
                "amount",
                (int) (actualFineAmount * 100)
        );

        options.put("currency", "INR");
        options.put("receipt", "receipt_" + payment.getId());

        Order order = razorpayClient.orders.create(options);

        // Step 9: Razorpay Order ID database me save karo
        payment.setRazorpayOrderId(order.get("id"));

        paymentRepository.save(payment);

        // Step 10: Frontend ko response return karo
        return new PaymentStartResponse(
                payment.getId(),
                order.get("id"),
                actualFineAmount * 100,
                "INR",
                razorpayKey
        );
    }

    public byte[] generateReceipt(Integer paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment Not Found"));

        Book book = bookRepository.findById(payment.getBookId())
                .orElseThrow(() -> new RuntimeException("Book Not Found"));

        return pdfService.generateReceipt(payment, book);
    }
    public Payment verifyPayment(PaymentVerifyRequest request) throws Exception {

        // Step 1: Payment database se fetch karo
        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() ->
                        new RuntimeException("Payment not found")
                );

        // Step 2: Already paid payment ko dobara process mat karo
        if (payment.getStatus() == PaymentStatus.PAID) {
            throw new RuntimeException("Payment is already completed");
        }

        // Step 3: Request ka Order ID aur database ka Order ID match karo
        if (payment.getRazorpayOrderId() == null ||
                !payment.getRazorpayOrderId()
                        .equals(request.getRazorpayOrderId())) {

            throw new RuntimeException("Invalid Razorpay Order ID");
        }

        // Step 4: Razorpay signature verify karne ke liye attributes banao
        JSONObject attributes = new JSONObject();

        attributes.put(
                "razorpay_order_id",
                request.getRazorpayOrderId()
        );

        attributes.put(
                "razorpay_payment_id",
                request.getRazorpayPaymentId()
        );

        attributes.put(
                "razorpay_signature",
                request.getRazorpaySignature()
        );

        // Step 5: Razorpay signature verify karo
        boolean isValid = Utils.verifyPaymentSignature(
                attributes,
                razorpaySecret
        );

        if (!isValid) {
            throw new RuntimeException(
                    "Payment signature verification failed"
            );
        }

        // Step 6: Exact fine transaction fetch karo
        if (payment.getTransactionId() == null) {
            throw new RuntimeException(
                    "Transaction ID not found for payment"
            );
        }

        Transaction transaction = transactionRepository
                .findById(payment.getTransactionId())
                .orElseThrow(() ->
                        new RuntimeException("Transaction not found")
                );

        // Step 7: Fine ko PAID mark karo
        transaction.setFinePaid(true);
        transactionRepository.save(transaction);

        // Step 8: Payment ko PAID mark karo
        payment.setStatus(PaymentStatus.PAID);

        payment.setRazorpayPaymentId(
                request.getRazorpayPaymentId()
        );

        payment = paymentRepository.save(payment);

// Step 9: Student ka actual email fetch karo
        String studentEmail = transaction
                .getCard()
                .getStudent()
                .getEmailId();

// Step 10: Payment confirmation email send karo
        emailService.sendSimpleMail(
                studentEmail,
                "Library Fine Payment Successful",
                "Hello " + transaction.getCard().getStudent().getName()
                        + ",\n\nYour library fine payment was successful."
                        + "\n\nPayment ID: " + payment.getId()
                        + "\nAmount: ₹" + payment.getAmount()
                        + "\nBook ID: " + payment.getBookId()
                        + "\nTransaction ID: " + payment.getTransactionId()
                        + "\nRazorpay Payment ID: " + payment.getRazorpayPaymentId()
                        + "\nStatus: " + payment.getStatus()
                        + "\n\nThank you."
        );

        return payment;
    }


}
