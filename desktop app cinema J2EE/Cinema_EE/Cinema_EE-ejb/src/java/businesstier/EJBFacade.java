/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesstier;

import integrationer.ClientFacade;
import integrationer.MovieFacade;
import integrationer.ReservationFacade;
import integrationer.ScreeningFacade;
import integrationer.SeatFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import sub_business_tier.Facade;
import sub_business_tier.entities.Movie;

/**
 *
 * @author stecu
 */
@Stateless
public class EJBFacade implements EJBFacadeRemote {

    @EJB
    private SeatFacade seatFacade;

    @EJB
    private ScreeningFacade screeningFacade;

    @EJB
    private ReservationFacade reservationFacade;

    @EJB
    private MovieFacade movieFacade;

    @EJB
    private ClientFacade clientFacade;

    
    
    
    static Facade facade = new Facade();
    
    
    
    @Override
    public String addReservation(String[] data, String[] data1, String[] data2, String[] data3) {
        return facade.addReservation(data, data1, data2, data3);
    }

    @Override
    public List<String[]> getMovieModels() {
        return facade.getMovieModels();
    }

    @Override
    public List<String[]> getScreeningModels(String[] data) {
        return facade.getScreeningModels(data);
    }

    @Override
    public String addScreening(String[] data, String[] data1) {
        return facade.addScreening(data, data1);
    }

    @Override
    public String modifyReservation(String[] data, String[] data1, String[] data2, String[] data3) {
        return facade.modifyReservation(data, data1, data2, data3);
    }

    @Override
    public List<String[]> getAllReservations() {
        return facade.getAllReservations();
    }

    @Override
    public String removeMovie(String[] data) {
        return facade.removeMovie(data);
    }

    @Override
    public String removeReservation(String[] data, String[] data1) {
        return facade.removeReservation(data, data1);
    }

    @Override
    public List<String[]> getClientModels() {
        return facade.getClientModels();
    }

    @Override
    public String removeScreening(String[] data, String[] data1) {
        return facade.removeScreening(data, data1);
    }

    @Override
    public List<String[]> getReservationsModelForClient(String[] data) {
        return facade.getReservationsModelForClient(data);
    }

    @Override
    public String addMovie(String[] data) {
        return facade.addMovie(data);
    }

    @Override
    public void addMovies() throws Exception {
        movieFacade.addMovies(facade.getMovies());
    }
    
    @Override
    public ArrayList<String[]> movies() throws Exception{
        List<Movie> help1 = movieFacade.findAll();
        ArrayList<String[]> help2 = new ArrayList();
        for(Movie m : help1) {
            String[] help3 = m.toModel();
            help2.add(help3);}
        return help2;
    }
    
    
    @Override
    public ArrayList<String[]> screenings() throws Exception{
        List<Movie> help1 = movieFacade.findAll();
        ArrayList<String[]> help2 = new ArrayList();
        for(Movie m : help1) {
            List<String[]> help3 = m.getScreeningModels();
            help2.addAll(help3);}
        return help2;
    }

    @Override
    public void addReservations() throws Exception {
        reservationFacade.addReservations(facade.getClients(),facade.getMovies());
    }

    @Override
    public void addScreenings() throws Exception {
        screeningFacade.addScreenings(facade.getMovies());
    }

    @Override
    public void addClients() throws Exception {
        clientFacade.addClients(facade.getClients());
    }

    @Override
    public void addSeats() throws Exception {
        seatFacade.addSeats(facade.getMovies());
    }
    
}
