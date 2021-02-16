package fr.excilys.formation.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import fr.excilys.formation.controller.Controller;
import fr.excilys.formation.model.Company;
import fr.excilys.formation.model.Computer;

public class View {
	private Controller controller;
	private int offset = 0, numberOfRows = 10;

	private enum Page {
		HOME("0"), COMPUTERS("1"), COMPANIES("2"), DETAIL("3"), CREATE("4"), UPDATE("5"), DELETE("6");

		public final String label;
		public static final Map<String, Page> association = new HashMap<>();

		static {
			for (Page page : values()) {
				association.put(page.label, page);
			}
		}

		private Page(String label) {
			this.label = label;
		}
	};

	private Page actualPage = Page.HOME;

	public View() {
		controller = new Controller();
		System.out.println("Bienvenue sur MyComputerDatabase.com !\n\n");
		displayPage();
	}

	public int update(String query) {
		int res = 1;
		switch (actualPage) {
		default:
		case HOME:
			res = pageHome(query);
			break;
		case COMPUTERS:
			pageListDatas(query);
			break;
		case COMPANIES:
			pageListDatas(query);
			break;
		case DETAIL:
			pageDetail(query);
			break;
		case CREATE:
			break;
		case UPDATE:
			break;
		case DELETE:

		}
		displayPage();
		return res;
	}

	private void displayPage() {
		switch (actualPage) {
		case HOME:
			printMenu();
			break;
		case COMPUTERS:
			printAllComputers();
			break;
		case COMPANIES:
			printAllCompanies();
			break;
		case DETAIL:
			break;
		case CREATE:
			break;
		case UPDATE:
			break;
		case DELETE:
		}
	}

	private int pageHome(String query) {
		if (query.equals("q") || query.equals("Q")) {
			System.out.println("\n\nAu plaisir de vous revoir !");
			return 0;
		} else if (Page.association.containsKey(query)) {
			actualPage = Page.association.get(query);
		} else {
			System.out.println("Nous ne connaissons pas cette commande :/");
			printMenu();
		}

		return 1;
	}

	private void printMenu() {
		System.out.println("Que souhaitez-vous faire ?");
		System.out.println("0 : Accueil");
		System.out.println("1 : Afficher tous les ordinateurs");
		System.out.println("2 : Afficher toutes les entreprises");
		System.out.println("3 : Afficher les informations sur un ordinateur");
		System.out.println("4 : Créer un nouvel ordinateur");
		System.out.println("5 : Modifier un ordinateur");
		System.out.println("6 : Supprimer un ordinateur");

	}

	private void pageListDatas(String query) {
		switch (query) {
		case "P":
			if (offset > 0) {
				offset = (offset > 9) ? offset - 10 : 0;
				System.out.println("Infos précedentes");
			} else {
				System.out.println("Nous ne pouvons pas revenir en arrière");
			}
			break;
		case "N":
			offset += 10;
			System.out.println("Infos suivantes");
			break;
		case "A":
			actualPage = Page.HOME;
			offset = 0;
			System.out.println("Switch to HOME");
			break;
		default:
			System.out.println("Commande non reconnue");
		}
	}

	private void printAllComputers() {
		List<Computer> computers = null;
		String format = "%4d %-40s%s%n";

		computers = controller.getComputers(offset, numberOfRows);

		System.out.printf("%-4s %-40s%s%n", "ID", "Nom", "Entreprise");

		for (Computer computer : computers) {
			System.out.printf(format, computer.getID(), computer.getName(), computer.getCompany().getName());
		}

		System.out.println("N : Page suivante | P : Page Précédente | A : Accueil");
	}

	private void printAllCompanies() {
		List<Company> companies = null;

		companies = controller.getCompanies(offset, numberOfRows);

		System.out.println("Entreprise");

		for (Company company : companies) {
			System.out.println(company.getName());
		}

		System.out.println("N : Page suivante | P : Page Précédente | A : Accueil");
	}

	private void pageDetail(String query) {
		
	}
}
