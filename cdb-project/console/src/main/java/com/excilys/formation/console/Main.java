package com.excilys.formation.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	
	public static void main(String[] args) throws IOException {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ContextConfig.class)) {
			System.out.println("COUCOU LA CONSOLE");
			View view = context.getBean(View.class);
			int status = 1;
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			while (status > 0) {
				status = view.update(reader.readLine());
			}
		}
	}
}
