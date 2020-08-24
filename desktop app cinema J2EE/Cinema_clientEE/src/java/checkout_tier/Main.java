/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkout_tier;

import businesstier.EJBFacadeRemote;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.ejb.EJB;

/**
 *
 * @author Jakub Siembida
 */
public class Main extends Application {

    @EJB
    private static EJBFacadeRemote facade;
    
    private static class View {
        public View(){}
        
        public Scene createStage(String alias) throws IOException{
            return new Scene(FXMLLoader.load(getClass().getResource(sceneRefs.get(alias))));
        }
    }
    
    public static final AlertGenerator alertGenerator = new AlertGenerator();
    
    private static final View view = new View();
        
    private static final Map<String, String> sceneRefs = new TreeMap<>();
    
    private static Stage primaryStage;
    
    
    public static EJBFacadeRemote getFacade() {
        return facade;
    }
    
    public static void swapScene(String alias){
        try {
            primaryStage.setScene(view.createStage(alias)); //za każdym razem nowa scena- dzięki temu automatycznie refreshuje tabele
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(Thread.currentThread().getName());
        //metoda uruchamiana tylko raz
        primaryStage = stage; //singleton
        sceneRefs.put("resAdd", "addRes.fxml");
        sceneRefs.put("resModify", "modifyRes.fxml");
        sceneRefs.put("resRemove", "removeRes.fxml");
        sceneRefs.put("scrAdd", "addScreening.fxml");
        sceneRefs.put("scrRemove", "removeScreening.fxml");
        sceneRefs.put("movAdd", "addMovie.fxml");
        sceneRefs.put("movRemove", "removeMovie.fxml");
        //sceneRefs.put("resRemove", new Scene(FXMLLoader.load(getClass().getResource("removeRes.fxml"))));
        //sceneRefs.put("resRemove", new Scene(FXMLLoader.load(getClass().getResource("removeRes.fxml"))));
        //sceneRefs.put("resRemove", new Scene(FXMLLoader.load(getClass().getResource("removeRes.fxml"))));
        sceneRefs.put("main", "main.fxml");        
        
        swapScene("main");//ta metoda ustawia scene do primaryStage
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
