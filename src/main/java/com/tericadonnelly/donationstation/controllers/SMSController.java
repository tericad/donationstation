package com.tericadonnelly.donationstation.controllers;



import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Controller
@RequestMapping ("sms")
public class SMSController {


    // Find your Account Sid and Auth Token at twilio.com/console
    public static String ACCOUNT_SID = "";
    public static String AUTH_TOKEN = "";


   @RequestMapping(value="incoming", method = RequestMethod.POST, produces = "application/xml;")
   @ResponseBody
   public String donationReply(@RequestParam String From, @RequestParam String Body, Model model){

       String phoneNumber = From;
       String messageReceived = Body;

       model.addAttribute("phoneNumber", phoneNumber);
       model.addAttribute("message", messageReceived);

       String url = "Go here to donate: https://home.wasteoftime.org/donate/now/?phone=";
       Message sms = new Message.Builder().body(new Body(url + phoneNumber + "&message=" + messageReceived)).build();
       MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();

       try {
           return twiml.toXml();
       } catch (TwiMLException e) {
           e.printStackTrace();
           return "";
       }
   }


    public static void configProperties(){


        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value
            ACCOUNT_SID = prop.getProperty("ACCOUNT_SID");
            AUTH_TOKEN = prop.getProperty("AUTH_TOKEN");

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



    }


