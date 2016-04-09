package edu.nyu.cloud.bootstrap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class is used to bootstrap the application.
 * 
 * @author rahulkhanna Date:05-Apr-2016
 */
// @EnableAutoConfiguration
// @ComponentScan(basePackages = "edu.nyu.cloud")
public class Application {

	private static Application app = new Application();

	private static Application getInstance() {
		return app;
	}

	public static void main(String[] args) {
		// SpringApplication.run(Application.class, args);
		getInstance().propel();

	}

	private void propel() {
		while (!Thread.currentThread().isInterrupted()) {
			ApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/application-context.xml");
		}

	}
}