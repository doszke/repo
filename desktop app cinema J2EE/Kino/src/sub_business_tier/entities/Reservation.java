package sub_business_tier.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import sub_business_tier.Factory;

/**
 *
 * @author Magda
 */

@Entity
public class Reservation implements Serializable{

    private static final long seralVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private boolean isPaid;
    
    
    @Transient
   // @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID")
   // @ManyToOne
    private Client client;
    @ManyToOne
    private Seat seat;
    private int number;

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
    
    public void setScreening(Screening s){
        
    }
    
    public void removeFromClient() {
    //    seat.removeReservation(this);
        client.removeReservation(this);
    }
    
     public void removeFromSeat() {
        seat.removeReservation(this);
      //  client.removeReservation(this);
    }

    public int getClientId() {
        return client.getNumber();
    }

    public String getSeatStr() {
        return seat.getRow() + "," + seat.getNumberOfSeat();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.number;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reservation other = (Reservation) obj;
        if (this.number != other.number) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reservation{" + "seat : " + seat.getNumberOfSeat() + " " + seat.getScreening().getMovie().getTitle()
                + " " + seat.getScreening().getDate() + " " + seat.getScreening().getTime()
                + ", number : " + number + ", client : " + client.getLastName() + "," + client.getFirstName() + '}';
    }

    public void detachClient() {
        client.removeReservation();
    }

    public String modify(Movie movie, String[] screeningSearchData) {
        Factory factory = new Factory();
        Screening s = movie.searchScreening(factory.createScreening(screeningSearchData));
        s.searchFreeSeat();
        this.setSeat(s.getSeat());
        this.setScreening(s);
        return "Modified";
    }

    String[] toModel() {
        return new String[]{String.valueOf(number), String.valueOf(isPaid), String.valueOf(client.getNumber()), getSeatStr()};
    }

    
}
