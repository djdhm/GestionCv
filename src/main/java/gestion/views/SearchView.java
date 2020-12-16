package gestion.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.LazyInitializationException;
import org.primefaces.model.LazyDataModel;

import gestion.entities.Activite;
import gestion.entities.NatureActivite;
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
	private Personne selectedPersonne;
	private LazyPersonneDataModel lazyListePersonne;
	private NatureActivite typeActiviteFilter;
	
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
		//listePersonnes = guestService.getAllPersonnes();
		System.out.println("Recuperer les personnne caa prends du tempss...s");
		lazyListePersonne = new LazyPersonneDataModel(guestService);	
		
	}

	public void setActivityFilter(ValueChangeEvent	 event) {
		lazyListePersonne.setFiltersHaveChanged(true);
		System.out.println(event.getNewValue());
		NatureActivite nature =(NatureActivite) event.getNewValue();
		if( nature !=null) this.lazyListePersonne.setFilter("activity" , nature.name());
		else lazyListePersonne.removeFilter("activity");
	}
	public void setDescriptionFilter(ValueChangeEvent	 event) {
		lazyListePersonne.setFiltersHaveChanged(true);
		System.out.println(event.getNewValue());
		String value  = event.getNewValue().toString();
		if( value !="") this.lazyListePersonne.setFilter("description" , value);
		else lazyListePersonne.removeFilter("description");
	}
	public void f1()
	{
		System.out.println("Fonction filter ");
		
	// somecode
	}

	public void setLastNameFilter(ValueChangeEvent event) {
		lazyListePersonne.setFiltersHaveChanged(true);
		System.out.println(event.getNewValue().toString());
		System.out.println(event.getNewValue());
		String value = event.getNewValue().toString();
		if( value !="") this.lazyListePersonne.setPersonneFilter("prenom" ,value );
		else lazyListePersonne.removePersonneFilter("prenom");
	}

	public void setEmailFilter(ValueChangeEvent event) {
		lazyListePersonne.setFiltersHaveChanged(true);
		System.out.println(event.getNewValue());
		String value = event.getNewValue().toString();
		if( value !="") this.lazyListePersonne.setPersonneFilter("email" ,value );
		else lazyListePersonne.removePersonneFilter("email");
	}

	public void setNameFilter(ValueChangeEvent event) {
		lazyListePersonne.setFiltersHaveChanged(true);
		System.out.println(event.getNewValue());
		String value = event.getNewValue().toString();
		if( value !="") this.lazyListePersonne.setPersonneFilter("nom" ,value );
		else lazyListePersonne.removePersonneFilter("nom");
	}
	public LazyDataModel<Personne> getLazyListePersonne() {
		return lazyListePersonne;
	}

	public List<Personne> getCooptationsOfPerson(Personne p) {
		if(p==null) {
			return new ArrayList();
		}
		List<Personne> coopted = new ArrayList<Personne>();
		System.err.println("On veut recup cooptation de "+p.getPrenom());
		Set<Personne> liste = guestService.getPersonsCooptationById(p);
		coopted.addAll(liste);
		return coopted;
	}


	public void setLazyListePersonne(LazyPersonneDataModel lazyListePersonne) {
		this.lazyListePersonne = lazyListePersonne;
	}


	public NatureActivite getTypeActiviteFilter() {
		return typeActiviteFilter;
	}


	public void setTypeActiviteFilter(NatureActivite typeActiviteFilter) {
		this.typeActiviteFilter = typeActiviteFilter;
	}
	public NatureActivite[] getNatures() {
		return NatureActivite.values();
	}


	public Personne getSelectedPersonne() {
		return selectedPersonne;
	}
	List <Activite> selectedPersonneActivites;
	List <Personne> selectedPersonneCooptations;

	public List<Activite> getSelectedPersonneActivites() {
		return selectedPersonneActivites;
	}


	public void setSelectedPersonneActivites(List<Activite> selectedPersonneActivites) {
		this.selectedPersonneActivites = selectedPersonneActivites;
	}


	public List<Personne> getSelectedPersonneCooptations() {
		return selectedPersonneCooptations;
	}


	public void setSelectedPersonneCooptations(List<Personne> selectedPersonneCooptations) {
		this.selectedPersonneCooptations = selectedPersonneCooptations;
	}


	public void setSelectedPersonne(Personne selectedPersonne) {
		this.selectedPersonne = guestService.getPersonById(selectedPersonne.getIdPerson());
	
		try {
			this.selectedPersonneActivites = guestService.getPersonneActivites(selectedPersonne);
			System.out.println(selectedPersonneActivites.size());
			this.selectedPersonneCooptations = this.getCooptationsOfPerson(selectedPersonne);
		}catch(LazyInitializationException e) {
			System.out.println(e);
			System.out.println("Test Lazy ");
		}
		
		
		//this.selectedPersonneCooptations = getCooptationsOfPerson(selectedPersonne);
		/*
		System.out.println("I am getting the new Personne now "+selectedPersonne.getIdPerson());
		this.selectedPersonne = guestService.getPersonById(selectedPersonne.getIdPerson());
		System.out.println("Changing Persone "+selectedPersonne.getEmail());
		System.out.println("Changing Persone "+selectedPersonne.getIdPerson());
		List<Activite> liste = this.selectedPersonne.getActivites();
		if(liste==null) {
			System.out.println("Ulach");

		}else {
			System.out.println(liste.size());

		}
		System.out.println("I am getting the new Personne now ");*/


	}
	
	private int nombre; 
	
	
	public void genererData() {
		System.out.println(nombre);
		if(nombre > 0) 	guestService.generateDate(nombre);
	}


	public int getNombre() {
		return nombre;
	}


	public void setNombre(int nombre) {
		this.nombre = nombre;
	}
	
}
