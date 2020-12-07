package gestion.services;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import gestion.dao.IActiviteDao;
import gestion.dao.IPersonneDAO;
import gestion.entities.Activite;
import gestion.entities.Personne;

@Stateful
@SessionScoped
public class UserService {

	
	
	@Inject
	IPersonneDAO personneDao;
	@Inject
	IActiviteDao activiteDao;
	
	
	private boolean loggedIn;
	private Personne personne;
	
	private Activite editedActivity;

	/*On vérifie si l'authentification est correcte */
	public boolean authentify(String username,String password) {
		Personne p = personneDao.getPersonneByEmail(username);
		if(p == null) {
			System.out.println("Aucun compte n'est associé à "+username);
			return false; 
		}
		if(p.verifyPassword(password)) {
			loggedIn=true;
			this.personne = p;
			System.out.println(personne.getNom() +" "+ personne.getPrenom() + " s'est loggé");
			return true;
		}
		System.out.println("--------Problème login : le psw " + password + " est mauvais");
		return false;
	}
	
	public Personne login(String username,String password) {
		 
		//TODO: faire un logout ici au cas où une personne ait 2 compte ??
		authentify(username, password);
		return this.personne;
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

	//TODO: Pour la cooptation, j'ai pas compris quoi en faire ^^
	public void addPersonne(Personne p) throws AccessInterditException {
		System.out.println(this.personne);
		System.out.println(this.loggedIn);
		if(loggedIn) {
			personneDao.savePersonne(p);
			this.personne.addCooptation(p);
			personneDao.savePersonne(p);
			
		}else throw new AccessInterditException("test");
	}

	/* Ajoute une activité à la personne */
	public void addActivite(Activite activite) throws AccessInterditException {
		if(loggedIn) {
			
			this.activiteDao.saveActivite(activite);
			this.personne.addActivite(activite);
			this.personneDao.savePersonne(personne);
			
		}else throw new AccessInterditException("adding activite");
	}
	
	public void updateActivite(Activite activite) throws AccessInterditException {
		if(loggedIn) {
			this.activiteDao.saveActivite(activite);
			//this.personne.updateActivite(activite);
			this.personneDao.savePersonne(personne);
		}else throw new AccessInterditException("updating activite");
	}
	
	public String createActivity() {
		if(loggedIn) {
			editedActivity = new Activite();
			return "editActivity?faces-redirect=true";
		}else {
			System.out.println("WTF je suis pas co...");
			return "search?faces-redirect=true";
			//throw new IllegalAccessError();
		}
	}
	
	public String editActivity(long id) {
		if(loggedIn) {
			editedActivity = activiteDao.getActiviteById(id);
			return "editActivity?faces-redirect=true";
		}else {
			System.out.println("WTF je suis pas co...");
			return "search?faces-redirect=true";
			//throw new IllegalAccessError();
		}
	}

	//TODO: Que faire de ça ??
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
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	
	
}
