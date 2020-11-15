package gestion.dao;

import java.util.List;

import gestion.entities.Personne;

public interface IPersonneDAO {

	
	
	public List<Personne> getAllPerson();
	public Personne getPersonById(long id);
	public void updatePerson(Personne p);
	public void deletePersonne(Personne p);
	public void deletePersonneById(long id);
	public void createPersonne(Personne p);
	public Personne getPersonneByEmail(String email);
	public List<Personne> applyFilter(String nom,String prenom,String activiteTitre);
}
