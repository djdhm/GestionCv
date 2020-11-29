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
		
		/* Ce code crée des problèmes de persistence lors d'inscription avec page dédiée ??*/
		/*if (personneDAO.getAllPerson().size() < 2) {
			Personne p1 = new Personne();
			p1.setNom("Albert");
			p1.setPrenom("Einstein");
			p1.setEmail("relativite@restreinte.emc2");
			personneDAO.createPersonne(p1);
		}*/
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
