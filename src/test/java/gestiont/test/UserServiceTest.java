package gestiont.test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import gestion.dao.IPersonneDAO;
import gestion.entities.Activite;
import gestion.entities.NatureActivite;
import gestion.entities.Personne;
import gestion.services.AccessInterditException;
import gestion.services.UserService;
import com.github.javafaker.Faker;


public class UserServiceTest extends BaseJunit5{
	@Inject
	UserService userService;
	
	@Inject
	IPersonneDAO personneDao;
	
	private static final Faker faker= new Faker();
 
	//authentify
	//login
	//logout
	//updatePerson
	//addCooptation
	//addPersonne //On l'utilise vraiment ???
	//addActivite
	//updateActivite
	//createActivity
	//deleteActivity
	//getCooptations
	//getEmail
	//getActivities
	//
	//
	//
	//
	
	/*test authentification*/
	@AfterEach
	public void disconnect() {
		
	}
	@Test //Test OK
	public void testLoginSuccess() {
		final String TEST_EMAIL_COM = "test@email.com";
		final String PASSWORD = "password";

		Date myDate = new Date(System.currentTimeMillis());
	    Personne p = new Personne("name", "surname", "www.adresse.b", TEST_EMAIL_COM, PASSWORD, new Date(myDate.getTime() - 10));
		personneDao.savePersonne(p);
	
		boolean loggedIn = userService.authentify(TEST_EMAIL_COM,PASSWORD);
        
		assertTrue(loggedIn);
		
	}
	
	@Test //Test OK
	public void testLoginFaillure() {
		final String TEST_EMAIL_COM = "false@email.com";
		final String PASSWORD = "notcorrect";

		Date myDate = new Date(System.currentTimeMillis());
	    Personne p = new Personne("name", "surname", "www.adresse.b", TEST_EMAIL_COM, PASSWORD, new Date(myDate.getTime() - 10));
		personneDao.savePersonne(p);
		
		boolean loggedWithFalseEmail = userService.authentify("nocorrect@email.test", "password");
		boolean loggedWithFalsePassword = userService.authentify(TEST_EMAIL_COM, "password");
		assertFalse(loggedWithFalsePassword);
		assertFalse(loggedWithFalseEmail);
		
	}
	
	/*Test CRUD Activités d'une personne*/
	@Test //Test OK
	public void testCRUDActivitesFromPerson() throws AccessInterditException {
		final String TEST_EMAIL_COM = "test2@email.com";
		final String PASSWORD = "password";
		Date myDate = new Date(System.currentTimeMillis());
	    Personne p = new Personne("name", "surname", "www.adresse.b", TEST_EMAIL_COM, PASSWORD, new Date(myDate.getTime() - 10));
		personneDao.savePersonne(p);
	
		userService.login(TEST_EMAIL_COM, PASSWORD);
		
		Activite a = new Activite();
		a.setAnnee(2020+faker.number().numberBetween(-10, 0));
		a.setDescription(faker.beer().style());
		a.setNature(NatureActivite.values()[faker.number().numberBetween(0,NatureActivite.values().length-1)]);
		a.setSiteWeb(faker.internet().url());
		a.setTitre("Bucheron");
		p.addActivite(a);
		
		// TEST ajout de l'activité
		userService.addActivite(a);
		assertTrue(userService.getActivities().get(0).getTitre().equals("Bucheron"));
		
		//TEST modification de l'activité
		a.setTitre("Trader");
		userService.updateActivite(a);
		assertTrue(userService.getActivities().get(0).getTitre().equals("Trader"));
		
		//TEST suppression
		userService.deleteActivity(a);
		assertTrue(userService.getActivities().size() == 0);
	}
	
