package main.resources.utilities.notification;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;


public class Alerts {
    static Alert a;
    public static void successAlert(String headerText, String contentText){
        a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Success");
        a.setHeaderText(headerText);
        a.setContentText(contentText);
        a.showAndWait();
    }

    protected static void infoAlert(String headerText, String contentText){
        a = new Alert(Alert.AlertType.NONE);
        a.setTitle("Information");
        a.setHeaderText(headerText);
        a.setContentText(contentText);
        a.showAndWait();
    }

    public static Optional<ButtonType> confirmationDialog(String headerText, String contentText){
        a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Information");
        a.setHeaderText(headerText);
        a.setContentText(contentText);

        return a.showAndWait();
    }

    public static void errorAlert(String headerText, String contentText){
        a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(headerText);
        a.setContentText(contentText);
        a.showAndWait();
    }
}
