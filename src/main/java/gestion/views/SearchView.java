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
	private boolean filtersHaveChanged = false;
	
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
			System.out.println("WHAAAAAAAAAAAAAAAAAAAAt");
			return new ArrayList();
		}
		List<Personne> coopted = new ArrayList<Personne>();
		System.err.println("On veut recup cooptation de "+p.getPrenom());
		for(Long id : guestService.getPersonsCooptationById(p.getIdPerson())) {
			coopted.add(guestService.getPersonById(id));
		}
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


	public void setSelectedPersonne(Personne selectedPersonne) {
		
		this.selectedPersonne = selectedPersonne;
		System.out.println("Changing Persone "+selectedPersonne.getEmail());
		List<Activite> liste = this.selectedPersonne.getActivites();
		System.out.println(liste.size());
		for(Activite a: liste) {
			System.out.println(a.getNature());
		}
	}
}
