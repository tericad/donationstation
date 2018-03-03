package com.tericadonnelly.donationstation.models;

import com.sun.istack.internal.NotNull;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Charity {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=5, max=15, message = "Username must be between 5 and 15 characters")
    private String username;

    @NotNull
    @Size(min=6)
    private String password;

    private String name;

    @Email
    private String email;


    public Charity(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Charity() {
    }

    public int getId() {
        return id;
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
}
