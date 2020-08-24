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

/**
 *
 * @author stecu
 */
@Stateless
public class ClientFacade extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "Cinema_EE-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientFacade() {
        super(Client.class);
    }
    
    public void addClients(List<Client> clients){
        for(Client client:clients){
            if(client.getId()==null)
                getEntityManager().persist(client);
        }
    }
    
}
