/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesstier;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author stecu
 */
@Remote
public interface EJBFacadeRemote {

    public String addReservation(String[] data, String[] data1, String[] data2, String[] data3);

    public List<String[]> getMovieModels();

    public List<String[]> getScreeningModels(String[] data);

    public String addScreening(String[] data, String[] data1);

    public String modifyReservation(String[] data, String[] data1, String[] data2, String[] data3);

    public List<String[]> getAllReservations();

    public String removeMovie(String[] data);

    public String removeReservation(String[] data, String[] data1);

    public List<String[]> getClientModels();

    public String removeScreening(String[] data, String[] data1);

    public List<String[]> getReservationsModelForClient(String[] data);

    public String addMovie(String[] data);
    
    //zapis do bazy danych
    public void addMovies() throws Exception;
    
    public void addReservations() throws Exception;
    
    public void addScreenings() throws Exception;
    
    public void addClients() throws Exception;
    
    public void addSeats() throws Exception;

    public ArrayList<String[]> movies() throws Exception;

    public ArrayList<String[]> screenings() throws Exception;
    
}
