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

	
	@PersistenceContext(unitName="persistence")
	EntityManager em; 
	
	@Override
	public List<Activite> getAllActivities() {
		  Query query = em.createNamedQuery("findAllActivites",Activite.class);
		  return query.getResultList();
	}
	
	@Override
	public void saveActivite(Activite activite) {
		if(activite.getIdActivity() == null) {
			System.err.println("persiste activité : "+ activite.getIdActivity() +" "+activite.getTitre());
			em.persist(activite);
			System.err.println("Maintenant voici toutes les activités :");
			for(Activite a : getAllActivities())
				System.err.println(a.getIdActivity()+" "+a.getTitre());
		}
		else {
			System.err.println("merge activité : "+ activite.getIdActivity() +" "+activite.getTitre());
			em.merge(activite);
			System.err.println("Maintenant voici toutes les activités :");
			for(Activite a : getAllActivities())
				System.err.println(a.getIdActivity()+" "+a.getTitre());
		}
		
	}

	@Override
	public void deleteActivite(Activite activite) {
		em.remove(em.contains(activite) ? activite: em.merge(activite));
		em.flush();
	}

	@Override
	public Activite getActiviteById(long id) {
		return em.find(Activite.class, id);
	}

}
