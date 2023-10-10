package tn.esprit.programmeechange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProgrammeechangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgrammeechangeApplication.class, args);
	}

}
