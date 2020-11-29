package gestion.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AjaxBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String text = "";
    List<String> cities = new LinkedList<String>();

    public AjaxBean() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        System.err.println("setText1 with " + text);
    }

    public String getNow() {
        return new Date().toString();
    }
    
    public void toUpper(AjaxBehaviorEvent event) {
        text = text.toUpperCase();
    }
    

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public void addCity() {
        if (text.trim().length() > 0) {
            cities.add(text);
            System.err.println("add " + text);
            text = "";
        }
    }

    public void removeCity(int index) {
        cities.remove(index);
    }

}