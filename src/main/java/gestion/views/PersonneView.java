package gestion.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

import gestion.entities.Activite;
import gestion.entities.Personne;
import gestion.services.GuestService;

@Named
@ViewScoped
public class PersonneView implements Serializable {
     
    /**
	 * 
	 */
	private static final long serialVersionUID = -1777604493741695988L;
	
	@Inject
	GuestService guestService ; 
	
	
	private Personne selectedPersonne;
	private List<Personne> selectedPersonneCooptations;
	private List<Activite> selectedPersonneActivites;
	
	private int id; 
	public Personne getSelectedPersonne() {
		return selectedPersonne;
	}



	public void setSelectedPersonne(Personne selectedPersonne) {
		this.selectedPersonne = selectedPersonne;
	}



	public List<Personne> getSelectedPersonneCooptations() {
		return selectedPersonneCooptations;
	}



	public void setSelectedPersonneCooptations(List<Personne> selectedPersonneCooptations) {
		this.selectedPersonneCooptations = selectedPersonneCooptations;
	}



	public List<Activite> getSelectedPersonneActivites() {
		return selectedPersonneActivites;
	}



	public void setSelectedPersonneActivites(List<Activite> selectedPersonneActivites) {
		this.selectedPersonneActivites = selectedPersonneActivites;
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




	
	
	
    @PostConstruct
    public void init() {
    	System.out.println("I am Initiating"+id);
    	
      
    }

    
    public void onload() {
    	
    	System.out.println("Loadingg before render"+id);
    	if(this.id!=0) {
    		System.out.println("Show Persosnne");
    		selectedPersonne = guestService.getPersonById(id);
    		if(selectedPersonne!=null) {
        		selectedPersonneActivites = guestService.getPersonneActivites(selectedPersonne);
        		selectedPersonneCooptations = getCooptationsOfPerson(selectedPersonne);
        		System.out.println(selectedPersonneActivites.size());
        		System.out.println(selectedPersonneCooptations.size());
    		}
    	}else {
    		System.out.println("Choose a personne");
    	}
    }


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}
     
 
}