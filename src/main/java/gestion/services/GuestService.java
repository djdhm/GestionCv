package gestion.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		
		
	
		
	}
	
	public List<Personne> getAllPersonnes(){
		return this.personneDAO.getAllPerson();
	}
	
	public Set<Personne> getPersonsCooptationById(Personne p) {
		return personneDAO.getPersonneCooptations(p);
	}
	public List<Activite> getPersonneActivites(Personne p){
		return activityDAO.getActiviteOfPersonne(p);
		
	}
	
	public int countAllPersonne(Map<String, String> filters, Map<String,String> activiteFilters) {
		return personneDAO.countAllPersonne(filters,activiteFilters);
	}
	public Personne getPersonById(long id) {
		return personneDAO.getPersonById(id);
	}
	
	public void signup(Personne p ) {
		personneDAO.savePersonne(p);
	}
	


	public List<Personne> filterPersonnes(Map<String,String> filters, Map<String, String> activiteFilters, int first, int pageSize){
		
		return this.personneDAO.getFilteredData(filters,activiteFilters,first,pageSize);
				
	}

	public boolean verifyExistingEmail(String email) {
		// TODO Auto-generated method stub
		return (this.personneDAO.getPersonneByEmail(email)!=null);
		
	}

	public void generateDate(int nombre) {
		// TODO Auto-generated method stub
		for(int i=0;i<nombre	;i++) {
			personneDAO.savePersonne(Personne.randomPersonne());
			
		}
	}

	
    
	
}
