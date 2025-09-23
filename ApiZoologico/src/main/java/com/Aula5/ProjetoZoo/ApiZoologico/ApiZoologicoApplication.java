package com.Aula5.ProjetoZoo.ApiZoologico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class    ApiZoologicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiZoologicoApplication.class, args);
	}

}
