package com.example.labv4;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Controller implements Initializable {
    @FXML
    private TableColumn<Agreement, String> c_client, c_dataclose,c_dataopen,c_number, c_status,c_type;
    @FXML
    private TableView<Agreement> table;
    @FXML
    private Label l_client, l_date_finish, l_date_start, l_num, l_status, l_type;

    private Stage stage;

    ObservableList<Agreement> agreements = FXCollections.observableArrayList();

    @FXML
    void add(ActionEvent event) throws IOException {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("add.fxml"));
        dialog.setScene(new Scene(loader.load(),600, 400));
        AddController controller = loader.getController();
        controller.getDialog(dialog);
        controller.getAgreements(agreements);
        dialog.showAndWait();
        table.setItems(FXCollections.observableList(agreements));
    }

    @FXML
    void delete(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem()!=null) {
            agreements.remove(table.getSelectionModel().getSelectedItem());
            table.setItems(FXCollections.observableList(agreements));
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
            int id = agreements.indexOf(table.getSelectionModel().getSelectedItem());
            controller.getAgreeemet(agreements.get(id));
            stage.showAndWait();
            table.setItems(agreements);
            table.getSelectionModel().clearSelection();
            table.getSelectionModel().select(id);
            table.refresh();
        }
    }

    public void getStage(Stage stage)
    {this.stage = stage;}

    public File getFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApplication.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApplication.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Обновление заглавия сцены.
            stage.setTitle("Договоры - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Обновление заглавия сцены.
            stage.setTitle("Договоры");
        }
    }

    public void loadDataFromFile(File file) {
        try {

            JAXBContext context = JAXBContext
                    .newInstance(AgreementListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            // Чтение XML из файла и демаршализация.
            AgreementListWrapper wrapper = (AgreementListWrapper) um.unmarshal(file);

            agreements = FXCollections.observableList(wrapper.getAgreements());
            table.setItems(agreements);

            // Сохраняем путь к файлу в реестре.
            setFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не получилось открыть файл");
            alert.setContentText("Не получилось загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void saveDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(AgreementListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Обёртываем наши данные об преподавателях.
            AgreementListWrapper wrapper = new AgreementListWrapper();
            wrapper.setAgreements(agreements);

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);

            // Сохраняем путь к файлу в реестре.
            setFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не получилось открыть файл");
            alert.setContentText("Не получилось загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    @FXML
    private void new_file(ActionEvent event) {
        agreements.clear();
        table.setItems(agreements);
        setFilePath(null);
    }

    @FXML
    private void open_file(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            loadDataFromFile(file);
        }
    }

    /**
     * Сохраняет файл в файл адресатов, который в настоящее время открыт.
     * Если файл не открыт, то отображается диалог "save as".
     */
    @FXML
    private void save_file(ActionEvent event) {
        File personFile = getFilePath();
        if (personFile != null) {
            saveDataToFile(personFile);
        } else {
            save_as_file(event);
        }
    }

    /**
     * Открывает FileChooser, чтобы пользователь имел возможность
     * выбрать файл, куда будут сохранены данные
     */
    @FXML
    private void save_as_file(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            saveDataToFile(file);
        }
    }

    /**
     * Открывает диалоговое окно about.
     */
    @FXML
    private void about(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Договоры");
        alert.setHeaderText("О приложении");
        alert.setContentText("Автор: Бойко Валерий \nИСТ-331");

        alert.showAndWait();
    }

    /**
     * Закрывает приложение.
     */
    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        c_client.setCellValueFactory(new PropertyValueFactory<Agreement,String>("client"));
        c_number.setCellValueFactory(new PropertyValueFactory<Agreement,String>("number"));
        c_type.setCellValueFactory(new PropertyValueFactory<Agreement,String>("type"));
        c_status.setCellValueFactory(new PropertyValueFactory<Agreement,String>("status"));
        c_dataopen.setCellValueFactory(new PropertyValueFactory<Agreement,String>("dateopen"));
        c_dataclose.setCellValueFactory(new PropertyValueFactory<Agreement,String>("dateclose"));
        table.setItems(FXCollections.observableList(agreements));

        table.setItems(agreements);
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Agreement agrements = table.getSelectionModel().getSelectedItem();
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
