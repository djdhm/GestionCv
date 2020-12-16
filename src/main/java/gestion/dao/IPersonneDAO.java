package gestion.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gestion.entities.Activite;
import gestion.entities.Personne;

public interface IPersonneDAO {

	
	
	public List<Personne> getAllPerson();
	public Personne getPersonById(long id);
	public void deletePersonne(Personne p);
	public void deletePersonneById(long id);
	public void savePersonne(Personne p);
	public Personne getPersonneByEmail(String email);
	public List<Personne> applyFilter(String nom,String prenom,String activiteTitre);
	public int countAllPersonne(Map<String,String> filters, Map<String, String> activiteFilters);
	public List<Personne> getFilteredData(Map<String,String> filters, Map<String, String> activiteFilters, int first, int pageSize);
	Set<Personne> getPersonneCooptations(Personne p);
	public void addActivite(Personne personne, Activite activite);
	void addCooptation(Personne personne, Personne coopte);
}
