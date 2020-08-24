/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkout_tier;

import bean.MovieBean;
import bean.ScreeningBean;
import checkout_tier.Main;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddResController implements Initializable{

    private String[] movieSearchData = {"-1"};
    private String[] screeningSearchData = {"-1"};
    private final String[] NO_CLIENT = new String[]{"1", "not", "registered", "client"}; 
    
    @FXML
    private Button btn;
    
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
    private TableView<MovieBean> movies_tv;

    @FXML
    private TableView<ScreeningBean> screenings_tv;
    
    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField email;

    @FXML
    private TextField number;

    @FXML
    private CheckBox isPaid;
    
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
    void onClickBtn(ActionEvent event) throws Exception{
        String[] clientSearchData = {"1", firstname.getText(), lastname.getText(), email.getText()};
        for(String data : clientSearchData){
            if(data.equals("")){
               clientSearchData = NO_CLIENT;
               break; 
            }
        }
        
        
        String[] resData = new String[]{"0", Boolean.toString(isPaid.isSelected()), number.getText()};
        
        System.out.println("DODAWANIE REZERWACJI");
        System.out.println("moviesearchdata: " + Arrays.toString(movieSearchData));
        System.out.println("screeningsearchdata: " + Arrays.toString(screeningSearchData));
        System.out.println("Clientsearchdata: " + Arrays.toString(clientSearchData));
        System.out.println("resdata: " + Arrays.toString(resData));
        Main.alertGenerator.showAlert(AlertType.INFORMATION, "Making a reservation", Main.getFacade().addReservation(movieSearchData, screeningSearchData, clientSearchData, resData));
             
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableDataFactory factory = new TableDataFactory();
        
        factory.tableFactory(movies_tv, MovieBean.class,  Main.getFacade().getMovieModels(), new MovieBean());
        
        factory.tableFactory(screenings_tv, ScreeningBean.class); 
        
        movies_tv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                movieSearchData = new String[]{"1", newSelection.getTitle(), newSelection.getCountry(), newSelection.getLanguage()};
                //dodaję dane seansów dla wybranego filmu
                screenings_tv.getItems().clear(); //czyszczę
                
                screenings_tv.setItems(
                        factory.createTableData(
                                Main.getFacade().getScreeningModels(movieSearchData), 
                                new ScreeningBean()
                        )
                );
                
                //dodaję handler do onClick
                screenings_tv.getSelectionModel().selectedItemProperty().addListener((obs2, oldSelection2, newSelection2) -> {
                    screeningSearchData = new String[]{newSelection2.getDate(), newSelection2.getTime()};
                });
            }
        });
               
    }

}
