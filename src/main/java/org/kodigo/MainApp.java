package org.kodigo;

import org.kodigo.service.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "org.kodigo")
public class MainApp {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(MainApp.class);
		OrderService orderService = context.getBean(OrderService.class);
		orderService.processOrder(4111, 235, 2);
	}
}