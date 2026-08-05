package com.StudentLibrary.Studentlibrary.Services;

import com.StudentLibrary.Studentlibrary.Model.*;
import com.StudentLibrary.Studentlibrary.Repositories.BookRepository;
import com.StudentLibrary.Studentlibrary.Repositories.CardRepository;
import com.StudentLibrary.Studentlibrary.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.StudentLibrary.Studentlibrary.DTO.TransactionResponseDto;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CardRepository cardRepository;
    @Value("${books.max_allowed}")
    int max_allowed_books;
    @Value("${books.max_allowed_days}")
    int max_days_allowed;
    @Value("${books.fine.per_day}")
    int fine_per_day;


    public String issueBooks(int cardId,int bookId) throws Exception {
        try {
            Book book = bookRepository.findById(bookId).get();
            System.out.println(book);

            if (book == null || book.isAvailable() != true) {
                throw new Exception("Book is either unavailable or not present!!");
            }
            Card card = cardRepository.findById(cardId).get();
            System.out.println(card);
            if (card == null || card.getCardStatus() == CardStatus.DEACTIVATED) {
                throw new Exception("Card is invalid!!");
            }
            if (card.getBooks().size() > max_allowed_books) {
                throw new Exception("Book limit reached for this card!!");
            }
            book.setAvailable(false);
            book.setCard(card);
            List<Book> books = card.getBooks();
            books.add(book);
            card.setBooks(books);
            bookRepository.updateBook(book);
            Transaction transaction = new Transaction();

            transaction.setCard(card);
            transaction.setBook(book);
            transaction.setIssueOperation(true);
            transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);

            transaction.setDueDate(LocalDate.now().plusDays(max_days_allowed));
            transaction.setFinePaid(false);

            transactionRepository.save(transaction);
            return transaction.getTransactionId();
        } catch (Exception e) {
            e.printStackTrace();   // 👈 ye line rehne do

            throw e;
        }
    }
    public String returnBooks(int cardId, int bookId) throws Exception {

        // Step 1: Successful issue transaction find karo
        List<Transaction> transactions =
                transactionRepository.findByCard_Book(
                        cardId,
                        bookId,
                        TransactionStatus.SUCCESSFUL,
                        true
                );

        if (transactions == null || transactions.isEmpty()) {
            throw new Exception("No successful issue transaction found for this book and card!");
        }

        Transaction lastIssueTransaction =
                transactions.get(transactions.size() - 1);

        // Step 2: Card aur Book fetch karo
        Card card = lastIssueTransaction.getCard();
        Book book = lastIssueTransaction.getBook();

        if (card == null) {
            throw new Exception("Card not found!");
        }

        if (book == null) {
            throw new Exception("Book not found!");
        }

        // Step 3: Check karo book actually isi card ko issued hai
        if (book.getCard() == null ||
                book.getCard().getId() != cardId) {

            throw new Exception("This book is not currently issued to this card!");
        }

        // Step 4: Due date ke basis par fine calculate karo
        LocalDate returnDate = LocalDate.now();
        LocalDate dueDate = lastIssueTransaction.getDueDate();

        int fine = 0;

        if (dueDate != null && returnDate.isAfter(dueDate)) {

            long overdueDays =
                    java.time.temporal.ChronoUnit.DAYS.between(
                            dueDate,
                            returnDate
                    );

            fine = (int) overdueDays * fine_per_day;
        }

        // Step 5: Original issue transaction ko update karo
        lastIssueTransaction.setReturnDate(returnDate);
        lastIssueTransaction.setFineAmount(fine);
        lastIssueTransaction.setFinePaid(fine == 0);

        transactionRepository.save(lastIssueTransaction);

        // Step 6: Book ko available karo
        book.setCard(null);
        book.setAvailable(true);

        bookRepository.updateBook(book);

        // Step 7: Card ki books list se remove karo
        if (card.getBooks() != null) {
            card.getBooks().removeIf(
                    currentBook -> currentBook.getId() == bookId
            );
        }

        // Step 8: Return transaction create karo
        Transaction returnTransaction = new Transaction();

        returnTransaction.setBook(book);
        returnTransaction.setCard(card);
        returnTransaction.setFineAmount(fine);
        returnTransaction.setIssueOperation(false);
        returnTransaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        returnTransaction.setReturnDate(returnDate);
        returnTransaction.setFinePaid(fine == 0);

        transactionRepository.save(returnTransaction);

        return returnTransaction.getTransactionId();
    }


    public List<TransactionResponseDto> getTransactions() {

        List<Transaction> transactions = transactionRepository.findAll();

        List<TransactionResponseDto> response = new ArrayList<>();

        for (Transaction transaction : transactions) {

            try {

                TransactionResponseDto dto = new TransactionResponseDto();

                // Transaction ID
                dto.setTransactionId(
                        transaction.getTransactionId() != null
                                ? transaction.getTransactionId()
                                : "N/A"
                );


                // Book Name
                if (transaction.getBook() != null) {

                    dto.setBookName(
                            transaction.getBook().getName() != null
                                    ? transaction.getBook().getName()
                                    : "Unknown Book"
                    );

                } else {

                    dto.setBookName("Unknown Book");
                }


                // Student Name
                if (
                        transaction.getCard() != null &&
                                transaction.getCard().getStudent() != null
                ) {

                    dto.setStudentName(
                            transaction.getCard()
                                    .getStudent()
                                    .getName() != null
                                    ? transaction.getCard()
                                    .getStudent()
                                    .getName()
                                    : "Unknown Student"
                    );

                } else {

                    dto.setStudentName("Unknown Student");
                }


                // Transaction Type
                Boolean issueOperation =
                        transaction.getIssueOperation();

                if (Boolean.TRUE.equals(issueOperation)) {

                    dto.setType("Issue");

                } else if (Boolean.FALSE.equals(issueOperation)) {

                    dto.setType("Return");

                } else {

                    dto.setType("Unknown");
                }


                // Transaction Status
                if (transaction.getTransactionStatus() != null) {

                    dto.setStatus(
                            transaction.getTransactionStatus().toString()
                    );

                } else {

                    dto.setStatus("UNKNOWN");
                }


                // Fine
                dto.setFine(
                        transaction.getFineAmount()
                );


                // Transaction Date
                dto.setDate(
                        transaction.getTransactionDate()
                );


                response.add(dto);

            } catch (Exception e) {

                System.err.println(
                        "Failed to convert transaction ID: "
                                + transaction.getId()
                );

                e.printStackTrace();
            }
        }

        return response;
    }

    public long getTransactionsCount() {
        return transactionRepository.count();
    }

    public List<TransactionResponseDto> searchTransactions(String keyword) {

        List<TransactionResponseDto> allTransactions = getTransactions();

        List<TransactionResponseDto> result = new ArrayList<>();

        for (TransactionResponseDto dto : allTransactions) {

            if (dto.getBookName().toLowerCase().contains(keyword.toLowerCase())
                    || dto.getStudentName().toLowerCase().contains(keyword.toLowerCase())) {

                result.add(dto);

            }

        }

        return result;

    }




}

