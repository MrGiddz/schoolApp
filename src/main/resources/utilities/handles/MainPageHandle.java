package main.resources.utilities.handles;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.resources.models.LoginAction;
import main.resources.models.TableData;
import main.resources.models.TableHolder;

import java.io.FileInputStream;
import java.text.ParseException;

abstract public class MainPageHandle extends TableHolder {

    @FXML
    protected JFXButton signin;

    @FXML
    protected JFXButton signup;

    @FXML
    protected ScrollPane signUpPane;

    @FXML
    protected JFXTextField signUpUsername;

    @FXML
    protected JFXTextField email;

    @FXML
    protected JFXPasswordField password;

    @FXML
    protected ScrollPane loginPane;

    @FXML
    protected JFXTextField username;

    @FXML
    protected JFXPasswordField loginPassword;

    @FXML
    protected FontAwesomeIconView ico;

    @FXML
    protected JFXButton doSignup;

    @FXML
    protected JFXCheckBox adminCheck;

    protected LoginAction action;

    protected static double xOffset = 0;
    protected static double yOffset = 0;
    protected static Parent root;
    protected static Stage stage;
    protected static Scene scene;
    protected static RequiredFieldValidator validator;
    protected static FileInputStream icon = null;
    protected static Image icn;
    protected static TableData userData;

    static {
        try {
            userData = new TableData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
