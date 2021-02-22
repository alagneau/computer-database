package com.excilys.formation.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.excilys.formation.view.View;

public class Main {
	//private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws IOException {
		View view = new View();
		int status = 1;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		//logger.info("Example log from {}", Main.class.getSimpleName());
		
		while(status > 0) {
			status = view.update(reader.readLine());
		}
	}
}
