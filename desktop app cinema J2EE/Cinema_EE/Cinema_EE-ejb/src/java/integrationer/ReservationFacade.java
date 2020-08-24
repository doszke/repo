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
import sub_business_tier.entities.Client;
import sub_business_tier.entities.Movie;
import sub_business_tier.entities.Reservation;
import sub_business_tier.entities.Screening;
import sub_business_tier.entities.Seat;

/**
 *
 * @author stecu
 */
@Stateless
public class ReservationFacade extends AbstractFacade<Reservation> {

    @PersistenceContext(unitName = "Cinema_EE-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservationFacade() {
        super(Reservation.class);
    }

    public void addReservations(List<Client> clients, List<Movie> movies) {

        Seat hseat = null;
        Client hclient = null;
        Reservation hres = null;
        for (Movie movie : movies) {
            if (movie.getId() == null) {
                continue;
            }
            for (Screening screening : movie.getScreenings()) {
                if (screening.getId() == null) {
                    continue;
                }
                for (Seat seat : screening.getSeats()) {
                    if (seat.getId() == null) {
                        hseat = seat;
                    }
                    for (Client client : clients) {
                        if (client.getId() == null) {
                            continue;
                        }
                        hclient = client;
                        for (Reservation res : client.getReservations()) {
                            if (res.getSeat() == hseat) {
                                hres = res;
                            }
                        }
                    }
                }
            }
        }

        if (hclient != null && hseat != null && hres == null) {

            getEntityManager().persist(hres);
        }

    }
}
