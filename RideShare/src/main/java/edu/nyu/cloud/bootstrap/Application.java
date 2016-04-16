package edu.nyu.cloud.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * This class is used to bootstrap the application.
 * 
 * @author rahulkhanna Date:05-Apr-2016
 */
@SpringBootApplication
@ComponentScan(basePackages = "edu.nyu.cloud")
@ImportResource({"classpath:*/hibernate-config.xml","classpath:*/hibernate-beans.xml","classpath:*/application-beans.xml"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}