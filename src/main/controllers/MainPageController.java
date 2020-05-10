package main.controllers;


import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.controllers.dashboards.Dashboard;
import main.resources.models.LoginAction;
import main.resources.models.TableData;
import main.resources.models.UserData;
import main.resources.utilities.ShowPages;
import main.resources.utilities.connection.DatabaseConnect;
import main.resources.utilities.handles.MainPageHandle;
import main.resources.utilities.notification.Alerts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainPageController extends MainPageHandle implements Initializable {



    private void sign_up() {
        try {

            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID FROM USERS WHERE USER_NAME = ?  OR EMAIL=  ?");
            DatabaseConnect.preparedStatement.setString(1, signUpUsername.getText());
            DatabaseConnect.preparedStatement.setString(2, email.getText());
            DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();
            if (DatabaseConnect.res.next()) {
                Alerts.errorAlert("Duplicate Entry", "Username or Email exists");
            } else {
                DatabaseConnect.con.close();
                DatabaseConnect.preparedStatement.close();
                DatabaseConnect.res.close();
                DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("INSERT INTO USERS ( USER_NAME , EMAIL , PASSWORD, PRIVILEGE ) " +
                        "VALUES (?, ?, ?, ?)");
                DatabaseConnect.preparedStatement.setString(1, signUpUsername.getText());
                DatabaseConnect.preparedStatement.setString(3, password.getText());
                DatabaseConnect.preparedStatement.setString(2, email.getText());
                DatabaseConnect.preparedStatement.setInt(4, 0);

                if (DatabaseConnect.preparedStatement.executeUpdate() > 0) {
                    Alerts.successAlert("Success", "User added successfully");
                    clearEntries();
                } else {
                    Alerts.errorAlert("Error", "An error has occurred while adding data");
                }
                DatabaseConnect.con.close();
                DatabaseConnect.preparedStatement.close();
                DatabaseConnect.res.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alerts.errorAlert("Execption", e.getLocalizedMessage());
        }
    }


    @FXML
    private void loginAction(ActionEvent event) {

        try {
            if (adminCheck.isSelected()) {
                this.action = LoginAction.ADMIN;
                DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID, EMAIL FROM USERS WHERE USER_NAME = ? AND PASSWORD = ? AND PRIVILEGE = ?");
                DatabaseConnect.preparedStatement.setString(1, username.getText());
                DatabaseConnect.preparedStatement.setString(2, loginPassword.getText());
                DatabaseConnect.preparedStatement.setInt(3, 0);
                DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();

                if (DatabaseConnect.res.next()) {
                    userData = new TableData(DatabaseConnect.res.getInt("ID"), username.getText(), DatabaseConnect.res.getString("EMAIL"));
                    showPages(event);
                } else {
                    Alerts.errorAlert("An error has occurred...", "Wrong Username or Password, please try again or use the forgot password option");
                }
                DatabaseConnect.con.close();
                DatabaseConnect.preparedStatement.close();
                DatabaseConnect.res.close();
            } else {
                System.out.println(adminCheck.isSelected());
                this.action = LoginAction.SCHOOL;
                DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID, EMAIL FROM SCHOOL WHERE EMAIL = ? AND PASSWORD = ?");
                DatabaseConnect.preparedStatement.setString(1, username.getText());
                DatabaseConnect.preparedStatement.setString(2, loginPassword.getText());
                DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();

                if (DatabaseConnect.res.next()) {
                    userData =  new TableData(DatabaseConnect.res.getInt("ID"), username.getText(), DatabaseConnect.res.getString("EMAIL"));
                    showPages(event);
                } else {
                    Alerts.errorAlert("An error has occurred...", "Wrong Username or Password or you're not authorized, please try again or use the forgot password option");
                }
                DatabaseConnect.close();
            }
        }catch(SQLException | IOException e){
                Alerts.errorAlert("A Technical error has occurred...", "Please check your network main.resources.utilities.connection or contact administrator");
                e.printStackTrace();
                e.printStackTrace();
            }
        }



    private void showPages( ActionEvent event) throws IOException, SQLException {
        Dashboard.setStatus(action);
        new ShowPages().show(event);

    }


    @FXML
    public void closeAction(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void minimizeApp(MouseEvent event) {
        stage = (Stage) ico.getScene().getWindow();
        stage.setIconified(true);
    }

    private void clearEntries() {
        signUpUsername.setText("");
        email.setText("");
        password.setText("");
    }

    @FXML
    private void buttonHandler(ActionEvent event){
        if (event.getSource() == signin) {
            loginPane.toFront();
        } else if (event.getSource() == signup) {
            signUpPane.toFront();
        } else if (event.getSource() == doSignup) {
            sign_up();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        validator = new RequiredFieldValidator();

        try {
            icon = new FileInputStream("C:\\Users\\OLAMIDE\\Documents\\Eclipse Workspace\\signUpExample\\src\\main\\images\\icons8_cancel_15px.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        validator.setMessage("No Input Given");
        username.getValidators().add(validator);
        signUpUsername.getValidators().add(validator);
        email.getValidators().add(validator);
        password.getValidators().add(validator);
        loginPassword.getValidators().add(validator);

        username.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                username.validate();
            }
        });

        signUpUsername.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                signUpUsername.validate();
            }
        });

        email.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                email.validate();
            }
        });

        password.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                password.validate();
            }
        });

        loginPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                loginPassword.validate();
            }
        });

        icn = new Image(icon);
        validator.setIcon(new ImageView(icn));
        checkPrivilege();
    }

    public void checkPrivilege() {
        if (adminCheck.isSelected()){
            username.setPromptText("USERNAME");
        }else {
            username.setPromptText("SCHOOL EMAIL");
        }
    }


}
