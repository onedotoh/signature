package io.spheric.signature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SignatureApplication {
	public static void main(String[] args) {
		SpringApplication.run(SignatureApplication.class, args);
	}
}
