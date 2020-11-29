package gestion.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Activite implements Serializable {

	
	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	Long id;
	
	@Column
	@NotNull
	int annee;
	
	@Column 
	@NotNull
	String titre;
	
	@Column
	String description;
	
	@Column
	String siteWeb;
	
	@ManyToOne(fetch = FetchType.LAZY)
    private Personne personne;
	
 
	
    public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	@Enumerated
	@Column
	NatureActivite nature;
    
    public Activite(String titre,String description,String siteWeb,NatureActivite nature) {
    	this.titre = titre;
    	this.description = description;
    	this.siteWeb = siteWeb;
    	this.nature = nature;
    }

	public Activite() {
		// TODO Auto-generated constructor stub
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSiteWeb() {
		return siteWeb;
	}

	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}

	public NatureActivite getNature() {
		return nature;
	}

	public void setNature(NatureActivite nature) {
		this.nature = nature;
	}

	public Long getId() {
		return id;
	}
	
	
   
}
