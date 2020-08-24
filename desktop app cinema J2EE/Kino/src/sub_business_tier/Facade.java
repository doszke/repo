package sub_business_tier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import sub_business_tier.entities.Client;
import sub_business_tier.entities.Movie;
import sub_business_tier.entities.Reservation;
import sub_business_tier.entities.Screening;

/**
 *
 * @author Weronika, Jakub
 */
public class Facade {

    public Facade() {
        Factory factory = new Factory();
        clients = new ArrayList<>();
        clients.add(factory.createClient(new String[]{"0", "not", "registered", "client", "1"}));
    }

    List<Movie> movies = new ArrayList<>();
    List<Client> clients;

    //getJS
    public List<Movie> getMovies() {
        return movies;
    }

    public List<Client> getClients() {
        return clients;
    }
    
    public List<Reservation> getReservations() {
        List<Reservation> allRes = new ArrayList<>(1000);//zapewniam minimum 1000 rezerwacji- wydajniejsze łączenie list
        clients.forEach((c) -> {
            allRes.addAll(c.getReservations());
        });
        return allRes;
    }
    
    

    public List<String[]> getMovieModels() {
        List<String[]> moviesModel = new ArrayList<>();
        movies.forEach((movie) -> {
            moviesModel.add(movie.toModel());
        });
        return moviesModel;
    }

    public List<String[]> getClientModels() {
        List<String[]> clientsModel = new ArrayList<>();
        clients.forEach((client) -> {
            clientsModel.add(client.toModel());
        });
        return clientsModel;
    }

    String[] kom = {"no such screening", "no free seat"};
    
    public List<String[]> getScreeningModels(String[] data){
        Movie helpMovie = new Factory().createMovie(data);
        return searchMovie(helpMovie).getScreeningModels();
    }
    
    public List<String[]> getAllReservations() {
        List<String[]> allRes = new ArrayList<>(1000);//zapewniam minimum 1000 rezerwacji- wydajniejsze łączenie list
        clients.forEach((c) -> {
            allRes.addAll(c.getReservationModels());
        });
        return allRes;
    }
    
    public List<String[]> getReservationsModelForClient(String[] data){
        Factory factory = new Factory();
        Client help_client = factory.createClient(data);
        int idx;
        if((idx = clients.indexOf(help_client)) != -1){
            return clients.get(idx).getReservationModels();
        }
        return null;
    }


    public String searchMovie(String[] data) {
        Factory factory = new Factory();
        Movie movie = factory.createMovie(data);
        if (searchMovie(movie) == null) {
            return null;
        }
        return movie.toString();
    }
    
    public List<Screening> getScreeningsForMovie(String[] data){
        Factory factory = new Factory();
        Movie help_movie = factory.createMovie(data);
        int idx;
        if((idx = movies.indexOf(help_movie)) != -1){
            return movies.get(idx).getScreenings();
        }
        return null;
    }
    
    public List<Reservation> getReservationsForClient(String[] data){
        Factory factory = new Factory();
        Client help_client = factory.createClient(data);
        int idx;
        if((idx = clients.indexOf(help_client)) != -1){
            return clients.get(idx).getReservations();
        }
        return null;
    }

    public String addReservation(String[] datamovie, String[] datascreen, String[] dataclient, String[] datares) {
        String result = null;
        int nr;
        Factory factory = new Factory();
        Movie helpMovie = factory.createMovie(datamovie), movie;
        movie = this.searchMovie(helpMovie);
        if (movie != null) {
            Client helpClient = factory.createClient(dataclient), client;
            client = this.searchClient(helpClient);
            if (client != null) {
                if ((nr = movie.searchFreeScreening(datascreen)) == 2) {
                    result = client.addReservation(movie.getFreeSeat(), datares);
                } else {
                    result = kom[nr];
                }
            } else {
               // result = "no such a client";
               this.addClient(dataclient);
                if ((nr = movie.searchFreeScreening(datascreen)) == 2) {
                    result = clients.get(clients.size() - 1).addReservation(movie.getFreeSeat(), datares);
                } else {
                    result = kom[nr];
                }
            }
        } else {
            result = "no such a movie";
        }
        return result;
    }

    public String addClient(String[] data) {
        Factory factory = new Factory();
        Client client = factory.createClient(data);
        if (searchClient(client) == null) {
            clients.add(client);
            return client.toString();
        }
        return null;
    }

    public String addMovie(String[] data) {
        Factory factory = new Factory();
        Movie movie = factory.createMovie(data);
        if (searchMovie(movie) == null) {
            movies.add(movie);
            return movie.toString();
        }
        return null;
    }

