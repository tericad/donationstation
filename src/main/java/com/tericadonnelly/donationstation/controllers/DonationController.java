package com.tericadonnelly.donationstation.controllers;


import com.tericadonnelly.donationstation.models.Donor;
import com.tericadonnelly.donationstation.models.Shipping;
import com.tericadonnelly.donationstation.models.ShippingWrapper;
import com.tericadonnelly.donationstation.models.data.DonorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
@RequestMapping ("donate")
public class DonationController {

    @Autowired
    private DonorDao donorDao;


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
    @ResponseBody //this is because we're returning nothing. Only status codes.
    public String processDonation(@RequestBody ShippingWrapper payload, Model model){

        String amount = payload.getAmount();
        String donorName = payload.getDonorName();
        String donorEmail = payload.getDonorEmail();
        String stripeToken = payload.getToken();


        // Set your secret key: remember to change this to your live secret key in production
// See your keys here: https://dashboard.stripe.com/account/apikeys
        Stripe.apiKey = Secret_Key;

// Token is created using Checkout or Elements!
// Get the payment token ID submitted by the form:

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

        return "";

    }

    @RequestMapping(value="thanks", method = RequestMethod.GET)
    public String donationThanks(Model model) {

        model.addAttribute("title", "Donation Station");

        return "stripe/thanks";
    }


}
