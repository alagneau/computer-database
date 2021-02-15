package fr.excilys.formation.controller;

import fr.excilys.formation.model.Model;

public class Controller {
	Model model;
	
	public Controller() {
		model = Model.getInstance();
	}
	
	public void update() {
		model = Model.getInstance();
	}
	
	
}
