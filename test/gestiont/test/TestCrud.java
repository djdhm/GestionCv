package gestiont.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestion.dao.IPersonneDAO;
import gestion.entities.Activite;
import gestion.entities.NatureActivite;
import gestion.entities.Personne;
import gestion.services.AccessInterditException;
import gestion.services.UserService;

public class TestCrud extends BaseJunit5 {
	
	
	@Inject 
	UserService userService;
	
	@Inject
	IPersonneDAO personneDao;
	
	final String TEST_EMAIL_COM = "alloo@unique.com";
	final String PASSWORD = "password";
	
	Personne p;
	// utiliser un beforeEach pour connecter l utilisateur car tous ces tests necessite une connexion 
	@BeforeEach
	public void connect() {
	

		
	    this.p = new Personne("name", "surname", "www.adresse.b", TEST_EMAIL_COM, PASSWORD, new Date());
		personneDao.savePersonne(p);
	
		userService.authentify(TEST_EMAIL_COM,PASSWORD);
	}
	
	@AfterEach
	public void deleteUser() {
		personneDao.deletePersonne(this.p);
		
	}
	

	@Test
	public void testAddPersonne(){
		Personne p1 = new Personne("test","test","siteweb.com","ffff@allo.fr","password", new Date());
		Personne p2 = new Personne("test","test","siteweb.com","ggg@allo.fr","password", new Date());
		Personne p3 = new Personne("test","test","siteweb.com","hhh@allo.fr","password", new Date());

		try {
			userService.addPersonne(p1);
			userService.addPersonne(p2);
			userService.addPersonne(p3);

			Personne pDb = personneDao.getPersonById(p1.getIdPerson());
			assert(pDb.getIdPerson()==p1.getIdPerson());
			List<Personne> listeCoptations = userService.getCoptations();
			
			System.out.println(listeCoptations);
			listeCoptations = userService.getCoptations();
			System.out.println("allo");
			System.out.println(listeCoptations.size());
			assert(listeCoptations.get(0).getIdPerson()==p1.getIdPerson());

			assert(listeCoptations.size()==3);


			
		} catch (AccessInterditException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

  
}
