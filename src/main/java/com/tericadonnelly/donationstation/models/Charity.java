package com.tericadonnelly.donationstation.models;


import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Charity {

    @Id
    @GeneratedValue
    private int id;
    private long phoneNumber;

    @NotNull
    @Size(min=5, max=15, message = "Username must be between 5 and 15 characters")
    private String username;

    @NotNull
    @Size(min=6)
    private String password;

    private String name;

    @Email
    private String email;


    @OneToMany
    @JoinColumn(name = "charity_id")
    private List<Donor> donors = new ArrayList<>();


    public Charity(int phoneNumber, String username, String password, String name, String email) {
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Charity() {
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Donor> getDonors() {
        return donors;
    }
}
