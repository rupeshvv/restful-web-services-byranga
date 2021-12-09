package com.spring.rest.webservices.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class HelloController {

	@Autowired
	private MessageSource messageSource;
	
	 HelloWorldBean helloWorldBean;
	  
	@GetMapping("/hello-world")
	public String helloWorld() {
		
		return "Hello World";
	}
	
	@GetMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	@GetMapping("/hello-world-bean/pathvariable/{name}")
	public HelloWorldBean helloWorldPathvariablle(@PathVariable String name) {
		//return new HelloWorldBean(String.format("Hello World, %s",name));
		return new HelloWorldBean("Hello World, "+name);
	}
	
	@GetMapping("/hello-world-internationalized")
	public String helloWorldInternationalized() {
		return messageSource.getMessage("good.morning.message", null,"Default Message", LocaleContextHolder.getLocale());
		
	}
	
	
	
}
