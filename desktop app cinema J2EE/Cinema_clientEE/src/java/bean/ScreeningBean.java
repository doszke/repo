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
public class ScreeningBean extends AbstractBean{
   private static int x = 0;
    private final SimpleStringProperty movie = new SimpleStringProperty("");
    private final SimpleStringProperty date = new SimpleStringProperty("");
    private final SimpleStringProperty time = new SimpleStringProperty("");

    public ScreeningBean() {
        this("", "", "");
    }
    
    public ScreeningBean(String[] data){
        this(data[0], data[1], data[2]);
    }
    
    public ScreeningBean(String movie, String date, String time){
        ++x;
        this.movie.set(movie);
        this.date.set(date);
        this.time.set(time);
    }
    
    public void setMovie(String movie){
        this.movie.set(movie);
    }

    public void setDate(String date){
        this.date.set(date);
    }

    public void setTime(String time){
        this.time.set(time);
    }

    public String getMovie(){
       return movie.get();
    }

    public String getDate(){
       return date.get();
    }

    public String getTime(){
        return time.get();
    }
   
    public SimpleStringProperty movieProperty(){
        return movie;
    }
    
    public SimpleStringProperty dateProperty(){
        return date;
    }
    
    public SimpleStringProperty timeProperty(){
        return time;
    }
    
    @Override
    public void setData(String[] data) {
        this.movie.set(data[0]);
        this.date.set(data[1]);
        this.time.set(data[2]);
    }
    
    @Override
    public AbstractBean newInstance(){
        return new ScreeningBean();
    }
}
