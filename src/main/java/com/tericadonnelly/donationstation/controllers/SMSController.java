package com.tericadonnelly.donationstation.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class SMSController {


    // Find your Account Sid and Auth Token at twilio.com/console
    public static String ACCOUNT_SID = "";
    public static String AUTH_TOKEN = "";


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