    public String addScreening(String[] data1, String[] data2) {
        Factory factory = new Factory();
        Movie movie = factory.createMovie(data1), foundMovie;
        if ((foundMovie = searchMovie(movie)) != null) {
            return foundMovie.addScreening(data2);
        }
        return null;
    }

    public String buyTicket(String[] datamovie, String[] datascreen, String[] dataclient, String[] datares) {
        Factory factory = new Factory();
        String[] c = {"0", "not", "registered", "client", "1"};
        clients.add(factory.createClient(c));
        return addReservation(datamovie, datascreen, c, datares);
    }

    public String modifyMovie(String[] data1, String[] data2) {
        Factory factory = new Factory();
        Movie help1 = factory.createMovie(data1), movieexist;
        Movie help2 = factory.createMovie(data2);
        if (searchMovie(help2) == null) //po modyfikacji film nie moze posiadac danych istniejcego filmu
        {
            if ((movieexist = searchMovie(help1)) != null) {
                movieexist.modify(help2);
                return movieexist.toString();
            }
        }
        return null;
    }
    
    
    public String removeReservation(String[] data1, String[] data2){
        Factory factory = new Factory();
        Client helpClient = factory.createClient(data1), client;
        if((client = searchClient(helpClient)) != null){
            return client.removeReservation(data2);
        }
        return "No such client";
    }

    public String removeMovie(String[] data) {
        Factory factory = new Factory();
        Movie movie = factory.createMovie(data);
        int idx = movies.indexOf(movie);
        if (idx != -1) {
            movie = movies.remove(idx);
            
            movie.removeScreenings();
            return movie.toString();
        }
        return null;
    }    

    public String removeClient(String[] data) {
        Factory factory = new Factory();
        Client client = factory.createClient(data);
        int idx = clients.indexOf(client);
        if (idx == -1) {
            return "no such client";
        }
        client = clients.remove(idx);
        client.removeReservation();
        return client.toString();
    }
    
    public String removeScreening(String[] data1, String[] data2) {
        Factory factory = new Factory();
        Movie movie = factory.createMovie(data1), existmovie;
        if ((existmovie = searchMovie(movie)) != null) {
            return existmovie.removeScreening(data2);
        }
        return null;
    }

    public Movie searchMovie(Movie movie) {
        int idx;
        if ((idx = movies.indexOf(movie)) != -1) {
            return movies.get(idx);
        }
        return null;
    }

    public Client searchClient(Client client) {
        int idx;
        if ((idx = clients.indexOf(client)) != -1) {
            return clients.get(idx);
        }
        return null;
    }

    public String showOffer() {
        String s = "";
        for (Movie movie : movies) {
            s += movie.toString() + "\n";
        }
        return s;
    }

