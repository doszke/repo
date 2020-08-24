/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Jakub Siembida
 */
public class ReservationBean extends AbstractBean{
    
    private final SimpleIntegerProperty number = new SimpleIntegerProperty(-1);
    private final SimpleBooleanProperty isPaid = new SimpleBooleanProperty(false);
    private final SimpleIntegerProperty clientId = new SimpleIntegerProperty(-1);
    private final SimpleStringProperty seat = new SimpleStringProperty("");

    public ReservationBean() { }
    
    public ReservationBean(int number, boolean isPaid, int clientId, String seat){
        this.number.set(number);
        this.isPaid.set(isPaid);
        this.clientId.set(clientId);
        this.seat.set(seat);
    }
    
    public ReservationBean(String[] data){
        number.set(Integer.parseInt(data[0]));
        isPaid.set(Boolean.valueOf(data[1]));
        clientId.set(Integer.parseInt(data[2]));
        seat.set(data[3]);
    }
    
    public String getNumber(){
        return String.valueOf(number.get());
    }
    
    public SimpleIntegerProperty numberProperty(){
        return number;
    }
    
    public SimpleBooleanProperty isPaidProperty(){
        return isPaid;
    }
    
    public SimpleIntegerProperty clientIdProperty(){
        return clientId;
    }
    
    public SimpleStringProperty seatProperty(){
        return seat;
    }

    @Override
    public void setData(String[] data) {
        number.set(Integer.parseInt(data[0]));
        isPaid.set(Boolean.valueOf(data[1]));
        clientId.set(Integer.parseInt(data[2]));
        seat.set(data[3]);
    }
    
    @Override
    public AbstractBean newInstance(){
        return new ReservationBean();
    }
    
}
