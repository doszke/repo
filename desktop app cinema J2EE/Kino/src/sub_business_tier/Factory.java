package sub_business_tier;

import java.time.LocalDate;
import java.time.LocalTime;
import sub_business_tier.entities.Client;
import sub_business_tier.entities.Movie;
import sub_business_tier.entities.Reservation;
import sub_business_tier.entities.Screening;
import sub_business_tier.entities.Seat;

public class Factory {

    public Reservation createReservation(String[] data) {
        Reservation reservation = null;
        switch (Integer.parseInt(data[0])) {
            case 0:
                reservation = new Reservation();
                reservation.setIsPaid(Boolean.valueOf(data[1]));
                reservation.setNumber(Integer.parseInt(data[2]));
                break;
            case 1:
                reservation = new Reservation();
                reservation.setNumber(Integer.parseInt(data[1]));
                break;
        }
        return reservation;
    }

    public Seat createSeat(String[] data) {  
        Seat seat = new Seat();
        seat.setRow(Integer.parseInt(data[1]));
        seat.setNumberOfSeat(Integer.parseInt(data[2]));
        return seat;
    }
    
    public Seat createSeat(int[] data) {  
        Seat seat = new Seat();
        seat.setRow(data[0]);
        seat.setNumberOfSeat(data[1]);
        return seat;
    }

    public Client createClient(String[] data) {
        Client client = null;
        switch (Integer.parseInt(data[0])) {
            case 0:
                client = new Client();
                client.setFirstName(data[1]);
                client.setLastName(data[2]);
                client.setEmail(data[3]);
                client.setNumber(Integer.parseInt(data[4]));
                break;

            case 1:
                client = new Client();
                client.setFirstName(data[1]);
                client.setLastName(data[2]);
                client.setEmail(data[3]);
                break;
        }
        return client;
    }

    public Movie createMovie(String[] data) {
        Movie movie = null;
        switch (Integer.parseInt(data[0])) {
            case 0:
                movie = new Movie();
                movie.setTitle(data[1]);
                movie.setRunningTime(Integer.parseInt(data[2]));
                movie.setCountry(data[3]);
                movie.setLanguage(data[4]);
                break;
            case 1:
                movie = new Movie();
                movie.setTitle(data[1]);
                movie.setCountry(data[2]);
                movie.setLanguage(data[3]);
                break;
        }
        return movie;
    }

    public Screening createScreening(String[] data) {
        Screening screening = new Screening();
        String[] dateParts = data[0].split("-");
        screening.setDate(LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2])));
        String[] timeParts = data[1].split(":");
        screening.setTime(LocalTime.of(Integer.parseInt(timeParts[0]), Integer.parseInt(timeParts[1])));
        return screening;
    }

}
