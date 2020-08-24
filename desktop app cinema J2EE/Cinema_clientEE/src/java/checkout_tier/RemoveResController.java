/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkout_tier;

/**
 *
 * @author Jakub Siembida
 */
import bean.ClientBean;
import bean.ReservationBean;
import checkout_tier.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RemoveResController implements Initializable{

    private final String[] NO_CLIENT = new String[]{"1", "not", "registered", "client"};
    
    private final TableDataFactory factory = new TableDataFactory();
    
    private String[] reservationSearchData = {"-1"};
    
    private String[] clientSearchData = {"-1"};
    
    @FXML TableView<ReservationBean> reservations_tv;
    
    @FXML TableView<ClientBean> clients_tv;
    
    @FXML
    private MenuItem movAdd;

    @FXML
    private MenuItem movRemove;

    @FXML
    private MenuItem resAdd;

    @FXML
    private MenuItem resModify;

    @FXML
    private MenuItem resRemove;

    @FXML
    private MenuItem scrAdd;

    @FXML
    private MenuItem scrRemove;

    @FXML
    void onClickMovAdd(ActionEvent event) {
        Main.swapScene("movAdd");
    }

    @FXML
    void onClickMovRemove(ActionEvent event) {
        Main.swapScene("movRemove");
    }

    @FXML
    void onClickResAdd(ActionEvent event) {
        Main.swapScene("resAdd");
    }

    @FXML
    void onClickResModify(ActionEvent event) {
        Main.swapScene("resModify");
    }

    @FXML
    void onClickResRemove(ActionEvent event) {
        Main.swapScene("resRemove");
    }

    @FXML
    void onClickScrAdd(ActionEvent event) {
        Main.swapScene("scrAdd");
    }

    @FXML
    void onClickScrRemove(ActionEvent event) {
        Main.swapScene("scrRemove");
    }

    
    @FXML void onClickBtn(ActionEvent event) throws Exception{
        
        Main.getFacade().addReservations();
        //Main.alertGenerator.showAlert(Alert.AlertType.INFORMATION, "Deleting a reservation", Main.getFacade().removeReservation(clientSearchData, reservationSearchData));
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        factory.tableFactory(clients_tv, ClientBean.class, Main.getFacade().getClientModels(), new ClientBean());
        
        factory.tableFactory(reservations_tv, ReservationBean.class, null, null);
        
        clients_tv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                clientSearchData = new String[]{"1", newSelection.getFirstName(), newSelection.getLastName(), newSelection.getEmail()};
                reservations_tv.setItems(
                        factory.createTableData(
                                Main.getFacade().getReservationsModelForClient(clientSearchData), 
                                new ReservationBean()
                        )
                );
            }
        });
        
        reservations_tv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                reservationSearchData = new String[]{"1", newSelection.getNumber()};   
            }
        });
        
    }

}
