package main.resources.utilities.handles;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

abstract public class AddSchoolHandle extends MainPageHandle {

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


    protected abstract void addSchool();
}
