package com.books.model;

public enum State {
	
	EnCours("Prêt en cours"),
	EnRetard("Prêt en retard"),
	Termine("Prêt terminé"),
	Renouvele("Prêt renouvelé");
	
	public String stateName;

	private State(String stateName) {
		this.stateName = stateName;
	}
	
}
