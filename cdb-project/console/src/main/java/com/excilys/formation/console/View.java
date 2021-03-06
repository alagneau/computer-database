package com.excilys.formation.console;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.formation.dto.mapper.ComputerDTOMapper;
import com.excilys.formation.dto.model.ComputerDTOViewDashboard;
import com.excilys.formation.exception.AddDataException;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.DatabaseAccessException;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.logger.CDBLogger;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.ListPage;

@Component
@Scope("prototype")
public class View {
	private static final int DEFAULT_NUMBER_OF_VALUES = 10;
	private static final int DEFAULT_PAGE_INDEX = 1;
	private static final int DEFAULT_PAGE_MENU = 0;
	private static final String BASE_URL = "http://localhost:8080/webapp";
	
	@Autowired
	private Controller controller;
	private int sousMenu = 0;
	private ListPage listPage = new ListPage.ListPageBuilder().index(DEFAULT_PAGE_INDEX).numberOfValues(DEFAULT_NUMBER_OF_VALUES).build();
	private Computer computerDetails = null;
	private Company companyDetails = null;
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private CDBLogger logger = new CDBLogger(View.class);
//	private Client client;
	private WebClient client;

	private enum Page {
		HOME("0"), COMPUTERS("1"), COMPANIES("2"), DETAIL("3"), CREATE("4"), UPDATE("5"), DELETE_COMPUTER("6"),
		DELETE_COMPANY("7");

		public final String label;
		public static final Map<String, Page> ASSOCIATION = new HashMap<>();

		static {
			for (Page page : values()) {
				ASSOCIATION.put(page.label, page);
			}
		}

		Page(String label) {
			this.label = label;
		}
	};

	private Page actualPage = Page.HOME;

