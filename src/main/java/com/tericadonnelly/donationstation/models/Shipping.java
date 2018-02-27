package com.tericadonnelly.donationstation.models;


public class Shipping {

    String city;
    String[] addressLine;
    String region;
    String postalCode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


     public String getAddressLine() {
         String commaSepAddress = "";
            for(String item : addressLine){
                commaSepAddress = commaSepAddress + item + ", ";
            }
            return commaSepAddress;
        }


    public void setAddressLine(String[] addressLine) {
        this.addressLine = addressLine;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}