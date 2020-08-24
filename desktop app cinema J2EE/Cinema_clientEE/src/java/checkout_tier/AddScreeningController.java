/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkout_tier;

import bean.MovieBean;
import checkout_tier.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Jakub Siembida
 */
public class AddScreeningController implements Initializable{
    
    static final String[] movies_th = {"title", "country", "language"};
    private String[] movieSearchData = {"-1"};
    
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
    private TextField date;

    @FXML
    private TextField time;

    @FXML
    private Button btn;

    @FXML
    private TableView<MovieBean> movies_tv;
    
    @FXML
    void onClickBtn(ActionEvent event) {
        String[] screeningData = {date.getText(), time.getText()};
        Main.alertGenerator.showAlert(AlertType.INFORMATION, "Creating a screening", Main.getFacade().addScreening(movieSearchData, screeningData));
    }
    
    
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
        TableDataFactory factory = new TableDataFactory();
        factory.tableFactory(movies_tv, MovieBean.class, Main.getFacade().getMovieModels(), new MovieBean());
        
        
        movies_tv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                movieSearchData = new String[]{"1", newSelection.getTitle(), newSelection.getCountry(), newSelection.getLanguage()};
            }
        });
        
        
    }

}
