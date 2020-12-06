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

		/*On rajoute un peu des personnes dans la BDD*/
		if (personneDAO.getAllPerson().size() < 2) {
			Personne p = new Personne();
			p.setNom("Einstein");
			p.setPrenom("Albert");
			p.setEmail("relativite@restreinte.emc2");
			personneDAO.savePersonne(p);
			
			Activite a = new Activite("Physicien", "c génial", "universe.org", null);
			activityDAO.saveActivite(a);
			p.addActivite(a);
			personneDAO.savePersonne(p);
			
			p = new Personne();
			p.setNom("Poincare");
			p.setPrenom("Henri");
			p.setEmail("conjecture@riemann.pi");
			personneDAO.savePersonne(p);
		}
	}
	
	public List<Personne> getAllPersonnes(){
		return this.personneDAO.getAllPerson();
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

	public List<Personne> filterPersonnes(String nom,String  prenom,String titreActivite){
		
		return this.personneDAO.applyFilter(nom, prenom, titreActivite);
				
	}

	public boolean verifyExistingEmail(String email) {
		// TODO Auto-generated method stub
		return (this.personneDAO.getPersonneByEmail(email)!=null);
		
	}
    
	
}
