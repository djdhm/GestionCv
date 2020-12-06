package gestion.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import gestion.dao.IPersonneDAO;
import gestion.entities.Personne;
import gestion.services.GuestService;
import gestion.services.UserService;

@Named("signup")
@SessionScoped
public class SignUpControler implements Serializable{
	private static final long serialVersionUID = 1L;

    @Inject 
    GuestService guestService;

   
   private Date maxDate = new Date();
   /* Objet qu'on utilise pour l'inscription */
   Personne signingUpPerson;
   
	@PostConstruct
	public void init() {
		/*On instancie un objet personne pour la personne qui s'inscrit*/
		signingUpPerson  = new Personne();
		
	}

	public String save() {
		if(guestService.verifyExistingEmail(signingUpPerson.getEmail())) {
			FacesContext context = FacesContext.getCurrentInstance();
			UIInput input = (UIInput) context.getViewRoot().findComponent("inscription:email");
			input.setValid(false);
			context.addMessage(input.getClientId(context), new FacesMessage("Email Already Exists"));
			context.validationFailed();
			return "";
		}
		else {
			System.out.println(signingUpPerson.getPrenom() +" est en train de s'inscrire");
			guestService.signup(this.signingUpPerson);
			signingUpPerson = new Personne();
			return "search?faces-redirect=true"; /*Pour l'instant on retourne à ça et non l'espace perso ??*/
		}
		
	}
	
	public Personne getSigningUpPerson() {
		return signingUpPerson;
	}

	public Date getMaxAge() {
	    Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.YEAR, -18);
        return currentDate.getTime();
	}
	public Date getMinAge() {
	    Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.YEAR, -100);
        return currentDate.getTime();
	}
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
	
	
	
}
