package gestion.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;

import gestion.dao.IActiviteDao;
import gestion.entities.Activite;
import gestion.entities.NatureActivite;
import gestion.entities.Personne;

@Stateless
public class ActiviteDaoImpl implements IActiviteDao {

	
	@PersistenceContext(unitName="persistence")
	EntityManager em; 
	
	@Override
	public List<Activite> getAllActivities(Map<String, String> activiteFilters) {
		  for(String filter:activiteFilters.keySet()) {
			  System.out.println(filter);
		  }
		  Query query = em.createNamedQuery("findAllActivites",Activite.class);
		  return query.getResultList();
	}
	
	@Override
	public void saveActivite(Activite activite) {
		if(activite.getIdActivity() == null) {
			System.err.println("persiste activité : "+ activite.getIdActivity() +" "+activite.getTitre());
			em.persist(activite);
			
		}
		else {
			System.err.println("merge activité : "+ activite.getIdActivity() +" "+activite.getTitre());
			em.merge(activite);
			System.err.println("Maintenant voici toutes les activités :");
		
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

	@Override
	public List<Long> getAssociatedPersonne(Map<String, String> activiteFilters) {
		// TODO Auto-generated method stub

		//	applyFilter(session, activiteFilters);
		String queryString= "select distinct(a.personne_idperson) from activite a where ";
		if(activiteFilters.get("activity")!=null) {
			queryString += " a.nature="+NatureActivite.valueOf(activiteFilters.get("activity")).ordinal()+" and ";
		}
		queryString += "lower(a.description) like '%"+activiteFilters.getOrDefault("description", "").toLowerCase()+"%'";
		System.out.println(queryString);
		Query query = em.createNativeQuery(queryString);
		List<Long> liste = query.getResultList();
		return liste;
	}
	private void applyFilter(Session session, Map<String, String> filters) {
		for(String column:filters.keySet()) {
			System.out.println(column+"=="+filters.get(column));
			if(column=="activity") {
				session.enableFilter(column).setParameter("value", filters.get(column));
			}else {
				System.out.println("Applying filter:"+column);
				session.enableFilter(column).setParameter("value", "%"+filters.get(column)+"%");

			}
		}
	}
	private void disableFilters(Session session,Map<String,String> filters) {
		for(String column:filters.keySet()) {
			System.out.println(column+"=="+filters.get(column));
			
				System.out.println("disabling :"+column);
				session.disableFilter(column);
		}
	}

}
