package gestion.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import gestion.dao.IPersonneDAO;
import gestion.entities.Personne;

@Stateless
public class GuestService {

	
	@EJB
	IPersonneDAO personneDAO;
	

	
	public List<Personne> getAllPersonnes(){
		return this.personneDAO.getAllPerson();
	}

	public List<Personne> filterPersonnes(String nom,String  prenom,String titreActivite){
		
		return this.personneDAO.applyFilter(nom, prenom, titreActivite);
				
	}
    
	
}
