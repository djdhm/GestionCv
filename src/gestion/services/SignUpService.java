package gestion.services;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import gestion.dao.IPersonneDAO;
import gestion.entities.Personne;

@Named("signup")
@SessionScoped
public class SignUpService implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	IPersonneDAO personneDao;

	Personne person  = new Personne();

	@PostConstruct
	public void init() {
		System.out.println("Une personne veut s'inscrire ");
		System.out.println("---------------Actuellement il y a "+personneDao.getAllPerson().size()+" personnes inscrites");
		person  = new Personne();
		
	}

	public String save() {
		System.out.println("---------------- La personne que l'on veut inscrire aura l'ID :"+person.getId()+" "+ person);
		personneDao.createPersonne(person);
		person = new Personne(); /*Pour forcer le changement d'ID"*/
		return "search?faces-redirect=true"; /*Pour l'instant on retourne à ça et non l'espace perso*/
	}

	
	
	/*Il est important de mettre le getter pour pouvoir accèder en jsf */
	public Personne getPerson() {
		return person;
	}

	

}
