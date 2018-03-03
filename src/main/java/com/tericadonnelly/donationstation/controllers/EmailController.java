package com.tericadonnelly.donationstation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.sendgrid.*;

@Controller
@RequestMapping("email")
public class EmailController {

    public static String SENDGRID_API_KEY = "";

    public static void configProperties(){


        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value
            SENDGRID_API_KEY = prop.getProperty("SENDGRID_API_KEY");
            System.out.println(SENDGRID_API_KEY);


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

    public static void sendEmails(String emailDonor, String emailCharity) throws IOException {
        Content donorContent = new Content("text/plain", "Thank you for your donation. Your tax letter " +
                "will be mailed in 4-8 weeks.");
        Content charityContent = new Content("text/plain", "You have received a new donation. Please login " +
                "to view details.");
        email(emailDonor, donorContent);
        email(emailCharity, charityContent);

    }

    // using SendGrid's Java Library
// https://github.com/sendgrid/sendgrid-java

        public static void email(String emailTo, Content content) throws IOException {
            Email from = new Email("tericadonnelly@gmail.com");
            String subject = "Donation Station";
            Email to = new Email(emailTo);
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(SENDGRID_API_KEY);
            Request request = new Request();
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                Response response = sg.api(request);
                System.out.println(response.getStatusCode());
                System.out.println(response.getBody());
                System.out.println(response.getHeaders());
            } catch (IOException ex) {
                throw ex;
            }
        }

}
