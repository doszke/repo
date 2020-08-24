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
public class ClientBean extends AbstractBean{
    
    private final SimpleStringProperty firstName = new SimpleStringProperty("");
    private final SimpleStringProperty lastName = new SimpleStringProperty("");
    private final SimpleStringProperty email = new SimpleStringProperty("");

    public ClientBean() { }
    
    public ClientBean(String firstname, String lastName, String email){
        this.firstName.set(firstname);
        this.lastName.set(lastName);
        this.email.set(email);
    }

    public ClientBean(String[] s) {
        this(s[0], s[1], s[2]);
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }
    
    public String getFirstName(){
        return firstName.get();
    }
    
    public String getLastName(){
        return lastName.get();
    }
    
    public String getEmail(){
        return email.get();
    }

    @Override
    public void setData(String[] data) {
        this.firstName.set(data[0]);
        this.lastName.set(data[1]);
        this.email.set(data[2]);
    }
    
    @Override
    public AbstractBean newInstance(){
        return new ClientBean();
    }
    
}
