package gestion.services;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.inject.Inject;

import gestion.dao.IActiviteDao;
import gestion.dao.IPersonneDAO;
import gestion.entities.Activite;
import gestion.entities.Personne;

@Stateful
public class UserService {

	
	
	@Inject
	IPersonneDAO personneDao;
	@Inject
	IActiviteDao activiteDao;
	
	
	private boolean loggedIn;
	private Personne personne;
	
	private Activite editedActivity;
	
	public boolean authentify(String username,String password) {
	 
		System.out.println("I.m trying to login");
		Personne p = personneDao.getPersonneByEmail(username);
		if(p == null) {
			System.out.println("--------Problème login : p est null...");
			return false; 
		}
		if(p.verifyPassword(password)) {
			loggedIn=true;
			this.personne = p;
			System.out.println("--------Login SUCCESS");
			System.out.println(personne.getNom() +" "+ personne.getPrenom() + " s'est loggé");
			System.out.println("Objet personne = "+personne);
			return true;
		}
		System.out.println("--------Problème login : le psw " + password + " est mauvais");
		return false;
	}
	
	public String login(String username,String password) {
		 
		//TODO: faire un logout ici au cas où une personne ait 2 compte ??
		
		if(authentify(username, password))
			return "loggedUserServices?faces-redirect=true";
		else
			return "login";
	}
	
	@Remove
	public String logout() {
		if(loggedIn) {
			loggedIn=false;
			System.out.println(personne.getNom() +" "+ personne.getPrenom() + " s'est déconnecté");
			personne=null;
			return "search?faces-redirect=true";
		}else {
			System.out.println("WTF je suis pas co...");
			return "search?faces-redirect=true";
			//throw new IllegalAccessError();
		}
	}
	
	public void addPersonne(Personne p) throws AccessInterditException {
		System.out.println(this.personne);
		System.out.println(this.loggedIn);
		if(loggedIn) {
			personneDao.createPersonne(p);
			this.personne.addCooptation(p);
			personneDao.updatePerson(p);
			
		}else throw new AccessInterditException("test");
	}
	
	public void addActivite(Activite activite) throws AccessInterditException {
		if(loggedIn) {
			
			this.activiteDao.saveActivite(activite);
			this.personne.addActivite(activite);
			this.personneDao.updatePerson(personne);
			
		}else throw new AccessInterditException("adding activite");
	}
	
	public String editActivity() {
		if(loggedIn) {
			editedActivity = new Activite();
			return "editActivity?faces-redirect=true";
		}else {
			System.out.println("WTF je suis pas co...");
			return "search?faces-redirect=true";
			//throw new IllegalAccessError();
		}
	}
	
	public List<Personne> getCoptations() throws AccessInterditException {
		if(loggedIn) {
			return this.personne.getCoptations();
			
			
		}else throw new AccessInterditException("adding activite");
	}
	public String getEmail() {
		if(loggedIn) return this.personne.getEmail();
		return null;
	}

	public List<Activite> getActivities() {
		// TODO Auto-generated method stub
		if(loggedIn) 		return this.personne.getActivities();
        return null;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public Activite getEditedActivity() {
		return editedActivity;
	}

	public void setEditedActivity(Activite editedActivity) {
		this.editedActivity = editedActivity;
	}
	
	
	
}
