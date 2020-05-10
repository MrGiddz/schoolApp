package main.controllers.dialogs;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
import main.controllers.dialogs.events.DialogAction;
import main.resources.models.TableData;
import main.resources.utilities.connection.DatabaseConnect;
import main.resources.utilities.notification.Alerts;
import main.resources.utilities.notification.DialogStore;
import main.resources.utilities.validations.Validations;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class EditSchool extends DialogStore {

private TableData schools;
    void setSchool(TableData school, DialogPane pane, DialogAction action) {

        this.schools = school;
        if (action.equals(DialogAction.ADD_SCHOOL)  || action.equals(DialogAction.EDIT_SCHOOL)){
            schoolName.textProperty().bindBidirectional(school.getSchoolName());
            address.textProperty().bindBidirectional(school.getAddress());
            owner.textProperty().bindBidirectional(school.getName_of_owner());
            principal.textProperty().bindBidirectional(school.getName_of_principal());
            phoneNo.textProperty().bindBidirectional(school.getPhone_no());
            schoolEmail.textProperty().bindBidirectional(school.SchoolEmailProperty());

            if (action.equals(DialogAction.ADD_SCHOOL)){
                schoolPassword.textProperty().bindBidirectional(school.getPassword());
            }else {

                Integer removeRow = GridPane.getRowIndex(schoolPassword);
                modifyDialog.getChildren().removeIf(node -> removeRow.equals(GridPane.getRowIndex(node)));
            }
        }else if (action.equals(DialogAction.ADD_STUDENT)  || action.equals(DialogAction.EDIT_STUDENT)){
            System.out.println(action);
        }



        Button okButton = (Button) pane.lookupButton(ButtonType.FINISH);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!validateFormData()){
                System.out.println("Finished Clicked");
                event.consume();
                if (updateSchool(action)){
                    Alerts.successAlert("Success", "Action succeeded");
                }
            }
        });
    }


    private boolean validateFormData() {
        return (Validations.isEmpty(schoolName) || Validations.isEmpty(address) || Validations.isEmpty(owner) || Validations.isEmpty(principal) || Validations.isEmpty(phoneNo) || Validations.isEmpty(schoolEmail));
    }



    private boolean updateSchool(DialogAction action){

        try {
                DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID FROM SCHOOL WHERE EMAIL = ? ");
                DatabaseConnect.preparedStatement.setString(1, ShowDialog.data.SchoolEmailProperty().get());
                DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();

            if (DatabaseConnect.res.next()){
                Alerts.errorAlert("Duplicate Entry", "School or  exists");
            }else {
                if (action.equals(DialogAction.EDIT_SCHOOL)){
                    DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("UPDATE SCHOOL SET NAME = ?, ADDRESS = ?," +
                            " NAME_OF_OWNER = ?, NAME_OF_PRINCIPAL = ?, PHONE_NO = ?, TIME_ADDED = ?, DATE_ADDED = ?, EMAIL = ? WHERE ID = ?");
                    DatabaseConnect.preparedStatement.setInt(9, schools.getSchoolId().get());
                }else if (action.equals(DialogAction.ADD_SCHOOL)){
                    DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("INSERT INTO SCHOOL(NAME, ADDRESS, NAME_OF_OWNER, NAME_OF_PRINCIPAL, PHONE_NO, TIME_ADDED, DATE_ADDED, EMAIL , PASSWORD, CAN_LOGIN)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    DatabaseConnect.preparedStatement.setString(9, schools.getPassword().get());
                    DatabaseConnect.preparedStatement.setString(10, "NO");
                }

                DatabaseConnect.preparedStatement.setString(1, schools.getSchoolName().get());
                DatabaseConnect.preparedStatement.setString(2, schools.getAddress().get());
                DatabaseConnect.preparedStatement.setString(3, schools.getName_of_owner().get());
                DatabaseConnect.preparedStatement.setString(4, schools.getName_of_principal().get());
                DatabaseConnect.preparedStatement.setLong(      5, Long.parseLong((schools.getPhone_no().get())));
                DatabaseConnect.preparedStatement.setString(6, LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
                DatabaseConnect.preparedStatement.setString(7, new SimpleDateFormat("yyyy/mm/dd").format(new Date()));
                DatabaseConnect.preparedStatement.setString(8, schools.SchoolEmailProperty().get());
                int i = DatabaseConnect.preparedStatement.executeUpdate();
                DatabaseConnect.close();

                if (i>0){
                  return true;
                }
            }
        }catch (SQLException e) {
            Alerts.errorAlert("Error adding Data", "School not added please try again or contact administrator");
            e.printStackTrace();
        }
        return false;

    }

}
