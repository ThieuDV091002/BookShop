package com.edubook.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EntityScan({"com.edubook.common.entity", "com.edubook.site", "com.edubook.site.book", 
	"com.edubook.site.customer", "com.edubook.site.shoppingcart", "com.edubook.site.security", 
	"com.edubook.site.address", "com.edubook.site.checkout", "com.edubook.site.order"})
@Component
@ComponentScan(basePackages = {"com.edubook.common.entity","com.edubook.site", "com.edubook.site.book", 
		"com.edubook.site.customer", "com.edubook.site.shoppingcart", "com.edubook.site.security", 
		"com.edubook.site.address", "com.edubook.site.checkout", "com.edubook.site.order"})
public class EduBookFrontEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduBookFrontEndApplication.class, args);
	}

}
