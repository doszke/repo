/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author Jakub Siembida
 */
public class AbstractBean {
    
    public AbstractBean() { }
    
    public void setData(String[] data){ };
    public AbstractBean newInstance(){
        return new AbstractBean();
    }
}
