package com.StudentLibrary.Studentlibrary.Controllers;

import com.StudentLibrary.Studentlibrary.DTO.PaymentRequest;
import com.StudentLibrary.Studentlibrary.Model.Payment;
import com.StudentLibrary.Studentlibrary.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.StudentLibrary.Studentlibrary.DTO.PaymentStartRequest;
import com.StudentLibrary.Studentlibrary.DTO.PaymentStartResponse;
import org.springframework.security.core.Authentication;
import com.StudentLibrary.Studentlibrary.DTO.PaymentVerifyRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;


@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public Payment createPayment(@RequestBody PaymentRequest request,
                                 Authentication authentication) {

        if (authentication == null) {
            throw new RuntimeException("Authentication is NULL");
        }
        String username = authentication.getName();


        return paymentService.createPayment(
                username,
                request.getBookId(),
                request.getAmount()
        );
    }

    @GetMapping("/my")
    public List<Payment> myPayments(Authentication authentication) {

        return paymentService.getMyPayments(authentication.getName());

    }

    @GetMapping("/all")
    public List<Payment> allPayments() {

        return paymentService.getAllPayments();

    }

//    @PutMapping("/pay/{id}")
//    public Payment pay(@PathVariable Integer id,
//                       @RequestParam String paymentId) {
//
//        return paymentService.markAsPaid(id, paymentId);
//
//     }




    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody PaymentRequest request) {

        try {
            System.out.println("====== ORDER API HIT ======");
            System.out.println("Amount = " + request.getAmount());

            String result = paymentService.createRazorpayOrder(request.getAmount());

            return ResponseEntity.ok(result);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getClass().getName() + " : " + e.getMessage());
        }
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(
            @RequestBody PaymentVerifyRequest request) {

        try {

            Payment payment = paymentService.verifyPayment(request);

            return ResponseEntity.ok(payment);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @PostMapping("/start")
    public PaymentStartResponse startPayment(
            @RequestBody PaymentStartRequest request,
            Authentication authentication) throws Exception {

        String username = authentication.getName();

        return paymentService.startPayment(username, request);
    }


    @GetMapping("/{paymentId}/receipt")
    public ResponseEntity<byte[]> downloadReceipt(
            @PathVariable Integer paymentId) {

        try {
            byte[] pdf = paymentService.generateReceipt(paymentId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=LibraryReceipt.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }




    }

//        byte[] pdf = paymentService.generateReceipt(paymentId);
//
//        return ResponseEntity.ok()
//
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=LibraryReceipt.pdf")
//
//                .contentType(MediaType.APPLICATION_PDF)
//
//                .body(pdf);
//    }


}






