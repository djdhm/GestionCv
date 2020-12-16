package gestiont.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestion.dao.IPersonneDAO;
import gestion.entities.Activite;
import gestion.entities.NatureActivite;
import gestion.entities.Personne;
import gestion.services.GuestService;

public class GuestServiceTest extends BaseJunit5{

	
	@Inject
	GuestService guestService;
	
	@Inject 
	static IPersonneDAO personneDao;

	
	@Test
	public void testSignupPersonne() {
		List<Personne> listePersonnes = this.guestService.getAllPersonnes();
		int taille = listePersonnes.size();
		Personne p = Personne.randomPersonne();
		
		this.guestService.signup(p);
		listePersonnes = this.guestService.getAllPersonnes();
		int nouvelleTaille = listePersonnes.size();
		assertEquals(nouvelleTaille,taille+1);
		Personne p_after = this.guestService.getPersonById(p.getIdPerson());
		assertNotNull(p_after);
		assertEquals(p.getEmail(),p_after.getEmail());
		assertEquals(p.getIdPerson(),p_after.getIdPerson());
	}
	
	
	@Test
	public void testVerifyExistingEmail() {
		
		Personne p = Personne.randomPersonne();
		guestService.signup(p);
		boolean mailDoesExist = guestService.verifyExistingEmail(p.getEmail()) ;
		boolean anotherMailDoesExist = guestService.verifyExistingEmail("john@cena.fr") ;
		
		assertTrue(mailDoesExist);
		assertFalse(anotherMailDoesExist);
		
		
	}
	
	@Test
	public void testCountPersonneWithoutFilters() {
		int beforeStarting = guestService.getAllPersonnes().size();

		ArrayList<Personne> liste = new ArrayList<Personne>();
		for(int i=0;i<20;i++) {
			Personne p = Personne.randomPersonne();
			liste.add(p);
			guestService.signup(p);		
		}
		int allPersonneSizze = guestService.getAllPersonnes().size();
		assertEquals(liste.size()+beforeStarting,allPersonneSizze);
		int size = guestService.countAllPersonne(new HashedMap<String, String>(), new HashedMap<String, String>());
		assertEquals(allPersonneSizze,size);
	}
	
	
	@Test
	public void testCountPersonneWithPersonneFilters() {
		String nom = "h";
		String prenom = "a";
		List<Personne> personnneList = guestService.getAllPersonnes();
		int beforeStarting = personnneList.size();
		HashMap< String, String> personneFilters = new HashMap<String, String>();
		personneFilters.put("nom", nom);
		
		Long filtredSize = personnneList.stream().filter(p-> StringUtils.containsIgnoreCase(p.getNom(),nom)).count();
		int guestCount = guestService.countAllPersonne(personneFilters, new HashMap<String, String>());
		System.out.println(filtredSize+" == "+guestCount);
		assertEquals(filtredSize.intValue(),guestCount);
		
		
		personneFilters.put("prenom", prenom);
		guestCount = guestService.countAllPersonne(personneFilters, new HashMap<String, String>());
		filtredSize = personnneList.stream().filter(p-> StringUtils.containsIgnoreCase(p.getNom(),nom) && StringUtils.containsIgnoreCase(p.getPrenom(),prenom)).count();
		System.out.println(filtredSize+" == "+guestCount);
		assertEquals(filtredSize.intValue(),guestCount);

			
	}
	
	@Test
	public void testCountPersonneWithActivityFilters() {
		String nature = "Projet";
		String description = "a";
		List<Personne> personnneList = guestService.getAllPersonnes();
		int beforeStarting = personnneList.size();
		HashMap< String, String> activityFilters = new HashMap<String, String>();
		activityFilters.put("activity", nature);
		Long filtredSize = personnneList
								.stream()
								.filter(p->{
									List<Activite> activities = guestService.getPersonneActivites(p);
									return activities.stream().anyMatch(a->{
										return a.getNature()==NatureActivite.valueOf(nature);
									});
								}).count();
		
		int guestCount = guestService.countAllPersonne(new HashedMap<String, String>(),activityFilters);
		System.out.println(filtredSize+" == "+guestCount);
		assertEquals(filtredSize.intValue(),guestCount);
		filtredSize = personnneList
				.stream()
				.filter(p->{
					List<Activite> activities = guestService.getPersonneActivites(p);
					return activities.stream().anyMatch(a->{
						return  StringUtils.containsIgnoreCase(a.getDescription(),description) 
								&&
								a.getNature()==NatureActivite.valueOf(nature);
					});
							}).count();
		
		activityFilters.put("description", "d");
		guestCount = guestService.countAllPersonne( new HashMap<String, String>(),activityFilters);
		System.out.println(filtredSize+" == "+guestCount);
		assertEquals(filtredSize.intValue(),guestCount);

			
	}
	
	@Test
	public void testGetPersonneWithoutFilters() {

		List<Personne> liste = guestService.getAllPersonnes();
		List<Personne> otherList= guestService.filterPersonnes(new HashedMap<String, String>(), new HashedMap<String, String>(),0,10);
		java.util.Set<Long> ids = liste.stream()
		        .map(Personne::getIdPerson)
		        .collect(Collectors.toSet());
		assertTrue(otherList.stream().allMatch(p-> ids.contains(p.getIdPerson())));
		
		List<Personne> anotherList= guestService.filterPersonnes(new HashedMap<String, String>(), new HashedMap<String, String>(),10,10);
		assertFalse(!anotherList.stream().anyMatch(p-> p.getIdPerson()==liste.get(0).getIdPerson()));
	}
	

	
}
