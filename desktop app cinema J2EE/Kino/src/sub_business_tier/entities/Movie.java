package sub_business_tier.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import static javax.persistence.CascadeType.ALL;
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
public class Movie implements Serializable{

    private static final long seralVersionUID = 1L;
    
    private String title, country, language;
    private int runningTime;
    
    @Transient
    private Screening screening;
    
    @OneToMany(mappedBy = "movie", cascade = ALL)
    private List<Screening> screenings;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    public Movie() {
        screenings = new ArrayList<>();
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public Seat getFreeSeat() {
        return screening.getSeat();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.title);
        hash = 97 * hash + Objects.hashCode(this.country);
        hash = 97 * hash + Objects.hashCode(this.language);
        hash = 97 * hash + this.runningTime;
        hash = 97 * hash + Objects.hashCode(this.screenings);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        Movie movie = (Movie) obj;
        if (getTitle().equals(movie.getTitle())) {
            if (getCountry().equals(movie.getCountry())) {
                if (getLanguage().equals(movie.getLanguage())) {
                    return true;
                }
            }
        }
        return false;
    }

    public String addScreening(String[] data) {
        Factory factory = new Factory();
        Screening screening = factory.createScreening(data);
        if (searchScreening(screening) == null) {
            screenings.add(screening);
            screening.setMovie(this);
            return toString();
        }
        return null;
    }

    public String removeScreening(String[] data) {
        Factory factory = new Factory();
        Screening screening = factory.createScreening(data);
        int idx = screenings.indexOf(screening);
        if (idx == -1) {
            return "no such screening";
        }
        screening = screenings.remove(idx);
        return screening.removeReservation();
    }

    public void removeScreenings() {
        for (Screening screning : screenings) {
            screning.removeReservation();
        }
    }
    
    public void removeScreening(Screening scre) {
        screenings.remove(scre);
        scre.removeReservation();
    }

    public Screening searchScreening(Screening screening) {
        int idx;
        if ((idx = screenings.indexOf(screening)) != -1) {
            return screenings.get(idx);
        }
        return null;
    }

    public Screening searchScreening(String[] dataClient, String[] dataMovie, String[] dataScreening) {
        Screening help1, screeningExists;
        Factory fabryka = new Factory();
        help1 = fabryka.createScreening(dataScreening);
        if ((screeningExists = this.searchScreening(help1)) != null) {
            return screeningExists;
        }
        return null;
    }

    public int searchFreeScreening(String[] dataScreening) {
        Screening help1;
        Factory fabryka = new Factory();
        help1 = fabryka.createScreening(dataScreening);
        if ((screening = this.searchScreening(help1)) != null) {
            if (screening.searchFreeSeat()) {
                return 2;
            } else {
                return 1;
            }
        }
        return 0;
    }

    public void modify(Movie help2) {
        if(!help2.getCountry().equals("N/A"))
            this.setTitle(help2.getTitle());
        if(!help2.getCountry().equals("N/A"))
            this.setCountry(help2.getCountry());
        if(!help2.getLanguage().equals("N/A"))
            this.setLanguage(help2.getLanguage());
        if(help2.getRunningTime() != -1)
            this.setRunningTime(help2.getRunningTime());
    }

    @Override
    public String toString() {
        return "\nMovie {" + "title : " + title
                + ", country : " + country + ", language : "
                + language + ", runningTime : " + runningTime
                + ", \nscreenings : " + Arrays.toString(screenings.toArray()) + '}';
    }

    public void modifyReservation(String[] clientSearchData, String[] screeningSearchData, String[] reservationSearchData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

        public String[] toModel() {
        return new String[]{title, country, language};
    }

    public List<String[]> getScreeningModels() {
        List<String[]> screeningsModel = new ArrayList<>();
        screenings.forEach((screening) -> {
            screeningsModel.add(screening.toModel());
        });
        return screeningsModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
}
