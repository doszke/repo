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
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

/**
 *
 * @author Jakub Siembida
 */
public class RemoveMovieController implements Initializable{
    
    private String[] movieSearchData = {"-1"};
    
    private TableDataFactory factory = new TableDataFactory();
    
    @FXML
    private TableView<MovieBean> movies_tv;
    
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
    
    @FXML void onClickBtn(ActionEvent event){
        Main.alertGenerator.showAlert(Alert.AlertType.INFORMATION, "Removing movie", Main.getFacade().removeMovie(movieSearchData));
        ObservableList<MovieBean> x = factory.createTableData(Main.getFacade().getMovieModels(), new MovieBean());
        movies_tv.getItems().clear();
        movies_tv.setItems(x); //refresh
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        factory.tableFactory(
                movies_tv, 
                MovieBean.class, 
                Main.getFacade().getMovieModels(), 
                new MovieBean()
        );
        
        movies_tv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                movieSearchData = new String[]{"1", newSelection.getTitle(), newSelection.getCountry(), newSelection.getTitle()};
            }   
        });
    }/*
    
    @FXML void onClickBtn(ActionEvent event){
        Main.alertGenerator.showAlert(Alert.AlertType.INFORMATION, "Removing movie", Main.getFacade().removeMovie(movieSearchData));
        ObservableList<MovieBean> x = factory.createTableData(Main.getFacade().getMovieModels(), new MovieBean());
        movies_tv.getItems().clear();
        movies_tv.setItems(x);
    }
    */
    @FXML
    void persistDataClicked(ActionEvent event) throws Exception {
        Main.getFacade().addMovies();
    }

    @FXML
    void showDatabaseClicked(ActionEvent event) throws Exception {
        
            ObservableList<MovieBean> x = factory.createTableData(Main.getFacade().movies(), new MovieBean());
            movies_tv.getItems().clear();
            movies_tv.setItems(x);
    }
    @FXML
    void notInBaseClicked (ActionEvent event){
       ObservableList<MovieBean> x = factory.createTableData(Main.getFacade().getMovieModels(), new MovieBean());
       movies_tv.getItems().clear();
        movies_tv.setItems(x);
    }
    /*
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        factory.tableFactory(
                movies_tv, 
                MovieBean.class, 
                Main.getFacade().getMovieModels(), 
                new MovieBean()
        );
        
        movies_tv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                movieSearchData = new String[]{"1", newSelection.getTitle(), newSelection.getCountry(), newSelection.getTitle()};
            }   
        });
    }
*/
}
