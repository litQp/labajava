package com.example.labv4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditController {
    @FXML

    private TextField text_num, text_client, text_type, text_status, text_date_start, text_date_finish;

    private Stage dialog;

    private Agrement agrements;

    @FXML
    void edit(ActionEvent event) {
        if (!text_num.getText().isEmpty() && !text_client.getText().isEmpty() && !text_type.getText().isEmpty()
                && !text_status.getText().isEmpty() && !text_date_start.getText().isEmpty() && !text_date_finish.getText().isEmpty()){
            agrements.setNumber(text_num.getText());
            agrements.setClient(text_client.getText());
            agrements.setType(text_type.getText());
            agrements.setStatus(text_status.getText());
            agrements.setDateopen(text_date_start.getText());
            agrements.setDateclose(text_date_finish.getText());
            dialog.close();}
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialog);
            alert.setTitle("Пустое поле!");
            alert.setHeaderText("Одно или несколько полей пусты");
            alert.showAndWait();
        }
    }

    @FXML
    void cancel(ActionEvent event) {dialog.close();}
    public void getDialog(Stage dialogStage) {this.dialog = dialogStage;}
    public void getAgreeemet(Agrement agrements) {
        text_num.setText(agrements.getNumber());
        text_client.setText(agrements.getClient());
        text_type.setText(agrements.getType());
        text_status.setText(agrements.getStatus());
        text_date_start.setText(agrements.getDateopen());
        text_date_finish.setText(agrements.getDateclose());
        this.agrements = agrements;}

}
