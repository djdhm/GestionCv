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
import gestion.services.AccessInterditException;
import gestion.services.UserService;

@Named
@ViewScoped
public class UserSpaceView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	UserService userService;

	private Activite nouvelleActivite;
	private boolean isNewActivite;
	private List<Activite> activities;
	private NatureActivite[] natures = NatureActivite.values();
	private Personne personne;
	private boolean ajout;
	private Personne signingUpPerson = new Personne();
	
	
	
	@PostConstruct
	public void init() throws IOException {
		
		if(userService.isLoggedIn()) {
			try {
				this.personne = userService.getPersonne();
				activities = personne.getActivites();
				nouvelleActivite = new Activite();
				isNewActivite = false;
				setAjout(false);
			}catch(Exception e ) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();
				externalContext.redirect("login.xhtml?redirect-to=loggedUserServices.xhtml");
			}
		}else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.redirect("login.xhtml?redirect-to=loggedUserServices.xhtml");
		}
	}


	public Personne getPersonne() {
		return personne;
	}
	
	
	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.invalidateSession();
		return "login.xhtml";
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
		try {
			userService.addActivite(nouvelleActivite);
			nouvelleActivite = new Activite();
		}catch(AccessInterditException e) {
			System.out.println("Vous netes pas connecete");
		}
	}
	
	public void onRowEdit(RowEditEvent<Activite> event) {
		try {
			userService.updateActivite(event.getObject());

		}catch(AccessInterditException exception) {
			FacesMessage msg = new FacesMessage("Erreur de modification", event.getObject().getSiteWeb());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		FacesMessage msg = new FacesMessage("Activite Modifie", event.getObject().getSiteWeb());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent<Activite> event) {

        FacesMessage msg = new FacesMessage("Modification Annullee", event.getObject().getSiteWeb());
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


	public boolean isAjout() {
		return ajout;
	}


	public void setAjout(boolean ajout) {
		this.ajout = ajout;
	}


	public Personne getSigningUpPerson() {
		return signingUpPerson;
	}


	public void setSigningUpPerson(Personne signingUpPerson) {
		this.signingUpPerson = signingUpPerson;
	}

}	
