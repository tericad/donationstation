package com.tericadonnelly.donationstation.models;






import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashMap;

@Entity
public class Donor {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String email;

    private String donationAmount;

    private HashMap<String, String> address;

    public Donor(String name, String email, String donationAmount, HashMap<String, String> address) {
        this.name = name;
        this.email = email;
        this.donationAmount = donationAmount;
        this.address = address;
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

    public String getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(String donationAmount) {
        this.donationAmount = donationAmount;
    }

    public HashMap<String, String> getAddress() {
        return address;
    }

    public void setAddress(HashMap<String, String> address) {
        this.address = address;
    }
}
