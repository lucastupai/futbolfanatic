package com.futbolfanatic.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication(scanBasePackages = {
    "com.futbolfanatic.notification_service",
    "com.futbolfanatic.order_service",
    "com.futbolfanatic.payment_service",
    "com.futbolfanatic.product_service",
    "com.futbolfanatic.demo" // importante para no romper lo actual
})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
