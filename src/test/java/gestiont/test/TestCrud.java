package gestiont.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestiont.test.BaseJunit5;
import gestion.dao.IPersonneDAO;
import gestion.entities.Personne;
import gestion.services.GuestService;
import gestion.services.UserService;

public class TestCrud extends BaseJunit5 {
	
	
	@Inject 
	UserService userService;
	
	@Inject 
	GuestService guestService;
	
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
		assertTrue(userService.authentify(TEST_EMAIL_COM,PASSWORD));

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

		
			guestService.signup(p1);
			guestService.signup(p2);
			guestService.signup(p3);

			Personne pDb = personneDao.getPersonById(p1.getIdPerson());
			assert(pDb.getIdPerson()==p1.getIdPerson());
			System.out.println("allo");


			
		
	}
	
	

  
}
