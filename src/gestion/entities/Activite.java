package gestion.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;


@NamedQueries({ 
	@NamedQuery(name = "findAllActivites", query = "Select a from Activite a"),

	})
@Entity
@FilterDefs({
	@FilterDef(name="activity", parameters = {@ParamDef( name = "value", type="int")}),
	@FilterDef(name="description", parameters = {@ParamDef( name = "value", type="string")})

})
@Filters({
	@Filter(name = "activity", condition = "nature = :value"),
	@Filter(name = "description", condition = "lower(description) like :value"),
})
public class Activite implements Serializable {

	
	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	Long idActivity;
	
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

	public Long getIdActivity() {
		return idActivity;
	}
	
	/*public String toString() {
		return titre + " " + annee;
	}*/
	
   
}
