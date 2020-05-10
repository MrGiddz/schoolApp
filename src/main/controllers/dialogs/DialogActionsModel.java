package main.controllers.dialogs;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogActionsModel implements Initializable {

    @FXML
    protected JFXTextField subjectSchoolField;

    @FXML
    protected JFXPasswordField password;

    @FXML
    protected JFXPasswordField confirmPassword;

    @FXML
    protected JFXComboBox<String> schoolSelect;

    @FXML
    protected JFXComboBox<String> subjectSelect;

    @FXML
    protected JFXComboBox<String> classSelect;

    @FXML
    protected JFXRadioButton compulsory;

    @FXML
    protected VBox actionsPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
