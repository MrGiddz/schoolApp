package main.controllers.dashboards;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.resources.models.LoginAction;
import main.resources.models.ViewSelect;
import main.resources.utilities.handles.DashboardHandle;
import main.controllers.viewTables.ViewTables;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Dashboard extends DashboardHandle implements Initializable, Members {

    private static LoginAction action;
    @FXML
    void sideBarHandler(ActionEvent event) throws IOException {

        if(event.getSource() == home) {
            if(Arrays.equals("home".toCharArray(), stack)){
                System.out.println("equals");
            }else{
                stack = "home".toCharArray();
                showMain();
            }

        }else if (event.getSource() == viewPayments){
                if(Arrays.equals("addSchool".toCharArray(), stack)){
                    System.out.println("equals");
                }else{
                    stack = "addSchool".toCharArray();
                    viewTable(ViewSelect.PAYMENT);
                }
            }
            else if (event.getSource() == viewSchools){
                if(Arrays.equals("viewSchools".toCharArray(), stack)){
                    System.out.println("equals");
                }else{
                    stack = "viewSchools".toCharArray();
                    viewTable(ViewSelect.SCHOOL);
                }
            }
            else if (event.getSource() == viewStudents){
                if(Arrays.equals("viewStudents".toCharArray(), stack)){
                    System.out.println("equals");
                }else{
                    stack = "viewStudents".toCharArray();
                    viewTable(ViewSelect.STUDENT);
                }
            }
            else if (event.getSource() == logout){
                ((Node) event.getSource()).getScene().getWindow().hide();
                root = FXMLLoader.load(getClass().getResource("/main/resources/views/mainPage.fxml"));
                stage = new Stage();
                stage.setTitle("School Management");
                stage.initStyle(StageStyle.TRANSPARENT);

                root.setOnMousePressed(event1 -> {
                    xOffset = event1.getSceneX();
                    yOffset = event1.getSceneY();
                });

                root.setOnMouseDragged(event1 -> {
                    stage.setX(event1.getScreenX() - xOffset);
                    stage.setY(event1.getScreenY() - yOffset);
                });

                scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                scene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
                stage.setScene(scene);
                stage.show();
            }
            System.out.println(stack);
        }


    @Override
    public void showPaymentView() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/main/resources/views/ViewTables.fxml"));
        dashboardMain.setCenter(root);
    }

    @Override
    public void setAddSchool() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/main/resources/views/AddSchool.fxml"));
        dashboardMain.setCenter(null);
        dashboardMain.setCenter(root);

    }


    @Override
    public void viewTable(ViewSelect viewSchools) throws IOException {

        switch (viewSchools){
            case STUDENT:
                ViewTables.setView(ViewSelect.STUDENT);
                break;
            case SCHOOL:
                ViewTables.setView(ViewSelect.SCHOOL);
                break;
            case PAYMENT:
                ViewTables.setView(ViewSelect.PAYMENT);
                break;
            default:
                break;
        }
        root = FXMLLoader.load(getClass().getResource("/main/resources/views/ViewTables.fxml"));
        dashboardMain.setCenter(root);


    }

    @FXML
    public void closeAction(MouseEvent event){
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void minimizeApp(MouseEvent event){
        stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void showMain() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/main/resources/views/DashBoardView.fxml"));
        dashboardMain.setCenter(root);
    }

    public static void setStatus(LoginAction actions){
        action = actions;
    }


    @SuppressWarnings("unused")
    public static LoginAction getStatus(){
        return action;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DashboardViewController.setStatus(action);
        if (action.equals(LoginAction.SCHOOL)){
            dashboardSidebar.getChildren().remove(addSchool);
            dashboardSidebar.getChildren().remove(viewSchools);
        }

        try {
            showMain();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
