package com.StudentLibrary.Studentlibrary.Services;


import com.StudentLibrary.Studentlibrary.DTO.HistoryDTO;
import com.StudentLibrary.Studentlibrary.Model.*;
import org.springframework.beans.factory.annotation.Value;
import com.StudentLibrary.Studentlibrary.DTO.NearestDueBookDTO;
import com.StudentLibrary.Studentlibrary.DTO.DashboardResponse;
import com.StudentLibrary.Studentlibrary.Repositories.AuthorRepository;
import com.StudentLibrary.Studentlibrary.Repositories.BookRepository;
import com.StudentLibrary.Studentlibrary.Repositories.PaymentRepository;
import com.StudentLibrary.Studentlibrary.Repositories.StudentRepository;
import com.StudentLibrary.Studentlibrary.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.StudentLibrary.Studentlibrary.DTO.StudentDashboardResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.StudentLibrary.Studentlibrary.Repositories.UserRepository;
import java.util.List;
import java.util.ArrayList;

import com.StudentLibrary.Studentlibrary.DTO.ActiveBookDTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Service
public class DashboardService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PaymentRepository paymentRepository;


    @Value("${books.fine.per_day}")
    private int finePerDay;

    public DashboardResponse getDashboard() {

        DashboardResponse response = new DashboardResponse();

        response.setTotalBooks(bookRepository.count());

        response.setIssuedBooks(bookRepository.countIssuedBooks());

        response.setTotalStudents(studentRepository.count());

        response.setTotalAuthors(authorRepository.count());

        response.setTotalTransactions(transactionRepository.getTransactionCount());

        response.setTotalPayments(paymentRepository.count());

        response.setPaidPayments(
                paymentRepository.countByStatus(PaymentStatus.PAID)
        );

        response.setPendingPayments(
                paymentRepository.countByStatus(PaymentStatus.PENDING)
        );

        Double revenue = paymentRepository.getTotalRevenue();

        response.setTotalRevenue(revenue == null ? 0.0 : revenue);

        return response;
    }
    public StudentDashboardResponse getStudentDashboard() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        StudentDashboardResponse response = new StudentDashboardResponse();

        response.setUsername(username);

// Step 1: Logged-in user fetch karo



       // System.out.println("JWT Username = [" + username + "]");


        User user = userRepository.findByUsername(username);


        //System.out.println("User Object = " + user);

       // System.out.println("User = " + user);


        if (user == null) {
            throw new RuntimeException("User not found");
        }

// Step 2: User ke email se Student fetch karo
        Student student = studentRepository.findByEmailId(user.getEmail());

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

