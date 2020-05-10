package main.resources.utilities.notification;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public abstract class DialogStore {

    @FXML
    protected GridPane modifyDialog;

    @FXML
    protected JFXTextField schoolName;

    @FXML
    protected JFXTextField address;

    @FXML
    protected JFXTextField owner;

    @FXML
    protected JFXTextField principal;

    @FXML
    protected JFXTextField phoneNo;

    @FXML
    protected JFXTextField schoolEmail;

    @FXML
    protected JFXPasswordField schoolPassword;
}
