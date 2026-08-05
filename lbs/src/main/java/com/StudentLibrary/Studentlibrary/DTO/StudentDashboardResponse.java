package com.StudentLibrary.Studentlibrary.DTO;

import java.util.List;


public class StudentDashboardResponse {

    private String username;

    private int activeBooks;

    private int totalBooksIssued;

    private int pendingFine;

    private int overdueBooks;

    private NearestDueBookDTO nearestDueBook;

    private List<ActiveBookDTO> activeBooksList;

    private List<HistoryDTO> history;

    public StudentDashboardResponse() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getActiveBooks() {
        return activeBooks;
    }

    public void setActiveBooks(int activeBooks) {
        this.activeBooks = activeBooks;
    }

    public int getTotalBooksIssued() {
        return totalBooksIssued;
    }

    public void setTotalBooksIssued(int totalBooksIssued) {
        this.totalBooksIssued = totalBooksIssued;
    }

    public int getPendingFine() {
        return pendingFine;
    }

    public void setPendingFine(int pendingFine) {
        this.pendingFine = pendingFine;
    }

    public int getOverdueBooks() {
        return overdueBooks;
    }

    public void setOverdueBooks(int overdueBooks) {
        this.overdueBooks = overdueBooks;
    }

    public NearestDueBookDTO getNearestDueBook() {
        return nearestDueBook;
    }

    public void setNearestDueBook(NearestDueBookDTO nearestDueBook) {
        this.nearestDueBook = nearestDueBook;
    }

    public List<ActiveBookDTO> getActiveBooksList() {
        return activeBooksList;
    }

    public void setActiveBooksList(List<ActiveBookDTO> activeBooksList) {
        this.activeBooksList = activeBooksList;
    }

    public List<HistoryDTO> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryDTO> history) {
        this.history = history;
    }
}
