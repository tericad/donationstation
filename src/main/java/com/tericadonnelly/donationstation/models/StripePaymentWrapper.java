package com.tericadonnelly.donationstation.models;

public class StripePaymentWrapper {

    String payerName;
    String payerEmail;
    Shipping shippingAddress;
    String amount;
    String token;

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    public Shipping getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Shipping shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
