package sub_business_tier.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author Magda
 */
@Entity 
public class Seat implements Serializable{

    private static final long seralVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private int row;
    private int numberOfSeat;
    private boolean isReserved;
    
    @ManyToOne
    private Screening screening;
    
    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public Seat() {
        reservations = new ArrayList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    
    
    public int getNumberOfSeat() {
        return numberOfSeat;
    }

    public void setNumberOfSeat(int numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
    }

    public void setIsReserved(boolean free) {
        this.isReserved = free;
    }

    public Screening getScreening() {
        return screening;
    }

    public Boolean getIsReserved() {
        return isReserved;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public void addReservation(Reservation reservation) {
        reservation.setSeat(this);
        reservations.add(reservation);
        setIsReserved(true);
    }

    public void removeReservation(Reservation res) {
        reservations.remove(res);
        setIsReserved(false);
    }
    
     public void removeReservation() {
        for (Reservation t : reservations) {
            t.removeFromClient();
        }
        reservations.clear();
    }

    @Override
    public String toString() {
        return "\nRow " + getRow() + " " + getNumberOfSeat() + ", isReserved : " + getIsReserved()
                + ", reservations : " + reservations;
    }


}
