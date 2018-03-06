package com.tericadonnelly.donationstation.models;






import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;


@Entity
public class Donor {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String email;

    private Double donationAmount;

    private Date date;

    private String addressLine;

    private String city;

    private String state;

    private String zipCode;

    @ManyToOne
    private Charity charity;

    public Donor(String name, String email, Double donationAmount, String addressLine, String city,
                 String state, String zipCode, Date date, Charity charity) {
        this.name = name;
        this.email = email;
        this.donationAmount = donationAmount;
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.date = date;
        this.charity = charity;
    }

    public Donor() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(Double donationAmount) {
        this.donationAmount = donationAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }
}
