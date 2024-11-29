package com.example.hython;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HythonApplication {

	public static void main(String[] args) {
		SpringApplication.run(HythonApplication.class, args);
	}

}