// Step 3: Student ka Card
        Card card = student.getCard();

        if (card == null) {
            throw new RuntimeException("Card not found for student: " + student.getName());
        }

        List<Transaction> transactions =
                transactionRepository.findByCardId(card.getId());
        List<ActiveBookDTO> activeBooksList = new ArrayList<>();
        List<HistoryDTO> historyList = new ArrayList<>();



        int totalBooksIssued = 0;
        int activeBooks = 0;
        int pendingFine = 0;
        int overdueBooks = 0;

        for (Transaction t : transactions) {

            // 1. Total books ever successfully issued
            if (Boolean.TRUE.equals(t.getIssueOperation())
                    && t.getTransactionStatus() == TransactionStatus.SUCCESSFUL) {

                totalBooksIssued++;
            }
            // 2. Returned but unpaid fine
            if (Boolean.TRUE.equals(t.getIssueOperation())
                    && t.getTransactionStatus() == TransactionStatus.SUCCESSFUL
                    && t.getReturnDate() != null
                    && !Boolean.TRUE.equals(t.getFinePaid())
                    && t.getFineAmount() > 0) {

                pendingFine += t.getFineAmount();
            }

            // 3. Currently active books
            if (Boolean.TRUE.equals(t.getIssueOperation())
                    && t.getTransactionStatus() == TransactionStatus.SUCCESSFUL
                    && t.getReturnDate() == null) {

                activeBooks++;



                ActiveBookDTO dto = new ActiveBookDTO();

                dto.setBookId(t.getBook().getId());
                dto.setBookName(t.getBook().getName());



                LocalDate issueDate = t.getTransactionDate()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                dto.setIssueDate(issueDate);
                dto.setDueDate(t.getDueDate());

                long daysLeft = 0;
                int fine = 0;

                if (t.getDueDate() != null) {
                    daysLeft = ChronoUnit.DAYS.between(
                            LocalDate.now(),
                            t.getDueDate()
                    );

                    if (daysLeft < 0) {

                        long overdueDays = Math.abs(daysLeft);

                        fine = (int) overdueDays * finePerDay;


                        overdueBooks++;
                        pendingFine += fine;

                        dto.setStatus("OVERDUE");

                    } else {

                        dto.setStatus("ACTIVE");
                    }

                }

                dto.setDaysLeft(daysLeft);
                dto.setFine(fine);

                dto.setStatus(daysLeft < 0 ? "OVERDUE" : "ACTIVE");


                activeBooksList.add(dto);
            }



        }
        for (Transaction t : transactions) {

            if (t.getTransactionStatus() != TransactionStatus.SUCCESSFUL) {
                continue;
            }
            if (!Boolean.TRUE.equals(t.getIssueOperation())) {
                continue;
            }

            if (t.getBook() == null) {
                continue;
            }


            HistoryDTO history = new HistoryDTO();

            history.setTransactionId(t.getId());

            history.setBookId(t.getBook().getId());
            history.setBookName(t.getBook().getName());

            Payment paidPayment =
                    paymentRepository
                            .findTopByTransactionIdAndStatusOrderByIdDesc(
                                    t.getId(),
                                    PaymentStatus.PAID
                            );

            if (paidPayment != null) {
                history.setPaymentId(paidPayment.getId());
            }

            if (t.getTransactionDate() != null) {

                LocalDate issueDate = t.getTransactionDate()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                history.setIssueDate(issueDate);
            }

            history.setReturnDate(t.getReturnDate());

            history.setFineAmount(t.getFineAmount());

            history.setFinePaid(
                    Boolean.TRUE.equals(t.getFinePaid())
            );

            if (t.getFineAmount() <= 0) {
                history.setPaymentStatus("NOT_REQUIRED");
            } else if (Boolean.TRUE.equals(t.getFinePaid())) {
                history.setPaymentStatus("PAID");
            } else {
                history.setPaymentStatus("PENDING");
            }

            if (Boolean.TRUE.equals(t.getIssueOperation())) {

                if (t.getReturnDate() != null) {
                    history.setStatus("RETURNED");
                } else if (t.getDueDate() != null
                        && LocalDate.now().isAfter(t.getDueDate())) {
                    history.setStatus("OVERDUE");
                } else {
                    history.setStatus("ISSUED");
                }

            } else {

                history.setStatus("RETURNED");
            }

            historyList.add(history);
        }



        response.setPendingFine(pendingFine);
        response.setOverdueBooks(overdueBooks);
        response.setTotalBooksIssued(totalBooksIssued);
        response.setActiveBooks(activeBooks);
        response.setActiveBooksList(activeBooksList);

        NearestDueBookDTO nearestDueBook = null;

        for (ActiveBookDTO book : activeBooksList) {

            if (book.getDueDate() == null) {
                continue;
            }

            if (nearestDueBook == null ||
                    book.getDueDate().isBefore(nearestDueBook.getDueDate())) {

                nearestDueBook = new NearestDueBookDTO();

                nearestDueBook.setBookName(book.getBookName());
                nearestDueBook.setIssueDate(book.getIssueDate());
                nearestDueBook.setDueDate(book.getDueDate());
                nearestDueBook.setDaysLeft(book.getDaysLeft());
                nearestDueBook.setFine(book.getFine());
            }
        }


// Nearest Due Book ka existing code yahin rahega

        response.setNearestDueBook(nearestDueBook);

        response.setHistory(historyList);

        return response;
    }
}
