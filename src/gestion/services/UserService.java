package gestion.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import gestion.dao.IActiviteDao;
import gestion.dao.IPersonneDAO;
import gestion.entities.Activite;
import gestion.entities.Personne;

@Stateful
public class UserService {

	
	
	@EJB
	IPersonneDAO personneDao;
	@EJB
	IActiviteDao activiteDao;
	
	
	
	private boolean loggedIn;
	private Personne personne;
	
	public boolean login(String username,String password) {
	 
		System.out.println("I.m trying to login");
		Personne p = personneDao.getPersonneByEmail(username);
		if(p == null) return false;
		if(p.verifyPassword(password)) {
			loggedIn=true;
			this.personne = p;
			return true;
			
		}
		return false;
	}
	
	public void logout() {
		if(loggedIn) {
			loggedIn=false;
			personne=null;
		}else {
			throw new IllegalAccessError();
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
			
			this.activiteDao.createActivite(activite);
			this.personne.addActivite(activite);
			this.personneDao.updatePerson(personne);
			
		}else throw new AccessInterditException("adding activite");
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
	
}
