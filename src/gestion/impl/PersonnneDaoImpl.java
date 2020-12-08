package gestion.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.openejb.jee.wls.SqlQuery;
import org.hibernate.Session;

import gestion.dao.IPersonneDAO;
import gestion.entities.Activite;
import gestion.entities.Personne;

@Stateless
public class PersonnneDaoImpl implements IPersonneDAO {
	
	
	@PersistenceContext(unitName="persistence")
	EntityManager em; 
	


	@Override
	public List<Personne> getAllPerson() {
		  Query query = em.createNamedQuery("findAllPersonnes",Personne.class);
		  return query.getResultList();
	}
	@Override
	public List<Personne> getAllPerson(int page, int pageSize) {
		// TODO Auto-generated method stub
		 Query query = em.createNamedQuery("findAllPersonnes",Personne.class);
		 query.setFirstResult(pageSize*page);
		 query.setMaxResults(pageSize);
		 return query.getResultList();
	}
	@Override
	public Personne getPersonById(long id) {
		return em.find(Personne.class,id);
		
	}

	@Override
	public void deletePersonne(Personne p) {
		em.remove(em.contains(p) ? p : em.merge(p));

	}

	@Override
	public void deletePersonneById(long id) {
		Personne p=getPersonById(id);
	
		em.remove(p);
		
	}

	@Override
	public void savePersonne(Personne p) {
		if(p.getIdPerson() == null) {
			System.err.println("On persiste une personne");
			em.persist(p);
		}
		else {
			System.err.println("On merge une personne");
			em.merge(p);
			}
	}
	public List<Personne> applyFilter(String nom,String prenom,String activiteTitre){

		HashMap<String, String> parameters = new HashMap();
		if(!nom.equals("")) parameters.put("n", nom);
		if(!prenom.equals("")) parameters.put("p", prenom);
		if(!activiteTitre.equals("")) parameters.put("a", activiteTitre);
		
		/*Il faut construire la requêtes en fonction des params présents*/
		/*TODO: rajouter le champs activite... Je l'ai enlevé temporairement*/
		/*TODO: rendre insensible à la casse ?? ie. Aucune différence entre minuscule et maj*/
		String queryString= "select p from Personne p where ";
		
		int counter = 0;
		
		for (Map.Entry me : parameters.entrySet()) {
			
	          System.out.println("Key: "+me.getKey() + " & Value: " + me.getValue());
	          //TODO: Utiliser StringBuilder LOL ???
	          if(counter > 0) {
	        	  queryString += " and ";
	          }
	          
	          if(me.getKey().equals("n") && !me.getValue().equals("")) {
	        	  queryString += "p.nom like :nomF";
	          }
	          if(me.getKey().equals("p") && !me.getValue().equals("")) {
	        	  queryString += "p.prenom like :prenomF";
	          }
	          if(me.getKey().equals("a") && !me.getValue().equals("")) {
	        	  queryString += "a.titre like :titreF and a.personne =p";
	          }
	          counter++;
	        }
		System.out.println("QUERY FINALE FILTER: "+queryString);
		Query query = em.createQuery(queryString,Personne.class);
		
		for (Map.Entry me : parameters.entrySet()) {
	          if(me.getKey().equals("n") && !me.getValue().equals("")) {query.setParameter("nomF", "%"+nom+"%");}
	          if(me.getKey().equals("p") && !me.getValue().equals("")) {query.setParameter("prenomF","%"+ prenom+"%");}
	          if(me.getKey().equals("a") && !me.getValue().equals("")) {query.setParameter("titreF","%"+activiteTitre+"%");}
	        }
		try {
			List<Personne> results = query.getResultList();
			return results;

		}catch(NoResultException e) {
			/*WHAT ???*/
			return new ArrayList<Personne>();
		}

		
	}

	@Override
	public Personne getPersonneByEmail(String email) {

		Query query = em.createNamedQuery("findPersonneByEmail", Personne.class);
		query.setParameter("email", email);
		try {
			return (Personne) query.getSingleResult();

		}catch(NoResultException e) {
			return null;
		}
	}
	@Override
	public int countAllPersonne() {
		// TODO Auto-generated method stub
		Query query = em.createNamedQuery("countAllPersonne");
		
		List liste = query.getResultList();
		for(Object o:liste) {
			System.out.println(o.toString());
			return Integer.parseInt(o.toString()) ;
		}
		return 1000;
	}


	@Override
	public List<Personne> getFilteredData(Map<String,String> filters) {
		// TODO Auto-generated method stub
		org.hibernate.Session session =em.unwrap(Session.class);

		Query query = em.createNamedQuery("findAllPersonnes",Personne.class);
		session.enableFilter("nomLike").setParameter("value", "Poincare");
		System.out.println(		session.getEnabledFilter("nomLike"));
		query.setMaxResults(1000);
		return query.getResultList();
		
		
	}
	@Override
	public int countAllPersonne(HashMap<String, String> filters) {
		// TODO Auto-generated method stub
		org.hibernate.Session session =em.unwrap(Session.class);

		session.enableFilter("nomLike").setParameter("value", "Poincare");
		System.out.println(		session.getEnabledFilter("nomLike"));
		Query query = em.createNamedQuery("countAllPersonnes");
		System.out.println(query.toString());
		List liste = query.getResultList();
		for(Object o:liste) {
			System.out.println(o.toString());
			return Integer.parseInt(o.toString()) ;
		}
		return 0;
	}


}
