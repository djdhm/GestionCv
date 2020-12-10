package gestion.views;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

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
		NatureActivite nature =(NatureActivite) event.getNewValue();
		if( nature !=null) this.lazyListePersonne.setFilter("description" , nature.name());
		else lazyListePersonne.removeFilter("description");
	}
	public void f1()
	{
		System.out.println("Fonction filter ");
		
	// somecode
	}


	public LazyDataModel<Personne> getLazyListePersonne() {
		return lazyListePersonne;
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
}
