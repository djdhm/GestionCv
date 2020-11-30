package gestiont.test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gestion.dao.IPersonneDAO;
import gestion.entities.Activite;
import gestion.entities.Personne;
import gestion.services.AccessInterditException;
import gestion.services.UserService;

public class LoginTest  extends BaseJunit5 {
	
	
	@Inject
	UserService userService;
	
	@Inject
	IPersonneDAO personneDao;
 
	@Test
	public void testLoginSuccess() {
		final String TEST_EMAIL_COM = "test@email.com";
		final String PASSWORD = "password";

	    Personne p = new Personne("name", "surname", "www.adresse.b", TEST_EMAIL_COM, PASSWORD, new Date());
		personneDao.savePersonne(p);
	
		boolean loggedIn = userService.authentify(TEST_EMAIL_COM,PASSWORD);
        
		assertTrue(loggedIn);
		
	}
	
	@Test
	public void testLoginFaillure() {
		final String TEST_EMAIL_COM = "false@email.com";
		final String PASSWORD = "notcorrect";

	    Personne p = new Personne("name", "surname", "www.adresse.b", TEST_EMAIL_COM, PASSWORD, new Date());
		personneDao.savePersonne(p);
		
		boolean loggedWithFalseEmail = userService.authentify("nocorrect@email.test", "password");
		boolean loggedWithFalsePassword = userService.authentify(TEST_EMAIL_COM, "password");
		assertFalse(loggedWithFalsePassword);
		assertFalse(loggedWithFalseEmail);
		
	}
	
	@Test
	public void testIllegalAccess() {
		assertThrows(AccessInterditException.class, () -> {
			 userService.addPersonne(new Personne());
			 
		 });
		assertThrows(AccessInterditException.class, () -> {
			 userService.addActivite(new Activite());
			 
		 });
		
	}
  
	
}
