package com.tericadonnelly.donationstation.controllers;


import com.tericadonnelly.donationstation.models.Charity;
import com.tericadonnelly.donationstation.models.Donor;
import com.tericadonnelly.donationstation.models.StripePaymentWrapper;
import com.tericadonnelly.donationstation.models.data.CharityDao;
import com.tericadonnelly.donationstation.models.data.DonorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import static com.tericadonnelly.donationstation.controllers.EmailController.sendEmails;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

@Controller
@RequestMapping ("donate")
public class DonationController {

    @Autowired
    private DonorDao donorDao;

    @Autowired
    private CharityDao charitydao;


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
    public String donateNow(@RequestParam String phone, @RequestParam String charity, @RequestParam String message, Model model){
        String phoneNumber = phone;
        String charityNumber = charity;
        String donation = message;
        String key = "'" + API_Key + "'";

            model.addAttribute("phoneNumber", phoneNumber);
            model.addAttribute("charity",charityNumber);
            model.addAttribute("donation", donation);
            model.addAttribute("title", "Donation Station");
            model.addAttribute("stripeKey", key);

            return "stripe/payNow";

    }

    @RequestMapping(value="charges", method = RequestMethod.POST)
    @ResponseBody //this is because we're returning nothing. Only status codes.
    public String processDonation(@RequestBody StripePaymentWrapper payload, Model model){

        String amount = payload.getAmount();
        Double donationAmount = (parseDouble(amount))/100;
        String donorName = payload.getPayerName();
        String donorEmail = payload.getPayerEmail();
        String addressLine = payload.getShippingAddress().getAddressLine();
        String city = payload.getShippingAddress().getCity();
        String state = payload.getShippingAddress().getRegion();
        String zipCode = payload.getShippingAddress().getPostalCode();
        String stripeToken = payload.getToken();
        String charityNumber = payload.getCharity();
        Long charityPhoneNumber = parseLong(charityNumber);
        List<Charity> charityList = charitydao.findByPhoneNumber(charityPhoneNumber);
        Charity charity = charityList.get(0);
        String charityEmail = charity.getEmail();
        Date date = new Date();

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

            Donor newDonor = new Donor(donorName, donorEmail, donationAmount, addressLine, city, state, zipCode, date, charity);
            donorDao.save(newDonor);

            try {
                sendEmails(donorEmail, charityEmail);


            } catch (IOException ex){
                ex.printStackTrace();
            }



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
