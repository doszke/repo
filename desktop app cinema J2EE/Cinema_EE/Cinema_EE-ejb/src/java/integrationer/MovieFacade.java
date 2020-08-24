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

/**
 *
 * @author stecu
 */
@Stateless
public class MovieFacade extends AbstractFacade<Movie> {

    @PersistenceContext(unitName = "Cinema_EE-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MovieFacade() {
        super(Movie.class);
    }
    
    public void addMovies(List<Movie> movies){
        for(Movie movie:movies){
            if(movie.getId()==null)
                getEntityManager().persist(movie);
        }
    }
    
}
