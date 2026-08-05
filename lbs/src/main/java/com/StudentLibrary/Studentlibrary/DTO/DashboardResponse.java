package com.StudentLibrary.Studentlibrary.DTO;

public class DashboardResponse {
    private Long totalBooks;
    private Long issuedBooks;
    private Long totalStudents;
    private Long totalAuthors;
    private Long totalTransactions;

    private Long totalPayments;
    private Long paidPayments;
    private Long pendingPayments;

    private Double totalRevenue;

    public DashboardResponse() {
    }

    public Long getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(Long totalBooks) {
        this.totalBooks = totalBooks;
    }

    public Long getIssuedBooks() {
        return issuedBooks;
    }

    public void setIssuedBooks(Long issuedBooks) {
        this.issuedBooks = issuedBooks;
    }

    public Long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Long getTotalAuthors() {
        return totalAuthors;
    }

    public void setTotalAuthors(Long totalAuthors) {
        this.totalAuthors = totalAuthors;
    }

    public Long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(Long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public Long getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(Long totalPayments) {
        this.totalPayments = totalPayments;
    }

    public Long getPaidPayments() {
        return paidPayments;
    }

    public void setPaidPayments(Long paidPayments) {
        this.paidPayments = paidPayments;
    }

    public Long getPendingPayments() {
        return pendingPayments;
    }

    public void setPendingPayments(Long pendingPayments) {
        this.pendingPayments = pendingPayments;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
