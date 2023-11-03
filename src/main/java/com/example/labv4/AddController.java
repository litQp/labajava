package com.example.labv4;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class AddController{
    @FXML
    private TextField text_num, text_client, text_type, text_status, text_date_start, text_date_finish;
    private Stage dialog;
    private ObservableList<Agrement> agrements = FXCollections.observableArrayList();
    @FXML
    void add(ActionEvent event) {
        if(!text_num.getText().isEmpty() && !text_client.getText().isEmpty() && !text_type.getText().isEmpty()
                && !text_status.getText().isEmpty() && !text_date_start.getText().isEmpty() && !text_date_finish.getText().isEmpty()){
            agrements.add(new Agrement(text_num.getText(),text_client.getText(),text_type.getText(),text_status.getText(),
                    text_date_start.getText(), text_date_finish.getText()));
            dialog.close();}
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialog);
            alert.setTitle("Пустое поле!");
            alert.setHeaderText("Одно или несколько полей пусты!");
            alert.showAndWait();
        }
    }
    @FXML
    void cancel(ActionEvent event) {dialog.close();}
    public void getDialog(Stage dialog)
    {this.dialog = dialog;}
    public void getAgreements(ObservableList<Agrement> agrements) {this.agrements = agrements;}
}



