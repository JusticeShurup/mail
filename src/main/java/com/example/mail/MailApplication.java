package com.example.mail;

import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.domain.PostalItem;
import com.example.mail.model.services.MailDepartmentService;
import com.example.mail.model.services.PostalItemService;
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
			MailDepartment mailDepartment2 = new MailDepartment("444555", "Почта под домом", "Magnitogorsk, Karla Marksa 150");
			PostalItem postalItem1 = new PostalItem("Письмо", "444555", "Magnitogorsk, Lenina 133", "Ivan", mailDepartment2, false);

			/*
			postalItemService.save(postalItem1);
			// fetch all MailDepartment
			System.out.println("-----List of MailDepartments------");
			for (MailDepartment mailDepartment : mailDepartmentService.getMailDepartmentList()) {
				System.out.println("MailDepartment Detail:" + mailDepartment.toString());
			}
			*/
			var mapper = new ObjectMapper();
			var str = mapper.writeValueAsString(postalItem1);
			System.out.println(str);
		};
	}

}
