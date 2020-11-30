package gestion.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import gestion.dao.IActiviteDao;
import gestion.entities.Activite;
import gestion.entities.Personne;

@Stateless
public class ActiviteDaoImpl implements IActiviteDao {

	
	@PersistenceContext(unitName="myBase")
	EntityManager em; 
	
	@Override
	public List<Activite> getAllActivities() {
		// TODO Auto-generated method stub
		  Query query = em.createNamedQuery("findAllActivites",Activite.class);
		  return query.getResultList();
	}
	
	@Override
	public void saveActivite(Activite activite) {
		if(activite.getIdActivity() == null) /*Je fais ça à cause du problème du detached entity... Pourtant l'objet est sensé être nouveau ! */
			em.persist(activite);
		else
			em.merge(activite);
		
		
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
