package gestion.dao;

import java.util.List;

import gestion.entities.Activite;

public interface IActiviteDao {
	public void saveActivite(Activite activite);
	public void deleteActivite(Activite activite);
	public Activite getActiviteById(long id);
	public List<Activite> getAllActivities();
	
}
