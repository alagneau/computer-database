package fr.excilys.formation.view;

import fr.excilys.formation.controller.Controller;

public class View {
	private Controller controller;

	public View() {
		controller = new Controller();
		System.out.println("Bienvenue sur MyComputerDatabase.com !\n\n");
	}
	
	private void printMenu() {
		System.out.println("Que souhaitez-vous faire ?");
	}
	
	public int update(String query) {
		switch(query) {
		case "Q":
		case "q":
			System.out.println("\n\nAu plaisir de vous revoir !");
			return 0;
		case "1":
			System.out.println("feature pas encore développée");
			break;
		default:
			System.out.println("Nous ne connaissons pas cette commande :/");
		}
		
		printMenu();
		
		return 1;
	}
}
