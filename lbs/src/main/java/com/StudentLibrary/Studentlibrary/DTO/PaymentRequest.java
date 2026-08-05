package com.StudentLibrary.Studentlibrary.DTO;

public class PaymentRequest {

    private Integer bookId;
    private Double amount;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
