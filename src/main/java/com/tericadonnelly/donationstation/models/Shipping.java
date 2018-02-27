package com.tericadonnelly.donationstation.models;


public class Shipping {

    String city;
    String[] address;
    String state;
    String zipCode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String[] getAddress() {
        return address;
    }

    /*   public String getAddress() {
            String commaSepAddress = "";
            for(String item : address){
                commaSepAddress = commaSepAddress + ", " + item;
            }
            return commaSepAddress;
        }
    */
    public void setAddress(String[] address) {
        this.address = address;
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


    public static String addressString(String[] address) {
        String commaSepAddress = "";
        for (String item : address) {
            commaSepAddress = commaSepAddress + ", " + item;
        }
        return commaSepAddress;
    }
}