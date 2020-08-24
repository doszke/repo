/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkout_tier;

import bean.AbstractBean;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Jakub Siembida
 */

public class TableDataFactory {
    
    public List<String> createTableHeaders(Class c){
        //getDeclaredFields zwraca wszystkie nagłówki pól, nawet prywatnych
        return Arrays.asList(c.getDeclaredFields()).stream()
                                                   .filter(field -> !Modifier.isStatic(field.getModifiers()))
                                                   .map(field -> field.getName())
                                                   .collect(Collectors.toList());
    }
    
    
    
    public ObservableList createTableData(List<String[]> data, AbstractBean o) {
        ObservableList<AbstractBean> list = FXCollections.observableArrayList();
        for(String[] v : data){
            o.setData(v);
            list.add(o);
            o = o.newInstance();
        }   
        
        return list;
    }
            
    public <T extends AbstractBean> void tableFactory(TableView<T> tableView, Class T, List<String[]> data, AbstractBean b){
        Platform.runLater(() -> {
            for (String th : createTableHeaders(T)) {
                TableColumn<T, String> tc = new TableColumn<>(th);
                tc.setCellValueFactory(new PropertyValueFactory(th));
                tableView.getColumns().add(tc);
            }
            if(data != null && b != null){
                tableView.setItems(createTableData(data, b));
            }
        });
    }       

    public <T extends AbstractBean> void tableFactory(TableView<T> tableView, Class T) {
        tableFactory(tableView, T, null, null);
    }
    
}
