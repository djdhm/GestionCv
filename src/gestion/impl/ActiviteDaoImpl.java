package gestion.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gestion.dao.IActiviteDao;
import gestion.entities.Activite;

@Stateless
public class ActiviteDaoImpl implements IActiviteDao {

	
	@PersistenceContext(unitName="myBase")
	EntityManager em; 
	

	
	@Override
	public void createActivite(Activite activite) {
		// TODO Auto-generated method stub
		em.persist(activite);
		
		
	}

	@Override
	public void deleteActivite(Activite activite) {
		// TODO Auto-generated method stub
		em.remove(activite);
		em.flush();
	}

	@Override
	public Activite getActiviteById(long id) {
		// TODO Auto-generated method stub
		return em.find(Activite.class, id);
	}

}
