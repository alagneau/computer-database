package fr.excilys.formation.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fr.excilys.formation.view.View;

public class Main {

	
	public static void main(String[] args) throws IOException {
		View view = new View();
		int status = 1;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while(status > 0) {
			status = view.update(reader.readLine());
		}
	}
}
