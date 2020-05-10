package main.controllers.addSchool;

import javafx.fxml.Initializable;
import main.resources.utilities.connection.DatabaseConnect;
import main.resources.utilities.handles.AddSchoolHandle;
import main.resources.utilities.notification.Alerts;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddSchool extends AddSchoolHandle implements Initializable {



    @Override
    public void addSchool(){

        try {
            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID FROM SCHOOL WHERE EMAIL = ? ");
            DatabaseConnect.preparedStatement.setString(1, schoolEmail.getText());
            DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();
            if (DatabaseConnect.res.next()){
                Alerts.errorAlert("Duplicate Entry", "School or  exists");
            }else {
                DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("INSERT INTO SCHOOL(NAME, ADDRESS, NAME_OF_OWNER, NAME_OF_PRINCIPAL, PHONE_NO, TIME_ADDED, DATE_ADDED, EMAIL , PASSWORD)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                DatabaseConnect.preparedStatement.setString(1, schoolName.getText());
                DatabaseConnect.preparedStatement.setString(2, address.getText());
                DatabaseConnect.preparedStatement.setString(3, owner.getText());
                DatabaseConnect.preparedStatement.setString(4, principal.getText());
                DatabaseConnect.preparedStatement.setLong(5, Long.parseLong((phoneNo.getText())));
                DatabaseConnect.preparedStatement.setString(6, LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
                DatabaseConnect.preparedStatement.setString(7, new SimpleDateFormat("yyyy/mm/dd").format(new Date()));
                DatabaseConnect.preparedStatement.setString(8, schoolEmail.getText());
                DatabaseConnect.preparedStatement.setString(9, schoolPassword.getText());
                int i = DatabaseConnect.preparedStatement.executeUpdate();
                DatabaseConnect.close();

                if (i>0){
                    Alerts.successAlert("School Added successfully", schoolName.getText().toUpperCase().concat(" owned by " + owner.getText().toUpperCase() )+ " added successfully");
                    clearEntries();
                }
            }
        }catch (SQLException e) {
            Alerts.errorAlert("Error adding Data", "School not added please try again or contact administrator");
            e.printStackTrace();
        }


    }

    private void clearEntries(){
        schoolName.setText("");
        address.setText("");
        owner.setText("");
        principal.setText("");
        phoneNo.setText("");
        schoolEmail.setText("");
        schoolPassword.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
