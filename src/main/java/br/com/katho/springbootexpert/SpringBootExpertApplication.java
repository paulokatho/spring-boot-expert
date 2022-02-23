package br.com.katho.springbootexpert;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootExpertApplication {

	//@Value("${application.name}")
	private String applicationName;
	
	@GetMapping("/hello")
	public String helloWorld() {
		return "SPRING BOOT APPLICATION";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootExpertApplication.class, args);
		
	}

}
