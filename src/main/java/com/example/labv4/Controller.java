package com.example.labv4;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TableColumn<Agrement, String> c_client, c_dataclose,c_dataopen,c_number, c_status,c_type;
    @FXML
    private TableView<Agrement> table;
    @FXML
    private Label l_client, l_date_finish, l_date_start, l_num, l_status, l_type;

    ObservableList<Agrement> agrements = FXCollections.observableArrayList(
            new Agrement("123D","ООО \"Техник\"","Аренда","Завершен","10.10.2023","12.10.2023"),
            new Agrement("B23","Бойко Валерий Александрович","Купли-продажи","Завершен","11.07.2023","11.07.2023"),
            new Agrement("M232","ЗАО \"Свет\"","Оказание услуг","В работе","15.10.2023","23.12.2023"));

    @FXML
    void add(ActionEvent event) throws IOException {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("add.fxml"));
        dialog.setScene(new Scene(loader.load(),600, 400));
        AddController controller = loader.getController();
        controller.getDialog(dialog);
        controller.getAgreements(agrements);
        dialog.showAndWait();
        table.setItems(FXCollections.observableList(agrements));
    }

    @FXML
    void delete(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem()!=null) {
            agrements.remove(table.getSelectionModel().getSelectedItem());
            table.setItems(FXCollections.observableList(agrements));
            l_num.setText("Договор №");
            l_client.setText("Клиент: ");
            l_type.setText("Тип договора: ");
            l_status.setText("Статус договора: ");
            l_date_start.setText("Дата заключения договора: ");
            l_date_finish.setText("Дата закрытия договора: ");
        }
    }

    @FXML
    void edit(ActionEvent event) throws IOException {
        if (table.getSelectionModel().getSelectedItem()!=null) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("edit.fxml"));
            stage.setScene(new Scene(loader.load(),600, 400));
            EditController controller = loader.getController();
            controller.getDialog(stage);
            int id = agrements.indexOf(table.getSelectionModel().getSelectedItem());
            controller.getAgreeemet(agrements.get(id));
            stage.showAndWait();
            table.setItems(agrements);
            table.getSelectionModel().clearSelection();
            table.getSelectionModel().select(id);
            table.refresh();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        c_client.setCellValueFactory(new PropertyValueFactory<Agrement,String>("client"));
        c_number.setCellValueFactory(new PropertyValueFactory<Agrement,String>("number"));
        c_type.setCellValueFactory(new PropertyValueFactory<Agrement,String>("type"));
        c_status.setCellValueFactory(new PropertyValueFactory<Agrement,String>("status"));
        c_dataopen.setCellValueFactory(new PropertyValueFactory<Agrement,String>("dateopen"));
        c_dataclose.setCellValueFactory(new PropertyValueFactory<Agrement,String>("dateclose"));
        table.setItems(FXCollections.observableList(agrements));

        table.setItems(agrements);
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Agrement agrements = table.getSelectionModel().getSelectedItem();
                l_num.setText("Договор №"+agrements.getNumber());
                l_client.setText("Клиент: "+agrements.getClient());
                l_type.setText("Тип договора: "+agrements.getType());
                l_status.setText("Статус договора: "+agrements.getStatus());
                l_date_start.setText("Дата заключения договора: "+agrements.getDateopen());
                l_date_finish.setText("Дата закрытия договора: "+agrements.getDateclose());
            }
        });
    }
}
