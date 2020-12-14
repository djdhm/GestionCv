package gestion.dao;

import java.util.List;
import java.util.Map;

import gestion.entities.Activite;
import gestion.entities.Personne;

public interface IActiviteDao {
	public void saveActivite(Activite activite);
	public void deleteActivite(Activite activite);
	public Activite getActiviteById(long id);
	public List<Activite> getAllActivities(Map<String, String> activiteFilters);
	public List<Long> getAssociatedPersonne(Map<String,String> activiteFilters);
	
}
