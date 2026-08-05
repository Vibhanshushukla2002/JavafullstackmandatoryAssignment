package com.StudentLibrary.Studentlibrary.Controllers;

import com.StudentLibrary.Studentlibrary.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin(origins = "http://localhost:5174")
@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    //what i need ideally is card_id and book_id

    @PostMapping("/issueBook")
    public ResponseEntity issueBook(@RequestParam(value = "cardId") int cardId,
                                    @RequestParam("bookId")int bookId) throws Exception {
        String transaction_id=transactionService.issueBooks(cardId,bookId);
        return new ResponseEntity("Your Transaction was successfull here is your Txn id:"+transaction_id, HttpStatus.OK);

    }
    @PostMapping("/returnBook")
    public ResponseEntity returnBook(@RequestParam("cardId") int cardId,
                                     @RequestParam("bookId") int bookId) throws Exception {
        String transaction_id=transactionService.returnBooks(cardId,bookId);
        return new ResponseEntity(
                "Your Transaction was Successful here is your Txn id:"+transaction_id,HttpStatus.OK);

    }
    @GetMapping("/getTransactions")
    public ResponseEntity getTransactions() {

        return new ResponseEntity(
                transactionService.getTransactions(),
                HttpStatus.OK
        );

    }
    @GetMapping("/transactions/count")
    public ResponseEntity<Long> getTransactionsCount() {
        return new ResponseEntity<>(
                transactionService.getTransactionsCount(),
                HttpStatus.OK
        );
    }
    @GetMapping("/searchTransaction")
    public ResponseEntity searchTransaction(
            @RequestParam String keyword
    ) {

        return new ResponseEntity(
                transactionService.searchTransactions(keyword),
                HttpStatus.OK
        );

    }


}
