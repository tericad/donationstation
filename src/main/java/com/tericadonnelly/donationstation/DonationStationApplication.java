package com.tericadonnelly.donationstation;

import com.tericadonnelly.donationstation.controllers.DonationController;
import com.tericadonnelly.donationstation.controllers.EmailController;
import com.tericadonnelly.donationstation.controllers.SMSController;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

import static com.tericadonnelly.donationstation.controllers.EmailController.email;
import static com.tericadonnelly.donationstation.controllers.SMSController.ACCOUNT_SID;
import static com.tericadonnelly.donationstation.controllers.SMSController.AUTH_TOKEN;

@SpringBootApplication
public class DonationStationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonationStationApplication.class, args);

		SMSController.configProperties();

		DonationController.configProperties();

		EmailController.configProperties();


		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		/*Message message = Message
				.creator(new PhoneNumber("+13146166469"),
						new PhoneNumber("+13143093599"),
						"Testing")
				.create();
		System.out.println(message.getSid()); */
	}

}
