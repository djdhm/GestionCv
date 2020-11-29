package gestion.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import gestion.dao.IPersonneDAO;
import gestion.entities.Personne;

@Stateless
public class GuestService {

	@EJB
	IPersonneDAO personneDAO;
	
	
	@PostConstruct
	public void init() {
		System.out.println("Create " + this);
		
		if (personneDAO.getAllPerson().size() < 2) {
			Personne p = new Personne();
			p.setNom("Einstein");
			p.setPrenom("Albert");
			p.setEmail("relativite@restreinte.emc2");
			personneDAO.createPersonne(p);
			
			p = new Personne();
			p.setNom("Poincare");
			p.setPrenom("Henri");
			p.setEmail("conjecture@riemann.pi");
			personneDAO.createPersonne(p);
		}
	}
	
	public List<Personne> getAllPersonnes(){
		return this.personneDAO.getAllPerson();
	}
	
	public String seeCV(long id){
	        return "showCV"; /*COMMENT indiquer l'id de la personne dont on va afficher le CV à la vue ?*/
	}

	public List<Personne> filterPersonnes(String nom,String  prenom,String titreActivite){
		
		return this.personneDAO.applyFilter(nom, prenom, titreActivite);
				
	}
    
	
}
