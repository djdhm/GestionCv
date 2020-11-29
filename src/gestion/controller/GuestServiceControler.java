package gestion.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import gestion.entities.Personne;
import gestion.services.GuestService;

@Named("guestService")
@ViewScoped
public class GuestServiceControler implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/*Les critères de recherche*/
	private String nomC = "";
	private String prenomC = "";
	private String ActiviteC = "";
	
	private List<Personne> resultOfSearch;
	
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
        return guestService.seeCV(id);
	}
	
	public void search() {
		System.out.println("SEARCH " + nomC +" "+ prenomC +" "+ ActiviteC);
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
		return resultOfSearch;
	}

	public void setResultOfSearch(List<Personne> resultOfSearch) {
		this.resultOfSearch = resultOfSearch;
	}
	
	
	
}
