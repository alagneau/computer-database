package com.excilys.formation.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.formation.controller.Controller;
import com.excilys.formation.exception.AddDataException;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.DatabaseAccessException;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;


@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class View {
	@Autowired
	private Controller controller;
	private int offset = 0, numberOfRows = 10;
	private int pageIndex = 0;
	private Computer computerDetails;
	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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
		System.out.println("Bienvenue sur MyComputerDatabase.com !\n\n");
		displayPage();
	}

	public int update(String query) {
		int statusAppli = 1;
		switch (actualPage) {
		default:
		case HOME:
			statusAppli = pageHome(query);
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
			pageCreate(query);
			break;
		case UPDATE:
			pageUpdate(query);
			break;
		case DELETE:
			pageDelete(query);
		}
		displayPage();
		return statusAppli;
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
			printDetailPage();
			break;
		case CREATE:
			printCreate();
			break;
		case UPDATE:
			printUpdate();
			break;
		case DELETE:
			printDelete();
		}
	}

	private int pageHome(String query) {
		pageIndex = 0;
		computerDetails = null;
		if (query.equals("q") || query.equals("Q")) {
			System.out.println("\n\nAu plaisir de vous revoir !");
			return 0;
		} else if (Page.association.containsKey(query)) {
			actualPage = Page.association.get(query);
		} else {
			printError(query);
		}

		return 1;
	}

	private void printMenu() {
		System.out.println("Que souhaitez-vous faire ?");
		System.out.println("A : Accueil");
		System.out.println("1 : Afficher tous les ordinateurs");
		System.out.println("2 : Afficher toutes les entreprises");
		System.out.println("3 : Afficher les informations sur un ordinateur");
		System.out.println("4 : Créer un nouvel ordinateur");
		System.out.println("5 : Modifier un ordinateur");
		System.out.println("6 : Supprimer un ordinateur");

	}

	private void pageListDatas(String query) {
		int maxValues = 0;
		try {
			maxValues = (actualPage == Page.COMPUTERS) ? controller.numberOfComputers() : controller.numberOfCompanies();
		} catch(DatabaseAccessException exception) {
			System.out.println(exception.getMessage());
		}
		switch (query) {
		case "P":
			offset = (offset > 9) ? offset - 10 : maxValues - 10;
			System.out.println("Infos précedentes\n");

			break;
		case "N":
			offset = (offset + 10 >= maxValues) ? 0 : offset + 10;
			System.out.println("Infos suivantes\n");
			break;
		case "A":
			actualPage = Page.HOME;
			offset = 0;
			System.out.println("Switch to HOME");
			break;
		default:
			printError(query);
		}
		System.out.println("offset : " + offset);
	}

	private void printAllComputers() {
		List<Optional<Computer>> computers = null;

		try {
			computers = controller.getComputers(offset, numberOfRows);
		} catch (ReadDataException | ArgumentException exception) {
			System.out.println(exception.getMessage());
		}

		System.out.println(Computer.HEADER);

		for (Optional<Computer> computer : computers) {
			if (computer.isPresent()) {
				System.out.println(computer.get().toString());
			}
		}

		System.out.println("N : Page suivante | P : Page Précédente | A : Accueil");
	}

	private void printAllCompanies() {
		List<Optional<Company>> companies = null;

		try {
			companies = controller.getCompanies(offset, numberOfRows);
		} catch (ReadDataException exception) {
			System.out.println(exception.getMessage());
		}

		System.out.println("Entreprise");

		for (Optional<Company> company : companies) {
			String name;
			if (company.isPresent()) {
				name = company.get().getName();
			} else {
				name = "";
			}
			System.out.println(name);
		}

		System.out.println("N : Page suivante | P : Page Précédente | A : Accueil");
	}

	private void pageDetail(String query) {
		computerDetails = null;
		if (query.equals("A")) {
			actualPage = Page.HOME;
		}
		int id;
		if ((id = queryToInt(query)) > 0) {
			try {
				computerDetails = controller.getComputerByID(id).get();
			} catch (ReadDataException | ArgumentException | NoSuchElementException exception) {
				System.out.println(exception.getMessage());
			}
		}
		else {
			computerDetails = null;
		}
	}

	private void printDetailComputer() {
		if (computerDetails != null) {
			String format = "%-40s%-40s%-20s%-20s%n";
			System.out.printf(format, "Ordinateur", "Entreprise", "Date d'arrivée", "Date de fin");
			System.out.printf(format, computerDetails.getName(), computerDetails.getCompany().getName(),
					computerDetails.getIntroduced(), computerDetails.getDiscontinued());
			System.out.println("");
		}
	}

	private void printDetailPage() {
		printDetailComputer();
		System.out.println("A : Accueil");
		System.out.println("Veuillez entrer l'ID de l'ordinateur que vous souhaitez voir en détails :");
	}

	private void pageCreate(String query) {
		switch (pageIndex) {
		case 0:
			if (query.equals("A")) {
				actualPage = Page.HOME;
				break;
			}
			try {
				computerDetails = new Computer.ComputerBuilder(query).build();
			} catch (ArgumentException exception) {
				System.out.println(exception.getMessage());
			}
			pageIndex++;
			break;
		case 1:
			switch (query) {
			case "R":
				pageIndex = 0;
				break;
			case "A":
				actualPage = Page.HOME;
				break;
			default:
				int id = queryToInt(query);
				try {
					if (controller.companyExists(id)) {
						try {
							computerDetails = new Computer.ComputerBuilder(computerDetails.getName())
													.company(new Company.CompanyBuilder().id(id).build())
													.build();
							if ((id = controller.addComputer(computerDetails)) > 0) {
								computerDetails = controller.getComputerByID(id).get();
								pageIndex++;
							} else
								System.out.println("L'ordinateur n'a pas peu être ajouté...");
						} catch (ArgumentException exception) {
							System.out.println(exception.getMessage());
						}
					} else {
						System.out.println("Cette entreprise n'est pas connue...");
					}
				} catch (ReadDataException | AddDataException exception) {
					System.out.println(exception.getMessage());
				}
			}
			break;
		case 2:
			switch (query) {
			case "A":
				actualPage = Page.HOME;
				break;
			case "C":
				pageIndex = 0;
				break;
			default:
				printError(query);
			}
		}

	}

	private void printCreate() {
		switch (pageIndex) {
		case 0:
			System.out.println("A : Accueil");
			System.out.println("Veuillez entrer le nom du nouvel ordinateur");
			break;
		case 1:
			System.out.println("A : Accueil | R : Retour en arrière");
			System.out.println("Veuillez entrer l'id de l'entreprise");
			break;
		case 2:
			System.out.println("L'ordinateur a bien été créé avec l'ID :" + computerDetails.getID());
			System.out.println("C : Créer un nouvel ordinateur | A : Accueil");
		}
	}

	private void pageUpdate(String query) {
		switch (pageIndex) {
		case 0:
			try {
				int id = Integer.parseInt(query);
				Optional<Computer> computerOpt = controller.getComputerByID(id);
				if (computerOpt.isPresent()) {
					computerDetails = computerOpt.get();
					pageIndex++;
				} else {
					computerDetails = null;
					System.out.println("Cet ordinateur n'existe pas (ou plus)");
				}
			} catch (NumberFormatException exception) {
				printError(query);
			} catch (DatabaseAccessException | ArgumentException exception) {
				System.out.println(exception.getMessage());
			}
			break;
		case 1:
			QUERY_SWITCH: switch (query) {
			default:
				printError(query);
				break QUERY_SWITCH;
			case "N":
				pageIndex = 2;
				break QUERY_SWITCH;
			case "C":
				pageIndex = 3;
				break QUERY_SWITCH;
			case "R":
				pageIndex = 0;
				break QUERY_SWITCH;
			case "A":
				actualPage = Page.HOME;
			}
			break;
		case 2:
			switch (query) {
			case "":
				System.out.println("Entrée empty");
				break;
			case "A":
				pageIndex = 0;
				break;
			case "R":
				pageIndex = 1;
				break;
			default:
				try {
					controller.changeComputerName(computerDetails, query);
					computerDetails = controller.getComputerByID(computerDetails.getID()).get();
					pageIndex = 6;
				} catch(DatabaseAccessException | ArgumentException | NoSuchElementException exception) {
					System.out.println("Une erreur a été rencontrée : " + exception.getMessage());
					System.out.println("ID = " + computerDetails.getID() + " nom = " + computerDetails.getName());
					pageIndex = 0;
				}
			}
			break;
		case 3:
			switch (query) {
			case "":
				System.out.println("Entrée empty");
				break;
			case "A":
				pageIndex = 0;
				break;
			case "R":
				pageIndex = 1;
				break;
			default:
				try {
					controller.changeComputerCompany(computerDetails, queryToInt(query));
					computerDetails = controller.getComputerByID(computerDetails.getID()).get();
					pageIndex = 6;
				} catch(DatabaseAccessException | ArgumentException | NoSuchElementException exception) {
					System.out.println("Une erreur a été rencontrée : " + exception.getMessage());
					pageIndex = 0;
				}
			}
			break;
		case 4:
			switch (query) {
			case "":
				System.out.println("Entrée empty");
				break;
			case "A":
				pageIndex = 0;
				break;
			case "R":
				pageIndex = 1;
				break;
			default:
				Optional<LocalDate> localDate = stringToLocalDate(query);
				if (localDate.isPresent()) {
					try {
						controller.changeComputerName(computerDetails, query);
						computerDetails = controller.getComputerByID(computerDetails.getID()).get();
						pageIndex = 6;
					} catch(DatabaseAccessException | ArgumentException | NoSuchElementException exception) {
						System.out.println("Une erreur a été rencontrée : " + exception.getMessage());
						System.out.println("ID = " + computerDetails.getID() + " nom = " + computerDetails.getName());
						pageIndex = 0;
					}
				} else {
					System.out.println("La date écrite n'est pas au bon format :/");
				}
			}
			break;
		case 5:

			break;
		case 6:
			switch (query) {
			case "A":
				actualPage = Page.HOME;
				break;
			case "C":
				pageIndex = 0;
				break;
			default:
				printError(query);
			}
		}
	}

	private void printUpdate() {
		switch (pageIndex) {
		case 0:
			System.out.println("Veuillez entrer l'id de l'ordinateur à modifier");
			break;
		case 1:
			printDetailComputer();
			System.out.println("Quel est le champ à modifier ?");
			System.out.println("N : Nom | C : Companie | I : Introduced | D : Discontinued | R : Retour | A : Accueil");
			break;
		case 2:
			System.out.println("A : Annuler | R : Retour");
			System.out.println("Quel est le nouveau nom de l'ordinateur ?");
			break;
		case 3:
			System.out.println("A : Annuler | R : Retour");
			System.out.println("Quelle est la nouvelle entreprise pour cet ordinateur ?");
			break;
		case 4:
			System.out.println("A : Annuler | R : Retour");
			System.out.println("Entrez la nouvelle date d'introduction au format dd-mm-yyyy ?");
			break;
		case 5:
			System.out.println("A : Annuler | R : Retour");
			System.out.println("Entrez la nouvelle date de départ au format dd-mm-yyyy ?");
			break;
		case 6:
			System.out.println("L'ordinateur a bien été modifié :");
			printDetailComputer();
			System.out.println("C : Effectuer un nouveau changement | A : Accueil");
		}
	}

	private void pageDelete(String query) {
		switch (pageIndex) {
		case 0:
			if (query.equals("A")) {
				actualPage = Page.HOME;
			} else {
				try {
					if (controller.computerExists(queryToInt(query))) {
						computerDetails = controller.getComputerByID(queryToInt(query)).get();
						pageIndex++;
					} else {
						System.out.println("L'ordinateur " + query + " n'existe pas.");
					}
				} catch (DatabaseAccessException | ArgumentException | NoSuchElementException exception) {
					System.out.println("Une erreur a été rencontrée : " + exception.getMessage());
				}
			}
			break;
		case 1:
			switch (query) {
			case "A":
				actualPage = Page.HOME;
				break;
			case "R":
				pageIndex = 0;
				break;
			case "S":
				try {
					controller.deleteComputer(computerDetails.getID());
					pageIndex = 2;
				} catch(DatabaseAccessException exception) {
					System.out.println("Il y a eu une erreur à la suppression de l'ordinateur..");
					pageIndex = 0;
				}
				break;
			default:
				printError(query);
			}
			break;
		case 2:
			switch (query) {
			case "A":
				actualPage = Page.HOME;
				break;
			case "S":
				pageIndex = 0;
				break;
			default:
				printError(query);
			}
		}
	}

	private void printDelete() {
		switch (pageIndex) {
		case 0:
			System.out.println("A : Accueil");
			System.out.println("Entrez l'ID de l'ordinateur que vous souhaitez supprimer");
			break;
		case 1:
			System.out.println("A : Accueil | R : Retour | S : Supprimer DEFINITIVEMENT");
			System.out.println("Voici l'ordinateur à l'ID " + computerDetails.getID());
			printDetailComputer();
			break;
		case 2:
			System.out.println("A : Accueil | S : Supprimer un autre ordinateur");
			System.out.println("L'ordinateur a bien été supprimé de la base de données");
		}
	}

	private void printError(String query) {
		System.out.println("La valeur entrée : " + query + " n'est pas reconnue :/\n");
	}

	private int queryToInt(String query) {
		int result = 0;
		try {
			result = Integer.parseInt(query);
		} catch (NumberFormatException exception) {
			printError(query);
		}
		return result;
	}

	private Optional<LocalDate> stringToLocalDate(String query) {
		LocalDate localDate = null;
		try {
			localDate = LocalDate.parse(query, dateFormat);
		} catch (DateTimeParseException exception) {

		}
		return Optional.ofNullable(localDate);
	}
}
