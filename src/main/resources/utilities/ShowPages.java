package main.resources.utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.resources.utilities.handles.DashboardHandle;
import main.resources.utilities.handles.MainPageHandle;
import main.resources.utilities.notification.Alerts;

import java.io.IOException;

public class ShowPages extends DashboardHandle {


    protected void showAdmin(ActionEvent event) throws IOException {

        Alerts.successAlert("Login successful", "You are logged in successfully, proceed to your dashboard");
        ((Node) event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/views/Dashboard.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event1 -> {
            xOffset = event1.getSceneX();
            yOffset = event1.getSceneY();
        });

        root.setOnMouseDragged(event1 -> {
            stage.setX(event1.getScreenX() - xOffset);
            stage.setY(event1.getScreenY() - yOffset);
        });

        stage.setScene(scene);
        stage.show();

        MainPageHandle.stage = stage;
    }

    public void show(ActionEvent event) throws IOException {
        Alerts.successAlert("Login successful", "You are logged in successfully, proceed to your dashboard");
        ((Node) event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/views/Dashboard.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event1 -> {
            xOffset = event1.getSceneX();
            yOffset = event1.getSceneY();
        });

        root.setOnMouseDragged(event1 -> {
            stage.setX(event1.getScreenX() - xOffset);
            stage.setY(event1.getScreenY() - yOffset);
        });

        stage.setScene(scene);
        stage.show();

        MainPageHandle.stage = stage;
    }

}
