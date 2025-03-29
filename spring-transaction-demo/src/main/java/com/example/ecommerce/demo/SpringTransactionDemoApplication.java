package com.example.ecommerce.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication(scanBasePackages = {"com.example.ecommerce.demo.controller","service","model", "repository","model","dto"})
public class SpringTransactionDemoApplication implements ApplicationRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringTransactionDemoApplication.class, args);
	}
	@Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("\n===== REGISTERED ENDPOINTS =====");
        requestMappingHandlerMapping.getHandlerMethods().forEach((key, value) -> {
            System.out.println(key + " -> " + value);
        });
        System.out.println("==============================\n");
    }

}
