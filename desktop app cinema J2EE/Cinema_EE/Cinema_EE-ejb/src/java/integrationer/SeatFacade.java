/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrationer;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sub_business_tier.entities.Movie;
import sub_business_tier.entities.Screening;
import sub_business_tier.entities.Seat;

/**
 *
 * @author stecu
 */
@Stateless
public class SeatFacade extends AbstractFacade<Seat> {

    @PersistenceContext(unitName = "Cinema_EE-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SeatFacade() {
        super(Seat.class);
    }
    
     public void addSeats(List<Movie> movies){
         for(Movie movie : movies){
            if(movie.getId() == null) continue;
            for(Screening screening : movie.getScreenings()){
                if(screening.getId() == null) continue;
            for(Seat seat: screening.getSeats()){
            if(seat.getId()==null)
                getEntityManager().persist(seat);
            }
            }
        }/*
        for(Seat seat: seats){
            if(seat.getId()==null)
                getEntityManager().persist(seat);
        }
*/
    }
    
}
