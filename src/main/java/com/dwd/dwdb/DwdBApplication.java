package com.dwd.dwdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class DwdBApplication {

	public static void main(String[] args) {
		SpringApplication.run(DwdBApplication.class, args);
	}

}
