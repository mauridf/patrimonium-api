package br.com.patrimonium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PatrimoniumApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatrimoniumApplication.class, args);
	}

}
