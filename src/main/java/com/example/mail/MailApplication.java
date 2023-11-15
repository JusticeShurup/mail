package com.example.mail;

import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.domain.PostalItem;
import com.example.mail.model.service.MailDepartmentService;
import com.example.mail.model.service.PostalItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MailApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailApplication.class, args);
	}


	@Bean
	public CommandLineRunner demo(MailDepartmentService mailDepartmentService, PostalItemService postalItemService) {
		return (args) -> {
			// save few MailDepartment
			/*
			MailDepartment mailDepartment1 = new MailDepartment("444555", "First Department", "Magnitogorsk, Lenina 133");


			mailDepartmentService.save(mailDepartment1);
			mailDepartmentService.save(mailDepartment2);
			*/

			/*
			postalItemService.save(postalItem1);
			// fetch all MailDepartment
			System.out.println("-----List of MailDepartments------");
			for (MailDepartment mailDepartment : mailDepartmentService.getMailDepartmentList()) {
				System.out.println("MailDepartment Detail:" + mailDepartment.toString());
			}
			*/
		};
	}

}
