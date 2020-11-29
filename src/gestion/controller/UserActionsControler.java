package gestion.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import gestion.dao.IPersonneDAO;
import gestion.entities.Personne;
import gestion.services.UserService;

@Named("userActions")
@SessionScoped
public class UserActionsControler implements Serializable{

	private static final long serialVersionUID = 1L;

    @Inject
    UserService userService;

   @Inject
	IPersonneDAO personneDao;
   
   /* Champs qu'on utilise pour le login */
   private String pwd = "";
   private String mail = "";
   
   Personne personne;
   
   @PostConstruct
   public void init() {
	}
	
	/*Sert à l'affichage des données persos dans espace perso*/
   public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}
	
	public String logout() {
		String result = userService.logout();
		personne = null;
		return result;
	}
		
		public String login(String username,String password) {
			
			String result = userService.login(username, password);
			personne = userService.getPersonne();
			return result;
		}

	

		public String getPwd() {
			return pwd;
		}

		public void setPwd(String pwd) {
			this.pwd = pwd;
		}

		public String getMail() {
			return mail;
		}

		public void setMail(String mail) {
			this.mail = mail;
		}
	
}
