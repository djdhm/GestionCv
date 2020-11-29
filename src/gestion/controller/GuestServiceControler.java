package gestion.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import gestion.entities.Personne;
import gestion.services.GuestService;

@Named("guestService")
@ViewScoped
public class GuestServiceControler implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
    GuestService guestService;
	
	public List<Personne> getAllPersonnes(){
		return guestService.getAllPersonnes();
	}
	
	public String seeCV(long id){
        return guestService.seeCV(id);
}
}
