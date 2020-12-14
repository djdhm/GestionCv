package gestion.views;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import gestion.entities.Personne;
import gestion.services.UserService;

@Named
@ViewScoped
public class LoginView implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	UserService userService;
	
	
    private String email;
    private String password;
 
    private String redirectTo;
     
    public String getRedirectTo() {
		return redirectTo;
	}

	public void setRedirectTo(String redirectTo) {
		this.redirectTo = redirectTo;
	}

	public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 

 
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String login() {
		System.out.println("Test");
		Personne p = userService.login(email, password);
		System.out.println(p);
		if(p==null) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Email ou Mot de passe incorrect","Veuillez reessayez!"));
			return "";
		}else {
			
			return "/search.xhtml?faces-redirect=true";
		}
//		System.out.println(p);
//		if(p==null) {
//			FacesContext.getCurrentInstance().addMessage(
//                    null,
//                    new FacesMessage(FacesMessage.SEVERITY_WARN,
//                    "Invalid Login!",
//                    "Please Try Again!"));
//			return "/";
//		}
//		else {
//			HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//			redirectTo = req.getParameter("redirect-to");
//			if(redirectTo == null ) return "hello.xhtml";
//			else return redirectTo;
//		}
		
       
    }
}
