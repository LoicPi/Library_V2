package com.books.model;

public enum State {
	
	EnCours("Prêt en cours"),
	EnRetard("Prêt en retard"),
	Termine("Prêt terminé"),
	Renouvele("Prêt renouvelé"),	
	EnAttente("Réservation en attente"),
	Finie("Réservation terminé"),
	AnnuleU("Réservation annulé par l'utilisateur"),
	AnnuleM("Réservation annulé suite mail");	
	
	public String stateName;

	private State(String stateName) {
		this.stateName = stateName;
	}
	
}
