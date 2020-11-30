package gestion.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import gestion.dao.IPersonneDAO;
import gestion.entities.Personne;
import gestion.services.UserService;

@Named("signup")
@SessionScoped
public class SignUpControler implements Serializable{
	private static final long serialVersionUID = 1L;

    @Inject
    UserService userService;

   @Inject
	IPersonneDAO personneDao;
   

   /* Objet qu'on utilise pour l'inscription */
   Personne signingUpPerson;
   
	@PostConstruct
	public void init() {
		/*On instancie un objet personne pour la personne qui s'inscrit*/
		signingUpPerson  = new Personne();
	}

	public String save() {
		System.out.println(signingUpPerson.getPrenom() +" est en train de s'inscrire");
		personneDao.savePersonne(signingUpPerson);
		
		System.err.println("Il y'a maintenant toutes ces personnes inscrites");
		for(Personne p : personneDao.getAllPerson()) {
			System.err.println(p.getIdPerson() + " " + p.getNom());
		}
		signingUpPerson = new Personne(); /*Pour forcer le changement d'ID pour la prochaine inscription"*/
		return "search?faces-redirect=true"; /*Pour l'instant on retourne à ça et non l'espace perso ??*/
	}
	
	public Personne getSigningUpPerson() {
		return signingUpPerson;
	}
	
	
	
}
