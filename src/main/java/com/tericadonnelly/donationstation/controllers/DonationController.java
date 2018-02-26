package com.tericadonnelly.donationstation.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;

@Controller
@RequestMapping ("donate")
public class DonationController {

    // Find your Api Key at dashboard.stripe.com
    public static String API_Key = "";
    public static String Secret_Key = "";


    public static void configProperties(){


        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value
            API_Key = prop.getProperty("API_Key");
            Secret_Key = prop.getProperty("Secret_Key");


        }catch(IOException ex){
            ex.printStackTrace();
        } finally{
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @RequestMapping(value="now", method = RequestMethod.GET)
    public String donateNow(@RequestParam String phone, @RequestParam String message, Model model){
        String phoneNumber = phone;
        String donation = message;
        String key = "'" + API_Key + "'";

            model.addAttribute("phoneNumber", phoneNumber);
            model.addAttribute("donation", donation);
            model.addAttribute("title", "Donation Station");
            model.addAttribute("stripeKey", key);

            return "stripe/payNow";

    }

    @RequestMapping(value="charges", method = RequestMethod.POST)
    public String processDonation(@RequestBody Map<String, Object> payload, Model model){

        String amount = payload.get("amount").toString();
        String donorName = payload.get("name").toString();
        String donorEmail = payload.get("email").toString();
        Object shipping = payload.get("shippingAddress");

        System.out.println(donorName + " " + donorEmail);
        Object token = payload.get("token");
        String stripeToken = token.toString();
        // Set your secret key: remember to change this to your live secret key in production
// See your keys here: https://dashboard.stripe.com/account/apikeys
        Stripe.apiKey = Secret_Key;

// Token is created using Checkout or Elements!
// Get the payment token ID submitted by the form:
        //String token = request.getParameter("stripeToken");

// Charge the user's card:
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("currency", "usd");
        params.put("description", "Donation charge");
        params.put("source", stripeToken);

        try {
            Charge charge = Charge.create(params);
            System.out.println(charge);

        } catch (StripeException e) {
            e.printStackTrace();

        }


        model.addAttribute("title", "Donation Station");
        model.addAttribute("amount", amount);

        return "stripe/thanks";

    }


}
