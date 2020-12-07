package gestion.validators;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.validator.routines.UrlValidator;
import org.primefaces.validate.ClientValidator;

public class WebsiteValidator implements Validator<String>,ClientValidator {

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "websitte-validator";
	}

	@Override
	public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
		if(value=="") return;
		String[] schemes = {"http","https"};
		UrlValidator urlValidator = new UrlValidator(schemes);
		if (!urlValidator.isValid(value)) 
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error",
					value + " is not a valid website;"));
		
	}

}
