package gestion.dao;

import gestion.entities.Activite;

public interface IActiviteDao {
	public void createActivite(Activite activite);
	public void deleteActivite(Activite activite);
	public Activite getActiviteById(long id);

}
