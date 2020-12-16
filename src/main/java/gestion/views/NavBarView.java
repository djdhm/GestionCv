package gestion.views;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import gestion.services.UserService;
@Named
@ConversationScoped
public class NavBarView implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 529111991361777756L;

	@Inject
	UserService userService;
	
	
	public boolean getLoggedIn() {
		return userService.isLoggedIn();
	///	return userService.isLoggedIn();
	}
	
	public String logout() {
		userService.logout();
		return "/search.xhtml?faces-redirect=true";
	}
	public String goToLogin() {
		return "/login.xhtml?faces-redirect=true";
	}
}
