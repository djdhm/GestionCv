package gestion.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import gestion.dao.IActiviteDao;
import gestion.dao.IPersonneDAO;
import gestion.entities.Activite;
import gestion.entities.Personne;

@Stateless
public class GuestService {

	@EJB
	IPersonneDAO personneDAO;
	
	@EJB
	IActiviteDao activityDAO;
	
	List<Activite> currentActivity; /* Un CV */
	
	@PostConstruct
	public void init() {
		System.out.println("Create " + this);
		
		// Creating Mock Data 
		// Tu peux ennlever les commentaire et choisir laa taaille des donneess 
//		for(int i=0;i<100000;i++) {
//			personneDAO.savePersonne(Personne.randomPersonne());
//			
//		}
		
	}
	
	public List<Personne> getAllPersonnes(){
		return this.personneDAO.getAllPerson();
	}
	
	public List<Personne> getAllPersonnes(int page, int pageSize){
		return this.personneDAO.getAllPerson(page,pageSize);
	}
	public int countAllPersonne(HashMap<String, String> filters) {
		return personneDAO.countAllPersonne(filters);
	}
	public Personne getPersonById(long id) {
		return personneDAO.getPersonById(id);
	}
	
	public void signup(Personne p ) {
		personneDAO.savePersonne(p);
	}
	
	public List<Activite> getActivitiesOfPerson(long id){
			return personneDAO.getPersonById(id).getActivites();
	}

	public List<Personne> filterPersonnes(Map<String,String> filters){
		
		return this.personneDAO.getFilteredData(filters);
				
	}

	public boolean verifyExistingEmail(String email) {
		// TODO Auto-generated method stub
		return (this.personneDAO.getPersonneByEmail(email)!=null);
		
	}

	
    
	
}