	/*Test si renvoie/crée activites et mails -> null si pas loggé et pas null si loggué = tests accès champs "loggué" vs "non loggué" */
	@Test //Test OK
	public void testgetInfosWhenLogged() throws AccessInterditException {
		final String TEST_EMAIL_COM = "test3@email.com";
		final String PASSWORD = "password";
		Date myDate = new Date(System.currentTimeMillis());
	    Personne p = new Personne("name", "surname", "www.adresse.b", TEST_EMAIL_COM, PASSWORD, new Date(myDate.getTime() - 10));
		personneDao.savePersonne(p);
	
		userService.login(TEST_EMAIL_COM, PASSWORD);
		assertTrue(userService.getEmail()==TEST_EMAIL_COM);
		
        //création activité
		Activite a = new Activite();
		a.setAnnee(2020+faker.number().numberBetween(-10, 0));
		a.setDescription(faker.beer().style());
		a.setNature(NatureActivite.values()[faker.number().numberBetween(0,NatureActivite.values().length-1)]);
		a.setSiteWeb(faker.internet().url());
		a.setTitre("Bucheron");
		p.addActivite(a);
		
		// TEST ajout de l'activité
		userService.addActivite(a);
		assertTrue(userService.getActivities().get(0).getTitre().equals("Bucheron"));
	}
	
	@Test //Test ok
	public void testsetInfosWhenNotLogged() {
		
		final String TEST_EMAIL_COM = "test4@email.com";
		final String PASSWORD = "password";
		Date myDate = new Date(System.currentTimeMillis());
	    Personne p = new Personne("name", "surname", "www.adresse.b", TEST_EMAIL_COM, PASSWORD, new Date(myDate.getTime() - 10));
		personneDao.savePersonne(p);
	
		//On ne se login pas
		
        //création activité
		Activite a = new Activite();
		a.setAnnee(2020+faker.number().numberBetween(-10, 0));
		a.setDescription(faker.beer().style());
		a.setNature(NatureActivite.values()[faker.number().numberBetween(0,NatureActivite.values().length-1)]);
		a.setSiteWeb(faker.internet().url());
		a.setTitre("Bucheron");
		p.addActivite(a);
		
		// TEST ajout de l'activité échoue
		assertThrows(AccessInterditException.class,()->{
			userService.addActivite(a);
        });
	}
	
	/*Test si les cooptations sont correctement ajoutés*/
	
	@Test //Test OK
	public void testgetCooptations() throws AccessInterditException {
		//Création de la personne qui va coopter
		final String TEST_EMAIL_COM = "test5@email.com";
		final String PASSWORD = "password";
		Date myDate = new Date(System.currentTimeMillis());
	    Personne p = new Personne("name", "surname", "www.adresse.b", TEST_EMAIL_COM, PASSWORD, new Date(myDate.getTime() - 10));
		personneDao.savePersonne(p);
		
		userService.login(TEST_EMAIL_COM, PASSWORD);
		
		//Création de la personne à coopter
		final String TEST_EMAIL_COM_2 = "test222@email.com";
		final String PASSWORD_2 = "password";
	    Personne p2 = new Personne("nameCooptation", "surname", "www.adresse.b", TEST_EMAIL_COM, PASSWORD, new Date());
		personneDao.savePersonne(p2);
		
        //création cooptation
		userService.addCooptation(p2);
		assertTrue(p2.getNom().equals("nameCooptation"));
		
	}
	
	
	
	/*test si accès information échoue après logout*/
	@Test //TEST PAS OK = Can not call EJB Stateful Bean Remove Method without scoped @Dependent.  Found scope: @SessionScoped
	
	public void testgetInfosAfterLogout() throws AccessInterditException {
		final String TEST_EMAIL_COM = "test6@email.com";
		final String PASSWORD = "password";
		Date myDate = new Date(System.currentTimeMillis());
	    Personne p = new Personne("name", "surname", "www.adresse.b", TEST_EMAIL_COM, PASSWORD, new Date(myDate.getTime() - 10));
		personneDao.savePersonne(p);
	
		userService.login(TEST_EMAIL_COM, PASSWORD);
		
		Activite a = new Activite();
		a.setAnnee(2020+faker.number().numberBetween(-10, 0));
		a.setDescription(faker.beer().style());
		a.setNature(NatureActivite.values()[faker.number().numberBetween(0,NatureActivite.values().length-1)]);
		a.setSiteWeb(faker.internet().url());
		a.setTitre("Bucheron");
		p.addActivite(a);
		userService.addActivite(a);
		
		//On se logout
		
		userService.logout();
		
		// TEST récup mail = null
		assertTrue(userService.getEmail() == null);
	}
	
}
