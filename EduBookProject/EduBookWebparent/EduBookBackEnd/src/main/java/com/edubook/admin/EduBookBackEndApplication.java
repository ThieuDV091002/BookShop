package com.edubook.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EntityScan({"com.edubook.admin", "com.edubook.common.entity", "com.edubook.admin.account", "com.edubook.admin.book", "com.edubook.admin.order","com.edubook.admin.security"})
@Component
@ComponentScan(basePackages = {"com.edubook.admin", "com.edubook.common.entity", "com.edubook.admin.account", "com.edubook.admin.security", "com.edubook.admin.order", "com.edubook.admin.security"})
public class EduBookBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduBookBackEndApplication.class, args);
	}

}
