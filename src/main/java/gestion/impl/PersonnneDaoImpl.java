package gestion.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.openejb.jee.wls.SqlQuery;
import org.hibernate.Session;

import gestion.dao.IActiviteDao;
import gestion.dao.IPersonneDAO;
import gestion.entities.Activite;
import gestion.entities.NatureActivite;
import gestion.entities.Personne;

@Stateless
public class PersonnneDaoImpl implements IPersonneDAO {
	
	
	@PersistenceContext(unitName="persistence")
	EntityManager em; 
	
	@Inject
	IActiviteDao activiteDao;


	@Override
	public List<Personne> getAllPerson() {
		  Query query = em.createNamedQuery("findAllPersonnes",Personne.class);
		  return query.getResultList();
	}

	
	
	@Override
	public Personne getPersonById(long id) {
		String queryString = "select p from Personne p where p.idPerson = "+id;
		System.out.println(queryString);
		Query query = em.createQuery(queryString);
		try {
			Personne p1 = (Personne) query.getSingleResult();
			

			List<Activite> l = p1.getActivites();
			Set<Personne> coop = p1.getCooptations();
			for(Activite a:l) {
				System.out.println(a.getDescription());
			}
			return p1;
		}catch(Exception e) {
			System.out.println("Not found");
			return null;
		}
	
		
		
	}
	@Override 
	public Set<Personne> getPersonneCooptations(Personne p){
		Personne p1 = em.find(Personne.class,p.getIdPerson());
		Set<Personne> cooptations = new HashSet<Personne>();
		cooptations.addAll(p1.getCooptations());
		
		return cooptations;
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
	      em.createNativeQuery("SHOW TABLES");
	      em.createNativeQuery("SHOW COLUMNS from Personne");
	      
	      
		if(p.getIdPerson() == null) {
			System.err.println("On persiste une personne");
			em.persist(p);
		}
		else {
			System.err.println("On merge une personne");
			List<Activite> activities = p.getActivites();
			for(Activite act:activities)		System.out.println(act.getIdActivity()+"=="+act.getTitre());
			em.merge(p);

			p.setActivites(new ArrayList<Activite>(activities));
			em.merge(p);

		}
	}
	

	public List<Personne> applyFilter(String nom,String prenom,String activiteTitre){

		HashMap<String, String> parameters = new HashMap<String, String>();
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
	public List<Personne> getFilteredData(Map<String,String> filters, Map<String, String> activiteFilters,int first,int pageSize) {
		// On recupere la session hibernate pour appliquer les filtres predifinies 
		org.hibernate.Session session =em.unwrap(Session.class);
		// Appliquer les filtres sur la table personnes 
		applyFilter(session, filters);
		// Si on a pas de filtres sur les activites on fait une requete select all basique sur la table personne 
		// Sinon on passe pas la table activite pour faire plus rapide et evite de faire une jointure couteuse 
		if(!activiteFilters.isEmpty()) {
			// Recuperer une projection de la table activite vers IdPerson on appliquant les filtres sur les activites 
			List<Long> a = this.activiteDao.getAssociatedPersonne((activiteFilters));
			// Si aucune acitvite ne satisfait les criteres on retourne null 
			if(a.size()==0) return null;
			// Sinon on recupere toutes les p�rsonnes qui ont leur id dans la liste precedentes 
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Personne> cq = cb.createQuery(Personne.class);
			Root<Personne> root = cq.from(Personne.class);
			CriteriaQuery<Personne> vrai = cq.where(root.get("idPerson").in(a));
			TypedQuery<Personne> allQuery = em.createQuery(vrai);
			allQuery.setMaxResults(pageSize);
			allQuery.setFirstResult(first);
			List<Personne> resultat = allQuery.getResultList();
			
			// On desactive les filtres Hibernates 
			disableFilters(session, filters);
	        return resultat;
		}else {
			
			TypedQuery<Personne> query = em.createNamedQuery("findAllPersonnes",Personne.class);
			List<Personne> resultList = query.getResultList();
			disableFilters(session, filters);
			
			return resultList;

		}
		
		
	}
	@Override
	public int countAllPersonne(Map<String, String> filters, Map<String, String> activiteFilters) {
		// On applique les filtres de personnes dans la session 
		org.hibernate.Session session =em.unwrap(Session.class);
		System.out.println("activite Filter vide == "+activiteFilters.isEmpty());
		applyFilter(session, filters);

// Si on a pas de filtres sur les activites on fait une requete count basique sur la table personne 
// Sinon on passe pas la table activite pour faire plus rapide et evite de faire une jointure couteuse 
		if(!activiteFilters.isEmpty()) {
			// Recuperer les ids de personnes ayant des activites qui match les criteres  
			List<Long> liste = activiteDao.getAssociatedPersonne(activiteFilters);
			// Si aucune activite on retourne zero 
			if(liste.size()==0) return 0;
			// Sinon on fait une requete sur la table Personne pour appliquer les filtres sur la table personne
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Personne> cq = cb.createQuery(Personne.class);
			CriteriaQuery<Long> vrai = cb.createQuery(Long.class);
			Root<Personne> root = vrai.from(Personne.class);
			vrai.select(cb.count(root));
			vrai.where(root.get("idPerson").in(liste)) ;
			TypedQuery<Long> allQuery = em.createQuery(vrai);
			Long x = allQuery.getSingleResult();
			disableFilters(session,filters);
			return x.intValue();
		}
		// On fait un count sur la table personne
		TypedQuery<Long> query = em.createNamedQuery("countAllPersonnes",Long.class);
		int intValue = query.getSingleResult().intValue();
		disableFilters(session, filters);
		return intValue;
		
	}
	
	private void applyFilter(Session session, Map<String, String> filters) {
		for(String column:filters.keySet()) {
			System.out.println(column+"=="+filters.get(column));
			if(column=="activity") {
				session.enableFilter(column).setParameter("value", filters.get(column));
			}else {
				System.out.println("Applying filter:"+column);
				session.enableFilter(column).setParameter("value", "%"+filters.get(column).toLowerCase()+"%");

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
	@Override
	public void addActivite(Personne personne, Activite activite) {
		// TODO Auto-generated method stub
		Personne p = em.find(Personne.class, personne.getIdPerson());
		p.getActivites();
		p.addActivite(activite);
		System.out.println(p.getActivites().size());
		em.merge(p);

		System.out.println(p.getActivites().size());
	}
	@Override
	public void addCooptation(Personne personne,Personne coopte) {
		// TODO Auto-generated method stub
		coopte.setReferant(personne);
		em.persist(coopte);
		System.out.println(coopte.getIdPerson());
		Set<Personne> cooptations = new HashSet<Personne>();
		System.out.println("Adding "+coopte.getIdPerson()+"To user "+personne.getIdPerson());
		Personne p = em.find(Personne.class, personne.getIdPerson());
		System.out.println(p.getCooptations().size());

		cooptations.addAll(p.getCooptations());
		cooptations.add(coopte);
		
		p.setCooptations(cooptations);
		em.merge(p);		
		System.out.println(p.getCooptations().size());
	}

}
