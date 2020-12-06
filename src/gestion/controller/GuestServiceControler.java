package gestion.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import antlr.debug.GuessingEvent;
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

	/* Booléen pour savoir si on on remplit resultOfSearch avec toutes les personnes OU avec les personnes respectant les critères de recherche */
	boolean search;
	
	@Inject
    GuestService guestService;
	
	private List<Personne> filteredPersonnes = new ArrayList<Personne>();
	private List<Personne> allPersonnes;
	public List<Personne> getFilteredPersonnes() {
		System.out.println("TEEST Filtering get");
		return filteredPersonnes;
	}

	public void setFilteredPersonnes(List<Personne> filteredPersonnes) {
		System.out.println("TEEST Filtering set ");

		this.filteredPersonnes = filteredPersonnes;
	}

	@PostConstruct
	public void init() {
		/*On fait en sorte que ce ne soit pas null simplement*/
		allPersonnes = guestService.getAllPersonnes();
		
	}
	
	public List<Personne> getAllPersonnes(){
		if(allPersonnes == null) {
			allPersonnes = guestService.getAllPersonnes();
		}
		System.out.println(allPersonnes.size());
		return allPersonnes;
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

	/*Si aucun critère de recherche, on retourne toutes les personnes*/
	public List<Personne> getResultOfSearch() {
		
		System.out.println(guestService.getAllPersonnes().size());
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
	
	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		System.out.println("Filtrage globale");
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        int filterInt = getInteger(filterText);
        
        Personne p = (Personne) value;
        return p.getIdPerson().toString().contains(filterText)
                || p.getNom().toLowerCase().contains(filterText)
                || p.getPrenom().toLowerCase().contains(filterText)
                || p.getDateNaissance().getYear() < filterInt;

    }

	  private int getInteger(String string) {
	        try {
	            return Integer.valueOf(string);
	        }
	        catch (NumberFormatException ex) {
	            return 0;
	        }
	    }
}
