package main.controllers.dialogs;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.controllers.dashboards.DashboardViewController;
import main.controllers.dialogs.events.DialogAction;
import main.resources.models.*;
import main.resources.utilities.LoadData;
import main.resources.utilities.collections.ReversibleWeakHashMap;
import main.resources.utilities.connection.DatabaseConnect;
import main.resources.utilities.handles.MainPageHandle;
import main.resources.utilities.notification.Alerts;
import main.resources.utilities.validations.Validations;

import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class DialogActions<T> extends MainPageHandle implements Initializable {

    @FXML
    protected JFXTextField subjectSchoolField;

    @FXML
    protected JFXTextField tuition;

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

    @FXML
    private Label radioLabel;


    private T actionClass;

    private ReversibleWeakHashMap<Integer, String> subjectSchoolHolder;

    @SuppressWarnings("unused")
    public DialogActions() {}


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        compulsory.setOnAction(event -> {
            System.out.println(compulsory.isSelected());
            if (compulsory.isSelected()) {
                radioLabel.setText("Yes");
            }else {
                radioLabel.setText("No");
            }
        });

    }

    void setDialogs(DialogAction actionType, T object, DialogPane pane){

        this.actionClass = object;

        try {
            if (DashboardViewController.getStatus().equals(LoginAction.ADMIN)){
                subjectSchoolHolder = new ReversibleWeakHashMap<>();
                for (Map.Entry<Integer, TableData> e : LoadData.getSchoolFullInfo().entrySet()) {
                    subjectSchoolHolder.put(e.getKey(), e.getValue().getSchoolName().get());
                    schoolSelect.getItems().add(e.getValue().getSchoolName().get());
                }


                for (Map.Entry<Integer, ClassData> e : LoadData.getClassFullInfo().entrySet()) {
                    classSelect.getItems().add(e.getValue().getName());
                }
            }else {

                subjectSchoolHolder = new ReversibleWeakHashMap<>();
                for (Map.Entry<Integer, SubjectData> e : LoadData.getSubjectBasicInfo().entrySet()) {
                    subjectSchoolHolder.put(e.getKey(), e.getValue().getSubjectName());
                    subjectSelect.getItems().add(e.getValue().getSubjectName());
                }

                for (Map.Entry<Integer, ClassData> e : LoadData.getClassBasicInfo().entrySet()) {
                    classSelect.getItems().add(e.getValue().getName());
                }
            }



        } catch (SQLException e) { e.printStackTrace(); }

        switch (actionType){
            case ADD_SUBJECT:
            case EDIT_SUBJECT:
                doSubject();
                break;
            case ADD_CLASS:
            case EDIT_CLASS:
                doClass();
                break;
            case ADD_TEACHER:
            case EDIT_TEACHER:
                doTeacher();
                break;
            default:
                break;
        }

        Button okButton = (Button) pane.lookupButton(ButtonType.FINISH);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            try{
                if (actionType.equals(DialogAction.ADD_SUBJECT) || actionType.equals(DialogAction.EDIT_SUBJECT) )
                    updateSubject(actionType);
                else if (actionType.equals(DialogAction.ADD_TEACHER) || actionType.equals(DialogAction.EDIT_TEACHER) )
                    updateTeacher(actionType);
                else if (actionType.equals(DialogAction.ADD_CLASS) || actionType.equals(DialogAction.EDIT_CLASS) )
                    updateClass(actionType);


            }
            catch (SQLException e) {
                e.printStackTrace();
            }


            if (!validateFormData()){
                event.consume();
                if (authorizeLogin()){
                    Alerts.successAlert("Success", "Action succeeded");
                }
            }

        });
    }

    private void doTeacher() {
            actionsPane.getChildren().removeAll(classSelect, schoolSelect, compulsory, password, tuition, confirmPassword);
            subjectSchoolField.textProperty().bindBidirectional(((TeacherData) actionClass).nameProperty());
    }

    private void doClass() {
            actionsPane.getChildren().removeAll(classSelect, subjectSelect, schoolSelect, compulsory, password, confirmPassword);
            subjectSchoolField.textProperty().bindBidirectional(((ClassData) actionClass).nameProperty());
            tuition.textProperty().bindBidirectional(((ClassData) actionClass).tuitionProperty(), NumberFormat.getNumberInstance());
    }

    private void doSubject() {
        actionsPane.getChildren().removeAll(classSelect, subjectSelect, schoolSelect, tuition, password, confirmPassword);
        subjectSchoolField.textProperty().bindBidirectional(((SubjectData) actionClass).subjectNameProperty());
        radioLabel.textProperty().bindBidirectional(((SubjectData) actionClass).compulsoryProperty());
    }

    private boolean validateFormData() {
        return Validations.isEmpty(password) || Validations.isEmpty(confirmPassword);
    }

    private void updateSubject(DialogAction action) throws SQLException {
        if (action.equals(DialogAction.ADD_SUBJECT)){
            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID FROM SUBJECT WHERE SUBJECT_NAME = ? ");
            DatabaseConnect.preparedStatement.setString(1, subjectSchoolField.getText());
            DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();
            if (DatabaseConnect.res.next()){
                Alerts.errorAlert("Duplicate value", "Value already exists");
            }
            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("INSERT INTO SUBJECT ( SCHOOL_ID , SUBJECT_NAME , COMPULSORY , TIME_ADDED , DATE_ADDED ) VALUES ( ? , ? , ? , ? , ? ) ");
            DatabaseConnect.preparedStatement.setInt(1, userData.getUserId());
            DatabaseConnect.preparedStatement.setString(2, ((SubjectData)actionClass).getSubjectName());
            DatabaseConnect.preparedStatement.setBoolean(3, compulsory.isSelected());
            DatabaseConnect.preparedStatement.setString(4, LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
            DatabaseConnect.preparedStatement.setString(5, new SimpleDateFormat("yyyy/mm/dd").format(new Date()));

            if(DatabaseConnect.preparedStatement.executeUpdate() > 0){
                System.out.println("Done");
            }
        }else{
            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("UPDATE SUBJECT SET SCHOOL_ID = ?, SUBJECT_NAME = ? , COMPULSORY = ? , TIME_ADDED = ? , DATE_ADDED = ? WHERE ID = ?");

            DatabaseConnect.preparedStatement.setInt(1, userData.getUserId());
            DatabaseConnect.preparedStatement.setString(2, ((SubjectData)actionClass).getSubjectName());
            DatabaseConnect.preparedStatement.setBoolean(3, compulsory.isSelected());
            DatabaseConnect.preparedStatement.setString(4, LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
            DatabaseConnect.preparedStatement.setString(5, new SimpleDateFormat("yyyy/mm/dd").format(new Date()));
            DatabaseConnect.preparedStatement.setInt(6, ((SubjectData)actionClass).getId());

            if(DatabaseConnect.preparedStatement.executeUpdate() > 0){
                System.out.println("Done");
            }
        }

    }

    private void updateTeacher(DialogAction action) throws SQLException {
        if (action.equals(DialogAction.ADD_TEACHER)){
            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID FROM Teacher WHERE NAME = ? ");
            DatabaseConnect.preparedStatement.setString(1, subjectSchoolField.getText());
            DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();
            if (DatabaseConnect.res.next()){
                Alerts.errorAlert("Duplicate value", "Value already exists");
            }
            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("INSERT INTO TEACHER ( SCHOOL_ID , NAME , SUBJECT , DATE , TIME   ) VALUES ( ? , ? , ? , ? , ? ) ");
            DatabaseConnect.preparedStatement.setInt(1, userData.getUserId());
            DatabaseConnect.preparedStatement.setString(2, ((TeacherData)actionClass).getName());
            DatabaseConnect.preparedStatement.setInt(3, subjectSchoolHolder.getKey(subjectSelect.getValue()));
            DatabaseConnect.preparedStatement.setString(5, LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
            DatabaseConnect.preparedStatement.setString(4, new SimpleDateFormat("yyyy/mm/dd").format(new Date()));

            if(DatabaseConnect.preparedStatement.executeUpdate() > 0){
                System.out.println("Done");
            }
        }else{
            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("UPDATE TEACHER SET SCHOOL_ID = ? , NAME = ? , SUBJECT = ? , DATE = ? , TIME = ?  WHERE ID = ? ");
            DatabaseConnect.preparedStatement.setInt(1, userData.getUserId());
            DatabaseConnect.preparedStatement.setString(2, ((TeacherData)actionClass).getName());
            DatabaseConnect.preparedStatement.setInt(3, subjectSchoolHolder.getKey(subjectSelect.getValue()));
            DatabaseConnect.preparedStatement.setString(5, LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
            DatabaseConnect.preparedStatement.setString(4, new SimpleDateFormat("yyyy/mm/dd").format(new Date()));
            DatabaseConnect.preparedStatement.setInt(6, ((TeacherData)actionClass).getId());


            if(DatabaseConnect.preparedStatement.executeUpdate() > 0){
                System.out.println("Done");
            }
        }

    }

    private void updateClass(DialogAction action) throws SQLException {



        if (action.equals(DialogAction.ADD_CLASS)){
            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID FROM CLASSES WHERE SCHOOL_ID = ? ");
            DatabaseConnect.preparedStatement.setInt(1, userData.getUserId());
            DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();
            if (DatabaseConnect.res.next()){
                Alerts.errorAlert("Duplicate value", "Value already exists");
            }
            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("INSERT INTO CLASSES ( SCHOOL_ID , NAME , TUITION , DATE , TIME  ) VALUES ( ? , ? , ? , ? , ? ) ");
            DatabaseConnect.preparedStatement.setInt(1, userData.getUserId());
            DatabaseConnect.preparedStatement.setString(2, ((ClassData)actionClass).getName());
            DatabaseConnect.preparedStatement.setInt(3, ((ClassData)actionClass).getTuition());
            DatabaseConnect.preparedStatement.setString(5, LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
            DatabaseConnect.preparedStatement.setString(4, new SimpleDateFormat("yyyy/mm/dd").format(new Date()));

            if(DatabaseConnect.preparedStatement.executeUpdate() > 0){
                System.out.println("Done");
            }
        }else{
            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("UPDATE CLASSES SET SCHOOL_ID = ? , NAME = ? , TUITION = ? , DATE = ? , TIME = ? WHERE ID = ? ");
            DatabaseConnect.preparedStatement.setInt(1, userData.getUserId());
            DatabaseConnect.preparedStatement.setString(2, ((ClassData)actionClass).getName());
            DatabaseConnect.preparedStatement.setInt(3, ((ClassData)actionClass).getTuition());
            DatabaseConnect.preparedStatement.setString(5, LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
            DatabaseConnect.preparedStatement.setString(4, new SimpleDateFormat("yyyy/mm/dd").format(new Date()));
            DatabaseConnect.preparedStatement.setInt(6, ((ClassData)actionClass).getId());


            if(DatabaseConnect.preparedStatement.executeUpdate() > 0){
                System.out.println("Done");
            }
        }

    }

    private boolean authorizeLogin(){
        return false;

    }


}
