package com.example.labv4;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TableColumn<Agrement, String> c_client, c_dataclose,c_dataopen,c_number, c_status,c_type;
    @FXML
    private TableView<Agrement> table;

    ArrayList<Agrement> agrements = new ArrayList<>(Arrays.asList(
            new Agrement("123D","ООО \"Техник\"","Аренда","Завершен","10.10.2023","12.10.2023"),
            new Agrement("B23","Бойко Валерий Александрович","Купли-продажи","Завершен","11.07.2023","11.07.2023"),
            new Agrement("M232","ЗАО \"Свет\"","Оказание услуг","В работе","15.10.2023","23.12.2023")));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        c_client.setCellValueFactory(new PropertyValueFactory<Agrement,String>("client"));
        c_number.setCellValueFactory(new PropertyValueFactory<Agrement,String>("number"));
        c_type.setCellValueFactory(new PropertyValueFactory<Agrement,String>("type"));
        c_status.setCellValueFactory(new PropertyValueFactory<Agrement,String>("status"));
        c_dataopen.setCellValueFactory(new PropertyValueFactory<Agrement,String>("dateopen"));
        c_dataclose.setCellValueFactory(new PropertyValueFactory<Agrement,String>("dateclose"));
        table.setItems(FXCollections.observableList(agrements));
    }
}
