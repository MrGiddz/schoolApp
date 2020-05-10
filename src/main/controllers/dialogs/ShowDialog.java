package main.controllers.dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import main.controllers.addStudents.AddStudents;
import main.controllers.dialogs.events.DialogAction;
import main.resources.models.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

public class ShowDialog extends TableHolder {

    static TableData data;
    private DialogAction action;
    private FXMLLoader loader = new FXMLLoader();
    private DialogPane pane = null;

    public <T>ShowDialog(DialogAction action, T school) throws IOException {

        EditSchool editSchool;
        AddStudents addStudents;
        this.action = action;


        switch (action){
            case ADD_SCHOOL:
            case EDIT_SCHOOL:
                loadFxml();
                editSchool = loader.getController();
                if (school instanceof TableData)
                editSchool.setSchool((TableData) school, pane, action);
                break;
                
            case ADD_STUDENT:
            case EDIT_STUDENT:
                loadFxml();
                addStudents = loader.getController();
                if (school instanceof TableData)
                addStudents.setStudent((TableData) school, pane, action);
                break;

            case ADD_SUBJECT:
            case EDIT_SUBJECT:
                loadFxml();
                DialogActions<SubjectData> subjectAction;
                subjectAction = loader.getController();
                if (school instanceof SubjectData)
                    subjectAction.setDialogs(action, (SubjectData) school, pane);

                break;
                    

            case ADD_CLASS:
            case EDIT_CLASS:
                loadFxml();
                DialogActions<ClassData> classAction;
                classAction = loader.getController();
                if (school instanceof ClassData)
                    classAction.setDialogs(action, (ClassData) school, pane);

                break;
                
            case ADD_TEACHER:
            case EDIT_TEACHER:
                loadFxml();
                DialogActions<TeacherData> teacherAction;
                teacherAction = loader.getController();
                if (school instanceof TeacherData)
                    teacherAction.setDialogs(action, (TeacherData) school, pane);
            default:
                break;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(pane);
        dialog.setTitle(this.title);

        try {
            data = new TableData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.isPresent())
        if (ButtonType.FINISH == clickedButton.get()){
            System.out.println("Finish button clicked");

        }
    }
    
    private void loadFxml() throws IOException {

        if (action.equals(DialogAction.ADD_SCHOOL) || action.equals(DialogAction.EDIT_SCHOOL)){
            loader.setLocation(getClass().getResource("/main/resources/views/dialogs/EditSchool.fxml"));
            pane = loader.load();
        }else if (action.equals(DialogAction.ADD_STUDENT) || action.equals(DialogAction.EDIT_STUDENT)){
            loader.setLocation(getClass().getResource("/main/resources/views/AddStudents.fxml"));
            pane = loader.load();
        }else if (action.equals(DialogAction.ADD_SUBJECT) || action.equals(DialogAction.EDIT_SUBJECT) || action.equals(DialogAction.ADD_CLASS) ||
                action.equals(DialogAction.EDIT_CLASS) || action.equals(DialogAction.ADD_TEACHER) || action.equals(DialogAction.EDIT_TEACHER)){
            loader.setLocation(getClass().getResource("/main/resources/views/dialogs/DialogActions.fxml"));
            pane = loader.load();
        }

        pane.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
        pane.getStylesheets().add(getClass().getResource("/stylesheets/dialogBox.css").toExternalForm());
    }
}
