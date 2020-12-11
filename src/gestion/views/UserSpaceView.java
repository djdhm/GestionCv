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
import org.primefaces.event.CloseEvent;
import org.primefaces.event.MoveEvent;
import org.primefaces.event.RowEditEvent;

import gestion.entities.Activite;
import gestion.entities.NatureActivite;
import gestion.entities.Personne;
import gestion.services.AccessInterditException;
import gestion.services.GuestService;
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
	
	@Inject
	GuestService guestService;

	private Activite nouvelleActivite;
	private boolean isNewActivite;
	private List<Activite> activities;
	private List<Personne> cooptations; // Liste des personnes recommandables
	private Personne nouvelleCooptation;
	private NatureActivite[] natures = NatureActivite.values();
	private Personne personne;
	private boolean ajout;
	
	
	
	@PostConstruct
	public void init() throws IOException {
		
		if(userService.isLoggedIn()) {
			try {
				this.personne = userService.getPersonne();
				activities = personne.getActivites();
				nouvelleActivite = new Activite();
				nouvelleCooptation = new Personne();
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
	
	public void handleClose(CloseEvent event) {
			System.out.println("Test annule");
    }
     
    public void handleMove(MoveEvent event) {
		System.out.println("Move");

    }
     
	public void supprimerActivite(Activite activite) {
		try {
			userService.deleteActivity(activite);
			 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Activite Supprim�e", "");
		        FacesContext.getCurrentInstance().addMessage(null, message);
		}catch(AccessInterditException e) {
			System.out.println("Vous netes pas connecete");

		}
	}
	
	//On modifie les données persos
    public void onModifyPersosDatas() {

    	userService.updatePerson(personne);
    	System.out.println("Nouveau nom : "+personne.getNom());
    	//TODO: faire de la validation ??
        FacesMessage msg = new FacesMessage("Infos Persos modifié");
        FacesContext.getCurrentInstance().addMessage(null, msg);

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
        	//WHAT ???
        }
        
    }
    
    //DEBUT POUR COOPTATION
    
    public void ajouterCooptation() {
		try {
			System.err.println("--On coopte "+nouvelleCooptation.getPrenom()+" à "+userService.getPersonne().getPrenom());
			userService.addCooptation(nouvelleCooptation);
			nouvelleCooptation = new Personne();

			System.err.println("Voici maintenant toutes les personnes inscrites et issus de la cooptation");
			for(Personne p : guestService.getAllPersonnes())
				System.err.println(p.getIdPerson()+" "+p.getPrenom());
		}catch(AccessInterditException e) {
			System.out.println("Vous netes pas connecte");
		}
	}
    
    public void onRowCooptEdit(RowEditEvent<Personne> event) {
		try {
			userService.updatePerson(event.getObject());

		}catch(Exception exception) {
			FacesMessage msg = new FacesMessage("Erreur de modification cooptation", event.getObject().getNom());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		FacesMessage msg = new FacesMessage("Cooptation Modifie", event.getObject().getNom());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCooptCancel(RowEditEvent<Personne> event) {

        FacesMessage msg = new FacesMessage("Modification Annullee", event.getObject().getNom());
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }
     
    public void onCellCooptEdit(CellEditEvent<Personne> event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
        	//WHAT ???
        }
        
    }
    
    public void supprimerCooptation(Personne pers) {
		try {
			personne.getCooptations().removeIf(p -> pers.getIdPerson().equals(p.getIdPerson()));
			
			 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cooptation Supprim�e", "");
		        FacesContext.getCurrentInstance().addMessage(null, message);
		}catch(Exception e) {
			System.out.println("Vous netes pas connecete");

		}
	}

  //FIN POUR COOPTATION

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


	public List<Personne> getCooptations() {
		return cooptations;
	}


	public void setCooptations(List<Personne> cooptations) {
		this.cooptations = cooptations;
	}


	public Personne getNouvelleCooptation() {
		return nouvelleCooptation;
	}


	public void setNouvelleCooptation(Personne nouvelleCooptation) {
		this.nouvelleCooptation = nouvelleCooptation;
	}


	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	
}	
