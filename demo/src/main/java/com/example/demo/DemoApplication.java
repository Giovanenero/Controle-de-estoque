package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.Principal.Principal;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		Principal objPrincipal = new Principal();
		objPrincipal.executar();
		SpringApplication.run(DemoApplication.class, args);
	}

}
