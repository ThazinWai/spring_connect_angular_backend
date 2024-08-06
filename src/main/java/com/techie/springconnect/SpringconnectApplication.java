package com.techie.springconnect;

import com.techie.springconnect.config.OpenAPIConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(OpenAPIConfiguration.class)
public class SpringconnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringconnectApplication.class, args);
	}

}
