/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkout_tier;

import bean.MovieBean;
import bean.ReservationBean;
import bean.ScreeningBean;
import checkout_tier.Main;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ModifyResController implements Initializable{

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
    private TableView<ReservationBean> reservations_tv;
    
    @FXML
    private TableView<MovieBean> movies_tv;

    @FXML
    private TableView<ScreeningBean> screenings_tv;
    
    private String[] reservationSearchData;
    
    private String[] screeningSearchData = {"-1"};
    
    private String[] movieSearchData = {"-1"};
    /*
    private List<String> screenings_th = Arrays.asList(ScreeningBean.class.getDeclaredFields()).stream() //niestatyczne, mapowanie pola na jego reprezentacje Stringową
                                                                                               .filter(field -> !Modifier.isStatic(field.getModifiers()))
                                                                                               .map(field -> field.getName())
                                                                                               .collect(Collectors.toList());
    private List<String> movies_th = Arrays.asList(MovieBean.class.getDeclaredFields()).stream()
                                                                                       .filter(field -> !Modifier.isStatic(field.getModifiers()))
                                                                                       .map(field -> field.getName())
                                                                                       .collect(Collectors.toList());
    */
    
    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField email;

    
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
    
    @FXML
    void onClickDeselectMovie(ActionEvent event){
        screenings_tv.getSelectionModel().clearSelection();
        screenings_tv.getItems().clear();
        movies_tv.getSelectionModel().clearSelection();
        movieSearchData = new String[]{"-1"};
    }
    
    @FXML
    void onClickDeselectScreening(ActionEvent event){
        if(!Arrays.equals(movieSearchData, new String[]{"-1"})){
            screenings_tv.getSelectionModel().clearSelection();
            screeningSearchData = new String[]{"-1"};
        }
    }

    @FXML
    void onClickBtn(ActionEvent event){
        String[] clientSearchData = {"1", firstname.getText(), lastname.getText(), email.getText()};
        Main.alertGenerator.showAlert(Alert.AlertType.INFORMATION, 
                                      "Modifying reservation", 
                                      Main.getFacade().modifyReservation(reservationSearchData, 
                                                                         clientSearchData, 
                                                                         movieSearchData, 
                                                                         screeningSearchData));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableDataFactory factory = new TableDataFactory();
        //init nagłówków
                
        factory.tableFactory(
                reservations_tv, 
                ReservationBean.class, 
                Main.getFacade().getAllReservations(), 
                new ReservationBean()
        );
        
        factory.tableFactory(movies_tv, MovieBean.class);
        factory.tableFactory(screenings_tv, ScreeningBean.class);
        
        reservations_tv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                reservationSearchData = new String[]{"1", newSelection.getNumber()};
                movies_tv.getItems().clear();
                movies_tv.setItems(
                        factory.createTableData(
                            Main.getFacade().getMovieModels(), 
                            new MovieBean()
                        )
                );
            }
        });
        
        movies_tv.getSelectionModel().selectedItemProperty().addListener((obs1, oldSelection1, newSelection1) -> {
            if (newSelection1 != null) {
                movieSearchData = new String[]{"1", newSelection1.getTitle(), newSelection1.getCountry(), newSelection1.getLanguage()};
                //dodaję dane seansów dla wybranego filmu
                screenings_tv.getItems().clear(); //czyszczę
                screenings_tv.setItems(
                        factory.createTableData(
                            Main.getFacade().getScreeningModels(movieSearchData), 
                            new ScreeningBean()
                        )
                );
            }
        });
        
        screenings_tv.getSelectionModel().selectedItemProperty().addListener((obs2, oldSelection2, newSelection2) -> {
            if (newSelection2 != null) screeningSearchData = new String[]{newSelection2.getDate(), newSelection2.getTime()};
        });
    }
    
}
