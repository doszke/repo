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
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

/**
 *
 * @author Jakub Siembida
 */
public class RemoveScreeningController implements Initializable{
    
    private TableDataFactory factory = new TableDataFactory();
    
    private String[] screeningSearchData = {"-1"};
    
    private String[] movieSearchData = {"-1"};
    
    @FXML TableView<ScreeningBean> screenings_tv;
    
    @FXML TableView<MovieBean> movies_tv;
    
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
        System.out.println(Arrays.toString(movieSearchData));
        System.out.println(Arrays.toString(screeningSearchData));
        Main.alertGenerator.showAlert(Alert.AlertType.INFORMATION, "Removing a screening", Main.getFacade().removeScreening(movieSearchData, screeningSearchData));
        screenings_tv.getSelectionModel().clearSelection();
        screenings_tv.setItems(factory.createTableData(Main.getFacade().getScreeningModels(movieSearchData), new ScreeningBean()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        factory.tableFactory(movies_tv, MovieBean.class, Main.getFacade().getMovieModels(), new MovieBean());
        
        movies_tv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                movieSearchData = new String[]{"1", newSelection.getTitle(), newSelection.getCountry(), newSelection.getLanguage()};
                //dodaję dane seansów dla wybranego filmu
                screenings_tv.getItems().clear(); //czyszczę
                screenings_tv.setItems(factory.createTableData(Main.getFacade().getScreeningModels(movieSearchData), new ScreeningBean()));
            }
        });
        
        factory.tableFactory(screenings_tv, ScreeningBean.class);
        
        screenings_tv.getSelectionModel().selectedItemProperty().addListener((obs2, oldSelection2, newSelection2) -> {
            if (newSelection2 != null) screeningSearchData = new String[]{newSelection2.getDate(), newSelection2.getTime()};
        });
        
    }
    
}
