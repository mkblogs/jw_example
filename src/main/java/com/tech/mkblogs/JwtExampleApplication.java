package com.tech.mkblogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import com.tech.mkblogs.onstartup.DBInit;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableMongoAuditing
@Log4j2
public class JwtExampleApplication implements CommandLineRunner{

	@Autowired
	DBInit dbInit;
	
	public static void main(String[] args) {
		SpringApplication.run(JwtExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("In side run method ");
		dbInit.loadUsers();
	}
}
