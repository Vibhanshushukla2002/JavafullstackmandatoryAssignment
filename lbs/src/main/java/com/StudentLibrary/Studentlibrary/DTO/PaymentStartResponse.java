package com.StudentLibrary.Studentlibrary.DTO;

public class PaymentStartResponse {
    private Integer paymentId;
    private String orderId;
    private Double amount;
    private String currency;
    private String key;

    public PaymentStartResponse() {
    }

    public PaymentStartResponse(Integer paymentId,
                                String orderId,
                                Double amount,
                                String currency,
                                String key) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.key = key;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