	public View() {
		System.out.println("Bienvenue sur MyComputerDatabase.com !\n\n");
		logger.info("CREATION CLIENT");
//		client = Client.create(new DefaultClientConfig());
		List<Object> providers = new ArrayList<>();
		providers.add(new JacksonJaxbJsonProvider());
		client = WebClient.create(BASE_URL, providers);
		logger.info("CONNECTION LOGGIN");
//		client.addFilter(new HTTPBasicAuthFilter("user", "123456"));
		client.header("Authorization", "Basic " + Base64Utility.encode("admin:123456".getBytes()));
		logger.info("CONNECTION TERMINEE");
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
		case DELETE_COMPUTER:
			pageDeleteComputer(query);
			break;
		case DELETE_COMPANY:
			pageDeleteCompany(query);
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
		case DELETE_COMPUTER:
			printDeleteComputer();
			break;
		case DELETE_COMPANY:
			printDeleteCompany();
		}
	}

	private int pageHome(String query) {
		listPage.setPageIndex(DEFAULT_PAGE_INDEX);
		sousMenu = DEFAULT_PAGE_MENU;
		computerDetails = null;
		if (query.equals("q") || query.equals("Q")) {
			System.out.println("\n\nAu plaisir de vous revoir !");
			return 0;
		} else if (Page.ASSOCIATION.containsKey(query)) {
			actualPage = Page.ASSOCIATION.get(query);
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
		System.out.println("7 : Supprimer une entreprise");

	}

	private void pageListDatas(String query) {
		try {
			listPage.setMaxComputers((actualPage == Page.COMPUTERS) ? (int) controller.numberOfComputers()
					: (int) controller.numberOfCompanies());
		} catch (DatabaseAccessException exception) {
			System.out.println(exception.getMessage());
		}
		switch (query) {
			case "P":
				listPage.setPageIndex(listPage.getIndex() - 1);
				System.out.println("Infos précedentes\n");
				break;
			case "N":
				listPage.setPageIndex(listPage.getIndex() + 1);
				System.out.println("Infos suivantes\n");
				break;
			case "A":
				actualPage = Page.HOME;
				System.out.println("Switch to HOME");
				break;
			default:
				printError(query);
		}
	}

	private void printAllComputers() {
		List<Computer> computers = null;

//	try {
//		computers = controller.getComputerPage(listPage);
		Map<String, Object> params = new HashMap<>();
		params.put("pageIndex", listPage.getIndex());
		params.put("numberOfValues", listPage.getNumberOfValues());
		params.put("search", listPage.getSearchValue());
		
		logger.info("DEMANDE DE REQUETE");
		WebClient request = client.resetQuery().replacePath("/computers");
		for (Entry<String, Object> entry : params.entrySet()) {
			request.query(entry.getKey(), entry.getValue());
		}
		
		System.out.println("REQUETE : " + request.getCurrentURI());
		
		List<ComputerDTOViewDashboard> values = request.accept(MediaType.APPLICATION_JSON).get(new GenericType<List<ComputerDTOViewDashboard>>() {});
				
		computers = values.stream().map(c -> {
			try {
				return ComputerDTOMapper.dtoViewDashboardToComputer(c);
			} catch (ArgumentException exception) {
				System.out.println(exception.getMessage());
				return null;
			}
		}).collect(Collectors.toList());
		
//		List<Computer> computers = null;
//
//		logger.info("DEMANDE DE REQUETE");
//		WebResource resource = client.resource(BASE_URL).path("/computers");
//
//		resource.queryParam("pageIndex", "1");
//		resource.queryParam("numberOfValues", "10");
//		resource.queryParam("search", "");
//
//		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//		List<ComputerDTOViewDashboard> values = response.getEntity(new GenericType<List<ComputerDTOViewDashboard>>() {});
//		
//		computers = values.stream().map(c -> {
//			try {
//				return ComputerDTOMapper.dtoViewDashboardToComputer(c);
//			} catch (ArgumentException exception) {
//				System.out.println(exception.getMessage());
//				return null;
//			}
//		}).collect(Collectors.toList());
//		} catch (ReadDataException | ArgumentException exception) {
//			System.out.println(exception.getMessage());
//		}

		System.out.println(Computer.HEADER);

		for (Computer computer : computers) {
			if (computer != null) {
				System.out.println(computer.toString());
			} else {
				System.out.println("");
			}
		}

		System.out.println("N : Page suivante | P : Page Précédente | A : Accueil");
	}

	private void printAllCompanies() {
		List<Company> companies = null;

		try {
			companies = controller.getCompanyPage(listPage);
		} catch (ReadDataException exception) {
			System.out.println(exception.getMessage());
		}

		System.out.println("Entreprise");

		for (Company company : companies) {
			String name;
			if (company != null) {
				name = company.getName();
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
		} else {
			computerDetails = null;
		}
	}

	private void printDetailComputer() {
		if (computerDetails != null) {
			String format = "%-40s%-40s%-20s%-20s%n";
			System.out.printf(format, "Ordinateur", "Entreprise", "Date d'arrivée", "Date de fin");
			String companyName = computerDetails.getCompany() != null ? computerDetails.getCompany().getName() : null;
			
			System.out.printf(format, computerDetails.getName(), companyName,
					computerDetails.getIntroduced(), computerDetails.getDiscontinued());
			System.out.println("");
		}
	}

	private void printDetailCompany() {
		if (companyDetails != null) {
			String format = "%-40s%-20s%n";
			System.out.printf(format, "id", "Company");
			System.out.printf(format, companyDetails.getId(), companyDetails.getName());
			System.out.println("");
		}
	}

	private void printDetailPage() {
		printDetailComputer();
		System.out.println("A : Accueil");
		System.out.println("Veuillez entrer l'ID de l'ordinateur que vous souhaitez voir en détails :");
	}

	private void pageCreate(String query) {
		switch (sousMenu) {
		case 0:
			if (query.equals("A")) {
				actualPage = Page.HOME;
				break;
			}
			try {
				computerDetails = new Computer.ComputerBuilder("").build();
			} catch (ArgumentException exception) {
				logger.error(exception.getMessage());
			}
			sousMenu = 1;
			break;
		case 1:
			switch (query) {
			case "R":
				sousMenu = DEFAULT_PAGE_MENU;
				break;
			case "A":
				actualPage = Page.HOME;
				break;
			default:
				long id = queryToInt(query);
				try {
					if (controller.companyExists(id)) {
						try {
							computerDetails.setCompany(new Company.CompanyBuilder().id(id).name("").build());
							if ((id = controller.addComputer(computerDetails)) > 0) {
								computerDetails = controller.getComputerByID(id).get();
								sousMenu = 2;
							} else {
								System.out.println("L'ordinateur n'a pas peu être ajouté...");
							}
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
				sousMenu = DEFAULT_PAGE_MENU;
				break;
			default:
				printError(query);
			}
		}

	}

	private void printCreate() {
		switch (sousMenu) {
		case 0:
			System.out.println("A : Accueil");
			System.out.println("Veuillez entrer le nom du nouvel ordinateur");
			break;
		case 1:
			System.out.println("A : Accueil | R : Retour en arrière");
			System.out.println("Veuillez entrer l'id de l'entreprise");
			break;
		case 2:
			System.out.println("L'ordinateur a bien été créé avec l'ID :" + computerDetails.getId());
			System.out.println("C : Créer un nouvel ordinateur | A : Accueil");
		}
	}

	private void pageUpdate(String query) {
		switch (sousMenu) {
		case 0:
			try {
				int id = Integer.parseInt(query);
				Optional<Computer> computerOpt = controller.getComputerByID(id);
				if (computerOpt.isPresent()) {
					computerDetails = computerOpt.get();
					sousMenu = 1;
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
				sousMenu = 2;
				break QUERY_SWITCH;
			case "C":
				sousMenu = 3;
				break QUERY_SWITCH;
			case "R":
				sousMenu = DEFAULT_PAGE_MENU;
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
				sousMenu = DEFAULT_PAGE_MENU;
				break;
			case "R":
				sousMenu = 1;
				break;
			default:
				try {
					controller.changeComputerName(computerDetails, query);
					computerDetails = controller.getComputerByID(computerDetails.getId()).get();
					sousMenu = 6;
				} catch (DatabaseAccessException | ArgumentException | NoSuchElementException exception) {
					System.out.println("Une erreur a été rencontrée : " + exception.getMessage());
					System.out.println("ID = " + computerDetails.getId() + " nom = " + computerDetails.getName());
					sousMenu = DEFAULT_PAGE_MENU;
				}
			}
			break;
		case 3:
			switch (query) {
			case "":
				System.out.println("Entrée empty");
				break;
			case "A":
				sousMenu = DEFAULT_PAGE_MENU;
				break;
			case "R":
				sousMenu = 1;
				break;
			default:
				try {
					controller.changeComputerCompany(computerDetails, controller.getCompanyByID((long) queryToInt(query)).get());
					computerDetails = controller.getComputerByID(computerDetails.getId()).get();
					sousMenu = 6;
				} catch (DatabaseAccessException | ArgumentException | NoSuchElementException exception) {
					System.out.println("Une erreur a été rencontrée : " + exception.getMessage());
					sousMenu = DEFAULT_PAGE_MENU;
				}
			}
			break;
		case 4:
			switch (query) {
			case "":
				System.out.println("Entrée empty");
				break;
			case "A":
				sousMenu = DEFAULT_PAGE_MENU;
				break;
			case "R":
				sousMenu = 1;
				break;
			default:
				Optional<LocalDate> localDate = stringToLocalDate(query);
				if (localDate.isPresent()) {
					try {
						controller.changeComputerName(computerDetails, query);
						computerDetails = controller.getComputerByID(computerDetails.getId()).get();
						sousMenu = 6;
					} catch (DatabaseAccessException | ArgumentException | NoSuchElementException exception) {
						System.out.println("Une erreur a été rencontrée : " + exception.getMessage());
						sousMenu = DEFAULT_PAGE_MENU;
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
				sousMenu = DEFAULT_PAGE_MENU;
				break;
			default:
				printError(query);
			}
		}
	}

	private void printUpdate() {
		switch (sousMenu) {
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

	private void pageDeleteComputer(String query) {
		switch (sousMenu) {
		case 0:
			if (query.equals("A")) {
				actualPage = Page.HOME;
			} else {
				try {
					if (controller.computerExists(queryToInt(query))) {
						computerDetails = controller.getComputerByID(queryToInt(query)).get();
						sousMenu = 1;
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
				sousMenu = DEFAULT_PAGE_MENU;
				break;
			case "S":
				try {
					controller.deleteComputer(Arrays.asList(computerDetails.getId()));
					sousMenu = 2;
				} catch (DatabaseAccessException exception) {
					System.out.println("Il y a eu une erreur à la suppression de l'ordinateur..");
					sousMenu = DEFAULT_PAGE_MENU;
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
				sousMenu = DEFAULT_PAGE_MENU;
				break;
			default:
				printError(query);
			}
		}
	}

	private void printDeleteComputer() {
		switch (sousMenu) {
		case 0:
			System.out.println("A : Accueil");
			System.out.println("Entrez l'ID de l'ordinateur que vous souhaitez supprimer");
			break;
		case 1:
			System.out.println("A : Accueil | R : Retour | S : Supprimer DEFINITIVEMENT");
			System.out.println("Voici l'ordinateur à l'ID " + computerDetails.getId());
			printDetailComputer();
			break;
		case 2:
			System.out.println("A : Accueil | S : Supprimer un autre ordinateur");
			System.out.println("L'ordinateur a bien été supprimé de la base de données");
		}
	}

	private void pageDeleteCompany(String query) {
		switch (sousMenu) {
		case 0:
			if (query.equals("A")) {
				actualPage = Page.HOME;
			} else {
				try {
					if (controller.companyExists(queryToInt(query))) {
						companyDetails = controller.getCompanyByID(queryToInt(query)).get();
						sousMenu = 1;
					} else {
						System.out.println("L'entreprise n°" + query + " n'existe pas.");
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
				sousMenu = DEFAULT_PAGE_MENU;
				break;
			case "S":
				try {
					controller.deleteCompany(companyDetails);
					sousMenu = 2;
				} catch (DatabaseAccessException exception) {
					System.out.println("Il y a eu une erreur à la suppression de l'entreprise..");
					sousMenu = DEFAULT_PAGE_MENU;
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
				sousMenu = DEFAULT_PAGE_MENU;
				break;
			default:
				printError(query);
			}
		}
	}

	private void printDeleteCompany() {
		switch (sousMenu) {
		case 0:
			System.out.println("A : Accueil");
			System.out.println("Entrez l'ID de l'entreprise que vous souhaitez supprimer");
			break;
		case 1:
			System.out.println("A : Accueil | R : Retour | S : Supprimer DEFINITIVEMENT");
			System.out.println("Voici l'entreprise à supprimer :");
			printDetailCompany();
			break;
		case 2:
			System.out.println("A : Accueil | S : Supprimer une autre entreprise");
			System.out.println("L'entreprise a bien été supprimée de la base de données");
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
			localDate = LocalDate.parse(query, DATE_FORMAT);
		} catch (DateTimeParseException exception) {

		}
		return Optional.ofNullable(localDate);
	}
}
