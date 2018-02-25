package com.tericadonnelly.donationstation.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping ("donate")
public class DonationController {

    // Find your Api Key at dashboard.stripe.com
    public static String API_Key = "";


    public static void configProperties(){


        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value
            API_Key = prop.getProperty("API_Key");


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

}
