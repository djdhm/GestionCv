package gestion.views;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import gestion.entities.Activite;
import gestion.entities.NatureActivite;
import gestion.entities.Personne;
import gestion.services.UserService;

@Named
@ViewScoped
public class UserSpaceView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	UserService UserService;

	private Activite nouvelleActivite;
	private boolean isNewActivite;
	private List<Activite> activities;
	private NatureActivite[] natures = NatureActivite.values();
	private Personne personne;
	
	
	@PostConstruct
	public void init() throws IOException {
		
		if(UserService.isLoggedIn()) {
			this.personne = UserService.getPersonne();
			activities = personne.getActivites();
			nouvelleActivite = new Activite();
			isNewActivite = false;
		}else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.redirect("login.xhtml?redirect-to=loggedUserServices.xhtml");
		}
	}


	public Personne getPersonne() {
		return personne;
	}


	public List<Activite> getActivities() {
		return activities;
	}


	public NatureActivite[] getNatures() {
		return NatureActivite.values();
	}


	public Activite getNouvelleActivite() {
		return nouvelleActivite;
	}


	public void setNouvelleActivite(Activite nouvelleActivite) {
		this.nouvelleActivite = nouvelleActivite;
	}
	public void ajouterActivite() {
		System.out.println("Test");
		this.activities.add(new Activite());
		isNewActivite = true;
	}
	
	public void onRowEdit(RowEditEvent<Activite> event) {
        FacesMessage msg = new FacesMessage("Car Edited", event.getObject().getSiteWeb());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent<Activite> event) {

        FacesMessage msg = new FacesMessage("Edit Cancelled", event.getObject().getSiteWeb());
        if(isNewActivite) {
        	System.out.println(activities.size());
        	activities.remove(event.getObject());
        	System.out.println(activities.size());
        	isNewActivite = false;
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }
     
    public void onCellEdit(CellEditEvent<Personne> event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }


	public boolean isNewActivite() {
		return isNewActivite;
	}


	public void setNewActivite(boolean isNewActivite) {
		this.isNewActivite = isNewActivite;
	}

}
