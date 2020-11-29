package gestion.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import gestion.dao.IPersonneDAO;
import gestion.entities.Activite;
import gestion.entities.Personne;

@Stateless
public class PersonnneDaoImpl implements IPersonneDAO {
	
	
	@PersistenceContext(unitName="myBase")
	EntityManager em; 
	


	@Override
	public List<Personne> getAllPerson() {
		// TODO Auto-generated method stub
		  Query query = em.createNamedQuery("findAllPersonnes",Personne.class);
		  return query.getResultList();
	}

	@Override
	public Personne getPersonById(long id) {
		// TODO Auto-generated method stub
		return em.find(Personne.class,id);
		
	}

	@Override
	public void updatePerson(Personne p) {
		// TODO Auto-generated method stub
		em.merge(p);
	}

	@Override
	public void deletePersonne(Personne p) {
		// TODO Auto-generated method stub
		em.remove(em.contains(p) ? p : em.merge(p));

	}

	@Override
	public void deletePersonneById(long id) {
		// TODO Auto-generated method stub
		Personne p=getPersonById(id);
	
		em.remove(p);
		
	}

	/*On devrait la renommer en save ? */
	@Override
	public void createPersonne(Personne p) {
		// TODO Auto-generated method stub
		if(p.getId() == null) /*Je fais ça à cause du problème du detached entity... Pourtant l'objet est sensé être nouveau ! */
			em.persist(p);
		else
			em.merge(p);
	}
	public List<Personne> applyFilter(String nom,String prenom,String activiteTitre){
		
		String queryString= "select p from personne p join activite a where p.nom like :nomF and p.prenom like :prenomF and a.titre like :titreF";
		Query query = em.createNativeQuery(queryString,Personne.class);
		query.setParameter("nomF", "%"+nom+"%");
		query.setParameter("prenomF","%"+ prenom+"%");
		query.setParameter("titreF","%"+activiteTitre+"%");
		
		
		try {
			return query.getResultList();

		}catch(NoResultException e) {
			return new ArrayList<Personne>();
		}

		
	}

	@Override
	public Personne getPersonneByEmail(String email) {
		// TODO Auto-generated method stub

		Query query = em.createNamedQuery("findPersonneByEmail", Personne.class);
		query.setParameter("email", email);
		try {
			return (Personne) query.getSingleResult();

		}catch(NoResultException e) {
			return null;
		}
	}

}
