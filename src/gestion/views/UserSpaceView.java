package gestion.views;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import gestion.entities.Activite;
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

	
	private List<Activite> activities;
	private Personne personne;
	
	
	@PostConstruct
	public void init() throws IOException {
		
		if(UserService.isLoggedIn()) {
			this.personne = UserService.getPersonne();
			activities = personne.getActivites();
			
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

}
