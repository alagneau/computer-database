package com.excilys.formation.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.formation.configuration.SpringConfig;
import com.excilys.formation.view.View;

public class Main {
	
	public static void main(String[] args) throws IOException {
		try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class)) {
			View view = context.getBean(View.class);
			int status = 1;
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			while(status > 0) {
				status = view.update(reader.readLine());
			}
		}
	}
}
