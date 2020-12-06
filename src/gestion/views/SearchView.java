package gestion.views;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import gestion.entities.Personne;
import gestion.services.GuestService;

@Named
@ViewScoped
public class SearchView implements Serializable {

	private static final long serialVersionUID = 1L;


	@Inject
	GuestService guestService;

	private List<Personne> listePersonnes;
	private List<Personne> filteredPersonnes;

	
	public List<Personne> getFilteredPersonnes() {
		return filteredPersonnes;
	}


	public void setFilteredPersonnes(List<Personne> filteredPersonnes) {
		this.filteredPersonnes = filteredPersonnes;
	}


	public List<Personne> getListePersonnes() {
		return listePersonnes;
	}


	public void setListePersonnes(List<Personne> listePersonnes) {
		this.listePersonnes = listePersonnes;
	}


	@PostConstruct
	public void init() {
		listePersonnes = guestService.getAllPersonnes();
	}

	
	public void f1()
	{
		System.out.println("Keyup");
	// somecode
	}
}
