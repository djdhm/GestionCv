package gestion.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Past;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.ParamDef;

import com.github.javafaker.Faker;
import com.sun.istack.NotNull;

@NamedQueries({ 
	@NamedQuery(name = "findPersonne", query = "Select p from Personne p where p.idPerson= :value "),
	@NamedQuery(name = "findAllPersonnes", query = "Select p from Personne p "),
	@NamedQuery(name = "findAllPersonnesIn", query = "Select distinct p from Personne p where p.idPerson in (:liste)"),
	@NamedQuery(name = "findPersonsByLastName", query = "Select p from Personne p where p.nom= :nom"),
	@NamedQuery(name = "findPersonByFirstName", query = "Select p from Personne p where p.prenom= :prenom"),
	@NamedQuery(name = "findPersonneByEmail", query = "Select p from Personne p where p.email= :email") ,
	@NamedQuery(name = "countAllPersonnes", query = "Select count(p) from Personne p ") 

		})
@Entity
@FilterDefs({
	@FilterDef(name="nom", parameters = {@ParamDef( name = "value", type="string")}),
	@FilterDef(name="prenom", parameters = {@ParamDef( name = "value", type="string")}),
	@FilterDef(name="email", parameters = {@ParamDef( name = "value", type="string")}),
	

})
@Filters({
	@Filter(name="nom", condition="lower(nom) like :value"),
	@Filter(name = "prenom", condition = "lower(prenom) like :value"),
	@Filter(name = "email", condition = "lower(email) like  :value")

})
public class Personne implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Faker faker= new Faker();
	public static Personne randomPersonne() {

		Personne p = new Personne();
		p.setNom(faker.name().firstName());
		p.setPrenom(faker.name().lastName());
		p.setDateNaissance(faker.date().birthday(18,70));
		p.setEmail(faker.internet().emailAddress());
		p.setPassword("password");
		p.setSiteweb(faker.internet().domainName());
		//p.setCooptations(new HashSet<Long>());
		
		for(int i = 0 ; i< faker.number().numberBetween(0, 5);i++) {
			Activite a = new Activite();
			a.setAnnee(2020+faker.number().numberBetween(-10, 0));
			a.setDescription(faker.beer().style());
			a.setNature(NatureActivite.values()[faker.number().numberBetween(0,NatureActivite.values().length-1)]);
			a.setSiteWeb(faker.internet().url());
			a.setTitre(faker.app().name());
			p.addActivite(a);
		}
		
		
		return p;
	}
	
	@Id @GeneratedValue()
	Long idPerson;

	@Column
	@NotNull
	String nom;

	@Column
	@NotNull
	String prenom;

	@Column
	String siteweb;

	@Column
	String email;

	@Column
	String password;

	@Column
	@Past
	Date dateNaissance;

	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OneToMany(fetch = FetchType.LAZY , mappedBy = "referant")
	Set<Personne> cooptations = new HashSet<Personne>();
	
	
	 @ManyToOne()
	 Personne referant;
	

	public Personne getReferant() {
		return referant;
	}

	public void setReferant(Personne referant) {
		this.referant = referant;
	}

	@OneToMany( targetEntity=Activite.class, cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.LAZY)
	private List<Activite> activites = new ArrayList<Activite>();

	public Personne() {
		// TODO Auto-generated constructor stub
		this.nom = "";
		this.prenom = "";
		this.siteweb = "";
		this.email = "";
		this.password = "";

	}

	public Personne(String nom, String prenom, String siteWeb, String email, String password, Date dateNaissance) {
		// TODO Auto-generated constructor stub
		this.nom = nom;
		this.prenom = prenom;
		this.siteweb = siteWeb;
		this.email = email;
		this.password = password;
		this.dateNaissance = dateNaissance;
		this.cooptations = new HashSet<Personne>();
	}

	public void addCooptation(Personne personne) {
		this.cooptations.add(personne);
	}

	public void addActivite(Activite activite) {
		activite.setPersonne(this);
		this.activites.add(activite);
		System.out.println("Les activités de la personne "+this.getIdPerson());
		for(Activite a : this.activites) {
			System.out.println(a.getIdActivity()+" "+a.getTitre());
		}
	}
	
	public void updateActivite(Activite activite) {
		/*Technique du futur xD*/
		activites.removeIf(a -> a.getIdActivity().equals(activite.idActivity));
		activites.add(activite);
		
		//DEBUG
		for(Activite a : this.activites){
			System.err.println("*+*+* " + a.getIdActivity() + " " + a.getTitre());
		}
		
	}

	public void removeActivite(Activite activite) {
		this.activites.remove(activite);
		activite.setPersonne(null);
	}

	public Long getIdPerson() {
		// TODO Auto-generated method stub
		return this.idPerson;
	}

	public String getNom() {
		// TODO Auto-generated method stub
		return this.nom;
	}

	public boolean verifyPassword(String password2) {
		// TODO Auto-generated method stub
		System.out.println(this.password);
		return this.password.equals(password2);
	}


	public String getEmail() {
		// TODO Auto-generated method stub
		return email;
	}

	// J'ai rajouté le restant de setters et getters avec génération auto
	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getSiteweb() {
		return siteweb;
	}

	public void setSiteweb(String siteweb) {
		this.siteweb = siteweb;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Set<Personne> getCooptations() {
		return cooptations;
	}

	public void setCooptations(Set<Personne> cooptations) {
		this.cooptations = cooptations;
	}

	public List<Activite> getActivites() {
		return activites;
	}

	public void setActivites(List<Activite> activites) {
		this.activites = activites;
	}

	public void setId(long id) {
		this.idPerson = id;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
