/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Jakub Siembida
 */
public class MovieBean extends AbstractBean{
    private static int x = 0;
    private final SimpleStringProperty title = new SimpleStringProperty("");
    private final SimpleStringProperty country = new SimpleStringProperty("");
    private final SimpleStringProperty language = new SimpleStringProperty("");

    public MovieBean() {
        this("", "", "");
    }
    
    public MovieBean(String[] data){
        this(data[0], data[1], data[2]);
    }
    
    public MovieBean(String title, String country, String language){
        ++x;
        this.title.set(title);
        this.country.set(country);
        this.language.set(language);
    }
    
    public void setTitle(String title){
        this.title.set(title);
    }

    public void setCountry(String country){
        this.country.set(country);
    }

    public void setLanguage(String language){
        this.language.set(language);
    }

    public String getTitle(){
       return title.get();
    }

    public String getCountry(){
       return country.get();
    }

    public String getLanguage(){
        return language.get();
    }
   
    public SimpleStringProperty titleProperty(){
        return title;
    }
    
    public SimpleStringProperty countryProperty(){
        return country;
    }
    
    public SimpleStringProperty languageProperty(){
        return language;
    }

    @Override
    public void setData(String[] data) {
        this.title.set(data[0]);
        this.country.set(data[1]);
        this.language.set(data[2]);
    }
    
    @Override
    public AbstractBean newInstance(){
        MovieBean b = new MovieBean();
        return b;
    }
    
}
