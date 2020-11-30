package gestion.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import gestion.entities.Activite;
import gestion.entities.Personne;
import gestion.services.GuestService;

@Named("guestService")
@SessionScoped
public class GuestServiceControler implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/*Les critères de recherche*/
	private String nomC = "";
	private String prenomC = "";
	private String ActiviteC = "";
	
	private List<Personne> resultOfSearch;
	private List<Activite> currentCV;
	private Personne currentPerson;
	
	boolean search;
	
	@Inject
    GuestService guestService;
	
	@PostConstruct
	public void init() {
		/*On fait en sorte que ce ne soit pas null simplement*/
		resultOfSearch = guestService.getAllPersonnes();
	}
	
	public List<Personne> getAllPersonnes(){
		return guestService.getAllPersonnes();
	}
	
	public String seeCV(long id){
		currentPerson = guestService.getPersonById(id);
		currentCV = currentPerson.getActivites();
        return "showCV?faces-redirect=true";
	}
	
	public void search() {
		System.out.println("SEARCH " + nomC +" "+ prenomC +" "+ ActiviteC);
		
		search = true;
		resultOfSearch = guestService.filterPersonnes(nomC,prenomC,ActiviteC);
    }
	
	//------------- SETTERS ET GETTERS -----------------

	public String getNomC() {
		return nomC;
	}

	public void setNomC(String nomC) {
		this.nomC = nomC;
	}

	public String getPrenomC() {
		return prenomC;
	}

	public void setPrenomC(String prenomC) {
		this.prenomC = prenomC;
	}

	public String getActiviteC() {
		return ActiviteC;
	}

	public void setActiviteC(String activiteC) {
		ActiviteC = activiteC;
	}

	public List<Personne> getResultOfSearch() {
		
		if(!search)
			return guestService.getAllPersonnes();
		else
			return resultOfSearch;
	}


	public List<Activite> getCurrentCV() {
		return currentCV;
	}

	public void setCurrentCV(List<Activite> currentCV) {
		this.currentCV = currentCV;
	}

	public Personne getCurrentPerson() {
		return currentPerson;
	}

	public void setCurrentPerson(Personne currentPerson) {
		this.currentPerson = currentPerson;
	}
	
	
	
}
