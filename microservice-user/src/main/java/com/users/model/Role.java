package com.users.model;

public enum Role {

	Bibliothecaire("Bibliothécaire"),
	Utilisateur("Adhérent"),
	Administrateur("Administrateur");
	
	public String roleName;
	
	private Role(String roleName) {
		this.roleName = roleName;
	}
}
