package sub_business_tier.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import sub_business_tier.Factory;

/**
 *
 * @author Weronika
 */
@Entity
public class Client implements Serializable {
    
    private static final long seralVersionUID = 1L;
    
    private String firstName;
    private String lastName;
    private int Number;
    private String email;
    
    @Transient
    //@OneToMany(mappedBy = "client", cascade = ALL)
    private List<Reservation> reservations;
    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    public Client() {
        reservations = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    
    
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (getFirstName().equals(((Client) obj).getFirstName())) {
            if (getLastName().equals((((Client) obj).getLastName()))) {
                if (getEmail().equals((((Client) obj).getEmail()))) {
                    result = true;
                }
            }
        }
        return result;
    }

    public String addReservation(Seat seat, String[] isPaid) {
        Factory factory = new Factory();
        Reservation reservation = factory.createReservation(isPaid);
        if (searchReservation(reservation) == null) {
            reservation.setClient(this);
            reservations.add(reservation);
            seat.addReservation(reservation);
            return "reserved";
        }
        return "such reservation already exists";
    }

    public String removeReservation(String[] data){
        Factory factory = new Factory();
        Reservation helpReservation = factory.createReservation(data), res;
        if((res = searchReservation(helpReservation)) != null){
            res.removeFromSeat();
            res.detachClient();
            return "reservation removed";
        }
        return "No such reservation for specified client";
    }
    
    public void removeReservation() {
        for (Reservation t : reservations) {
            t.removeFromSeat();
         //   t.detachClient();
        }
        reservations.clear();
    }

    public Reservation searchReservation(Reservation reservation) {
        int idx;
        if ((idx = reservations.indexOf(reservation)) != -1) {
            return reservations.get(idx);
        }
        return null;
    }

    public Reservation searchReservation(String[] dataReservation) {
        Reservation help1, reservationExists;
        Factory fabryka = new Factory();
        help1 = fabryka.createReservation(dataReservation);
        if ((reservationExists = this.searchReservation(help1)) != null) {
            return reservationExists;
        }
        return null;
    }
    
    public void removeReservation(Reservation res) {
        reservations.remove(res);
    }

    @Override
    public String toString() {
        return "Imie : " + getFirstName()
                + ", Nazwisko : " + getLastName()
                + ", Email : " + getEmail()
                + ", Numer klienta : " + getNumber()
                + ", Rezerwacje : " + Arrays.toString(reservations.toArray());
    }

    public String modifyReservation(Movie movie, String[] reservationSearchData, String[] screeningSearchData) {
        Factory factory = new Factory();
        Reservation helpRes = factory.createReservation(reservationSearchData), res;
        if((res = searchReservation(helpRes)) != null){
            return res.modify(movie, screeningSearchData);
        }
        return "No such reservation";
    }
    
    public String[] toModel() {
        return new String[]{firstName, lastName, email};
    }

    public List<String[]> getReservationModels() {
        List<String[]> reservationsModel = new ArrayList<>();
        reservations.forEach((reservation) -> {
            reservationsModel.add(reservation.toModel());
        });
        return reservationsModel;
    }



}
