package gestion.validators;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.validate.ClientValidator;

public class PasswordStrengthValidator implements Validator<String>, ClientValidator{
    private static int calculatePasswordStrength(String password){
        
        //total score of password
        int iPasswordScore = 0;
        
        if( password.length() < 8 )
            return 0;
        else if( password.length() >= 10 )
            iPasswordScore += 2;
        else 
            iPasswordScore += 1;
        
        //if it contains one digit, add 2 to total score
        if( password.matches("(?=.*[0-9]).*") )
            iPasswordScore += 2;
        
        //if it contains one lower case letter, add 2 to total score
        if( password.matches("(?=.*[a-z]).*") )
            iPasswordScore += 2;
        
        //if it contains one upper case letter, add 2 to total score
        if( password.matches("(?=.*[A-Z]).*") )
            iPasswordScore += 2;    
        
        //if it contains one special character, add 2 to total score
        if( password.matches("(?=.*[~!@#$%^&*()_-]).*") )
            iPasswordScore += 2;
        
        return iPasswordScore;
        
    }
	@Override
	public Map<String, Object> getMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValidatorId() {
		// TODO Auto-generated method stub
		return "password-strength-validator";
	}

	@Override
	public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
		// TODO Auto-generated method stub
		if (value == null) {
			return;
		}

		int strength = calculatePasswordStrength(value);
		if(strength==0) throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error",
				"Please use a strong password"));
		
	}

}