    public String modifyReservation(String[] reservationSearchData, String[] clientSearchData, String[] movieSearchData, String[] screeningSearchData) {
        Factory factory = new Factory();
        Client helpClient = factory.createClient(clientSearchData), client;
        if((client = searchClient(helpClient)) != null){
            Movie helpMovie = factory.createMovie(movieSearchData), movie;
            if((movie = searchMovie(helpMovie)) != null){
                return client.modifyReservation(movie, reservationSearchData, screeningSearchData);
            }
        }
        return "No such client";
    }

    
    public static void main(String[] args) {
        Facade facade = new Facade();
        System.out.println("stworzenie klienta");
        String[] add_client_str1 = {"0", "Jan", "Nowak", "Email1", "1237819"};
        String[] add_client_str2 = {"0", "Anna", "Kowalska", "Email2", "99999"};
        System.out.println(facade.addClient(add_client_str1)); //doda
        System.out.println(facade.addClient(add_client_str2)); //doda
        System.out.println(facade.addClient(add_client_str2)); //nie doda
        System.out.println("\nstworzenie filmu");
        String[] add_movie_str1 = {"0", "Tytuł1", "108", "Kraj1", "polski"};
        String[] add_movie_str2 = {"0", "Tytuł2", "90", "Kraj2", "angielski"};
        System.out.println(facade.addMovie(add_movie_str1)); //doda
        System.out.println(facade.addMovie(add_movie_str2)); //doda
        System.out.println(facade.addMovie(add_movie_str2)); //nie doda
        System.out.println("\nstworzenie seansu");
        String[] add_scr_str1 = {"2020-03-21", "20:20"};
        String[] add_scr_str2 = {"2019-06-26", "9:07"};
        System.out.println(facade.addScreening(add_movie_str1, add_scr_str1)); //doda
        System.out.println(facade.addScreening(add_movie_str1, add_scr_str2)); //doda
        System.out.println(facade.addScreening(add_movie_str1, add_scr_str2)); //nie doda
        System.out.println(facade.addScreening(add_movie_str2, add_scr_str1)); //doda
        System.out.println(facade.addScreening(add_movie_str2, add_scr_str2)); //doda
        System.out.println(facade.addScreening(add_movie_str2, add_scr_str1)); //nie doda
        System.out.println("na kolekcji filmów wywołuje toString");
        for (Movie movie : facade.movies) {
            System.out.println(movie);
        }
        System.out.println("\n");
        System.out.println("stworzenie rezerwacji");
        String[] add_res_str1 = {"0", "true", "43432443"};
        String[] add_res_str2 = {"0", "false", "13123123"};
        System.out.println(facade.addReservation(add_movie_str1, add_scr_str1, add_client_str1, add_res_str1));//doda
        System.out.println(facade.addReservation(add_movie_str1, add_scr_str1, add_client_str1, add_res_str1));// nie doda
        System.out.println(facade.addReservation(add_movie_str1, add_scr_str1, add_client_str2, add_res_str2));//doda
        System.out.println(facade.addReservation(add_movie_str2, add_scr_str2, add_client_str2, add_res_str1));//doda
        System.out.println(facade.addReservation(add_movie_str1, add_scr_str1, add_client_str2, add_res_str1));//nie doda
        
        String[] add_client_str3 = {"0", "", "", "Email2", "99999"};
        String[] add_movie_str3 = {"0", "", "90", "", ""};
        String[] add_scr_str3 = {"2018-06-26", "9:07"};
        System.out.println(facade.addReservation(add_movie_str1, add_scr_str1, add_client_str3, add_res_str1));//nie doda
        System.out.println(facade.addReservation(add_movie_str3, add_scr_str1, add_client_str2, add_res_str1));//nie doda
        System.out.println(facade.addReservation(add_movie_str1, add_scr_str3, add_client_str2, add_res_str1));//nie doda
        System.out.println("\nwyświetlam kolekcję clients");
//
        for (Client client : facade.clients) {
            System.out.println(client);
        }
        

        System.out.println("\nModyfikacja danych filmu- add_movie_str1 zmieniam na add_movie_str4");
        String[] add_movie_str4 = {"0", "Tytuł3", "90", "Kraj2", "angielski"};
        String[] search_movie_str1 = {"1", "Tytuł1", "Kraj1", "polski"};
        System.out.println(facade.modifyMovie(search_movie_str1, add_movie_str4));//podmieni
        System.out.println("\nList of movies");
        System.out.println(Arrays.toString(facade.movies.toArray()) + "\n");

        System.out.println("\nusuwanie klienta");
//
        String[] search_client_str1 = {"1", "Jan", "Nowak", "Email1"};
        String[] search_client_str2 = {"1", "Anna", "Kowalska", "Email2"};
        System.out.println("\nclients.size() : " + facade.clients.size() + "\n");
        System.out.println("\nRezerwacje przed usunieciem klienta\n");
        System.out.println("\nReservations : " + facade.movies + "\n");
        System.out.println(facade.removeClient(search_client_str1));//usunie
        System.out.println("\nReservations : " + facade.movies + "\n");
        System.out.println(facade.removeClient(search_client_str2));//usunie
        System.out.println("\nReservations : " + facade.movies + "\n");
        System.out.println("\nclients.size() : " + facade.clients.size() + "\n");
        System.out.println("\n\nUsuwanie seansu");
        String[] search_movie_str2 = {"1", "Tytuł2", "Kraj2", "angielski"};
        String[] search_scr_str1 = {"2020-03-21", "20:20"};
        String[] search_scr_str2 = {"2019-06-26", "9:07"};
        System.out.println("przed:\n" + Arrays.toString(facade.movies.toArray()) + "\n");
        System.out.println(facade.removeScreening(search_movie_str1, search_scr_str1));//usunie
        System.out.println(facade.removeScreening(search_movie_str1, search_scr_str2));//usunie
        System.out.println(facade.removeScreening(search_movie_str2, search_scr_str1));//usunie
        System.out.println("\npo:\n" + Arrays.toString(facade.movies.toArray()) + "\n");
//        System.out.println("\nUsuwanie filmu");
//        System.out.println("movies.size(): " + facade.movies.size());
//        System.out.println(facade.removeMovie(search_movie_str1));
//        System.out.println(facade.removeMovie(search_movie_str2));
//        System.out.println("movies.size(): " + facade.movies.size());
        System.out.println("\nModyfikacja danych filmu- dodałem znowu add_movie_str1 i zmieniam na add_movie_str2");
        System.out.println(facade.addMovie(add_movie_str1)); //doda
        System.out.println("\n" + Arrays.toString(facade.movies.toArray()) + "\n");
        System.out.println(facade.modifyMovie(search_movie_str1, add_movie_str2));//podmieni
        System.out.println("\n" + Arrays.toString(facade.movies.toArray()) + "\n");

    }

    

}
