package com.spring.rest.webservices.helloworld;

public class HelloWorldBean{

   private String message;

  

public HelloWorldBean(String message) {
	super();
	this.message = message;
}

public String getmessage() {
	return message;
}

public void setmessage(String message) {
	this.message = message;
}
   
   
@Override
public String toString() {
	return "HelloWorldBean [message=" + message + "]";
}
	
	
}
