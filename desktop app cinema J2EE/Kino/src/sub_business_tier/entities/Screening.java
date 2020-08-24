package sub_business_tier.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import sub_business_tier.Factory;

/**
 *
 * @author Jakub Siembida
 */
@Entity
public class Screening implements Serializable{

    private static final long seralVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private LocalDate date;
    private LocalTime time;
    
    @OneToMany(mappedBy = "screening", cascade  = CascadeType.ALL)
    private List<Seat> seats;
    
    @ManyToOne
    private Movie movie;
    
    @Transient
    private Seat seat;

    //każdy screening ma stała liczbe miejsc: 1 x 2 – przy testowaniu funkcjonalnym
    {
        seats = new ArrayList<>();
        Factory factory = new Factory();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 20; j++) {
                Seat seat = factory.createSeat(new int[]{i, j});
                seat.setScreening(this);
                seats.add(seat);
            }
        }
    }

    public Screening() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Movie getMovie() {
        return movie;
    }

    public String removeReservation() {
        for (Seat seat : seats) {
           seat.removeReservation();
        }
            return null;
        }

    public List<Seat> getSeats() {
        return seats;
    }

    

    public String addSeat(String[] data) {
        Factory factory = new Factory();
        Seat seat = factory.createSeat(data);
        if (searchSeat(seat) == null) {
            seats.add(seat);
            seat.setScreening(this);
            return seat.toString();
        }
        return null;
    }

    public String removeSeat(String[] data) {
        Factory factory = new Factory();
        Seat seat = factory.createSeat(data);
        if (searchSeat(seat) != null) {
            seats.remove(seat);
            return seat.toString();
        }
        return null;
    }

    public Seat searchSeat(Seat seat) {
        int idx;
        if ((idx = seats.indexOf(seat)) != -1) {
            return seats.get(idx);
        }
        return null;
    }

    public Seat searchSeat(String[] data) {
        Seat help1, seatExists;
        Factory fabryka = new Factory();
        help1 = fabryka.createSeat(data);
        if ((seatExists = this.searchSeat(help1)) != null) {
            return seatExists;
        }
        return null;
    }

    public boolean searchFreeSeat() {
        for (int i = 0; i < seats.size(); i++) {
            seat = seats.get(i);
            if (!seat.getIsReserved()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Screening other = (Screening) obj;
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "\nScreening {" + " date : " + date + ", time : " + time + " seats : " + seats + '}';
    }

    String[] toModel() {
        return new String[]{movie.getTitle(), date.toString(), time.toString()};
    }


}
