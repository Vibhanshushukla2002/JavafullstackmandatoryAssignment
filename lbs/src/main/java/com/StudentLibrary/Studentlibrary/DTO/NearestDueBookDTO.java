package com.StudentLibrary.Studentlibrary.DTO;


import java.time.LocalDate;

public class NearestDueBookDTO {
    private String bookName;

    private LocalDate issueDate;

    private LocalDate dueDate;

    private long daysLeft;

    private int fine;

    public NearestDueBookDTO() {
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public long getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(long daysLeft) {
        this.daysLeft = daysLeft;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }
}
