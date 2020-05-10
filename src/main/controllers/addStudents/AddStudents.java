package main.controllers.addStudents;

import com.jfoenix.controls.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import main.controllers.dialogs.events.DialogAction;
import main.resources.models.ProgressWorker;
import main.resources.models.TableData;
import main.resources.utilities.LoadData;
import main.resources.utilities.collections.ReversibleWeakHashMap;
import main.resources.utilities.connection.DatabaseConnect;
import main.resources.utilities.handles.AddStudentsHandle;
import main.resources.utilities.notification.Alerts;
import org.controlsfx.control.ListSelectionView;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddStudents extends AddStudentsHandle implements Initializable{


    private ReversibleWeakHashMap<String, Object> objectReversibleWeakHashMap = new ReversibleWeakHashMap<>();
    private int targetSize = 0, expectedSize = 21;
    private DoubleProperty currentSize;


    @Override
    public void setSelectSchool() {
        try {

            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT id, name FROM school");

            DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();
            schoolCombo = new ReversibleWeakHashMap<>();

            while (DatabaseConnect.res.next()){
                schoolCombo.put(DatabaseConnect.res.getInt("id"), DatabaseConnect.res.getString("name"));
            }

            DatabaseConnect.close();

            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT id, name FROM CLASSES");
            DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();
            classCombo = new ReversibleWeakHashMap<>();

            while (DatabaseConnect.res.next()){
                classCombo.put(DatabaseConnect.res.getInt("id"), DatabaseConnect.res.getString("name"));
            }

            DatabaseConnect.close();

            for (Map.Entry s: schoolCombo.entrySet()){
                selectSchool.getItems().add((String) s.getValue());
            }

            for (Map.Entry s : classCombo.entrySet()){
                selectClass.getItems().add((String) s.getValue());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private ReversibleWeakHashMap<Integer, String> list;
    @Override
    protected void setSubjectSelection() {
        try {

            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID, SUBJECT_NAME FROM SUBJECT");

            DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();
            list = new ReversibleWeakHashMap<>();

            while (DatabaseConnect.res.next()){
                list.put(DatabaseConnect.res.getInt("ID"), DatabaseConnect.res.getString("SUBJECT_NAME"));
            }
            DatabaseConnect.close();

            for (Map.Entry s: list.entrySet()){
                subjectSelection.getSourceItems().add((String) s.getValue());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setStudent(TableData school, DialogPane pane, DialogAction addStudent) {

            firstname.textProperty().bindBidirectional(school.firstNameProperty());
            middlename.textProperty().bindBidirectional(school.middlenameProperty());
            lastname.textProperty().bindBidirectional(school.surnameProperty());
            gender.valueProperty().bindBidirectional(school.genderProperty());
            dateOfBirth.valueProperty().bindBidirectional(school.date_of_birthProperty());
            nationality.textProperty().bindBidirectional(school.nationalityProperty());
            disability.textProperty().bindBidirectional(school.disabledProperty());
            selectSchool.valueProperty().bindBidirectional(school.schoolProperty());
            nameOfParent.textProperty().bindBidirectional(school.parent_nameProperty());
            parentPhNo.textProperty().bindBidirectional(school.phone_numberProperty());
            parentEmail.textProperty().bindBidirectional(school.getStudentEmail());



            Button okButton = (Button) pane.lookupButton(ButtonType.FINISH);
            okButton.addEventFilter(ActionEvent.ACTION, event -> {
                if (!validateFormData()) {
                    if (addStudent.equals(DialogAction.ADD_STUDENT))
                        addStudent();
                    System.out.println("Finished Clicked");
                    event.consume();
                    if (updateStudent()) {
                        Alerts.successAlert("Success", "Action succeeded");
                    }
                }

            });

    }

    private boolean updateStudent() {
        return false;
    }


    private void addStudent(){
        try{
            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("INSERT INTO students(surname, firstname, middlename, date_of_birth, gender, disabled,  disability, " +
                    "school, class, nationality, parent_name, phone_number, email, subject_1, subject_2, subject_3, subject_4, subject_5, subject_6," +
                    " subject_7, subject_8, subject_9, subject_10, subject_11, date_added, time_added)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            DatabaseConnect.preparedStatement.setString(1, lastname.getText());
            DatabaseConnect.preparedStatement.setString(2, firstname.getText());
            DatabaseConnect.preparedStatement.setString(3, middlename.getText());
            DatabaseConnect.preparedStatement.setString(4, (dateOfBirth.getValue()).toString());
            DatabaseConnect.preparedStatement.setString(5, gender.getValue());
            if (disabled.isSelected()){
                DatabaseConnect.preparedStatement.setString(6, "Yes");
                DatabaseConnect.preparedStatement.setString(7, disability.getText());
            }else{
                DatabaseConnect.preparedStatement.setString(6, "No");
                DatabaseConnect.preparedStatement.setString(7, "nill");
            }
            DatabaseConnect.preparedStatement.setInt(8, schoolCombo.getKey(selectSchool.getValue()));
            DatabaseConnect.preparedStatement.setInt(9, classCombo.getKey(selectClass.getValue()));
            DatabaseConnect.preparedStatement.setString(10, nationality.getText());
            DatabaseConnect.preparedStatement.setString(11, nameOfParent.getText());
            DatabaseConnect.preparedStatement.setInt(12, Integer.parseInt(parentPhNo.getText()));
            DatabaseConnect.preparedStatement.setString(13, parentEmail.getText());
            for (int s = 0, i = 14; s < subjectSelection.getTargetItems().size(); s++, i++){
                DatabaseConnect.preparedStatement.setInt(i, list.getKey(subjectSelection.getTargetItems().get(s)));
            }
            DatabaseConnect.preparedStatement.setInt(24, 3);

            DatabaseConnect.preparedStatement.setString(25, new SimpleDateFormat("yyyy/mm/dd").format(new Date()));
            DatabaseConnect.preparedStatement.setString(26, LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
            int i = DatabaseConnect.preparedStatement.executeUpdate();
            DatabaseConnect.close();

            if (i>0){
                Alerts.successAlert("Student Added successfully", firstname.getText().toUpperCase().concat(" " + middlename.getText()).concat(" " + lastname.getText()) .toUpperCase()+ " added successfully");
                clearEntries();
            }

        }catch (Exception e){
            Alerts.errorAlert("Error", "An error has occurred while adding student data");
            e.printStackTrace();
        }

    }

    private void clearEntries(){
        firstname.setText("");
        middlename.setText("");
        lastname.setText("");
        dateOfBirth.getEditor().clear();
        dateOfBirth.setValue(null);
        personalInfo.getChildren().remove(gender);
        HBox.setMargin(gender, new Insets(10,0,0,0));
        personalInfo.getChildren().add(gender);
        gender.getItems().addAll("Male", "Female");
        gender.setPromptText("SELECT GENDER");
        nationality.setText("");
        disabled.setSelected(false);
        disability.setText("");
        schoolData.getChildren().remove(selectSchool);
        schoolData.getChildren().add(selectSchool);
        setSelectSchool();
        selectSchool.setPromptText("SELECT  SCHOOL");
        nameOfParent.setText("");
        parentPhNo.setText("");
        parentEmail.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gender.getItems().addAll("Male", "Female");
        setSelectSchool();
        setSubjectSelection();
        setProgressBar();

        subjectSelection.getTargetItems().addListener((ListChangeListener<String>) c -> {
            c.next();
            if (c.wasAdded()){

                    for (int i = 0; i < c.getAddedSubList().size(); i++) {
                        if (c.getList().size() > 10)
                            c.getList().remove(c.getAddedSubList().get(i));

                            targetSize = c.getList().size();
                            System.out.println("Target size = " + targetSize + "\nList Size = " + c.getList().size());
                            setProgressBar();
                    }

            }else if (c.wasRemoved()){

                for (int i = 0; i < c.getRemoved().size(); i++) {
                    targetSize = c.getList().size();
                    System.out.println(targetSize);
                    setProgressBar();
                }
            }
        });

        int initial = subjectSelection.getSourceItems().size();
        subjectSelection.getSourceItems().addListener((ListChangeListener<String>) c -> {
            c.next();

            if (c.wasRemoved()){
                for (int i = 0; i < c.getRemoved().size(); i++) {

                    if (c.getList().size() < initial - 10 ) {
                        subjectSelection.getSourceItems().add(c.getRemoved().get(i));
                        System.out.println(c.getRemoved().get(i).concat("\t = Value of removed at " + i));
                    }
                    System.out.println(targetSize);
                    setProgressBar();
                }
            }
        });
        progressBar.progressProperty().bind(ProgressWorker.progressCounterProperty());

        ProgressWorker.setProgressString();
        progressIndiator.textProperty().bind(ProgressWorker.progressStringProperty());
    }




    private boolean validateFormData() {
    return false;
    }


    private void setProgressBar(){
        currentSize = new SimpleDoubleProperty(objectReversibleWeakHashMap.size() + targetSize);
        ProgressWorker.setProgressCounter( currentSize.doubleValue() / expectedSize);
        ProgressWorker.setProgressString();
        System.gc();
    }

    public void progressWorker(KeyEvent keyEvent) {

        if (keyEvent.getSource() == firstname){
            if (!objectReversibleWeakHashMap.containsKey(((JFXTextField)keyEvent.getSource()).getId())) {
                if (!((JFXTextField)keyEvent.getSource()).getText().isEmpty())
                    objectReversibleWeakHashMap.put(((JFXTextField)keyEvent.getSource()).getId(), keyEvent.getSource());
                setProgressBar();
            }else {
                if (((JFXTextField)keyEvent.getSource()).getText().isEmpty()){
                    objectReversibleWeakHashMap.remove(((JFXTextField)keyEvent.getSource()).getId());
                    setProgressBar();
                }

            }
        }

        if (keyEvent.getSource() == middlename){
            if (!objectReversibleWeakHashMap.containsKey(((JFXTextField)keyEvent.getSource()).getId())) {
                if (!((JFXTextField)keyEvent.getSource()).getText().isEmpty())
                    objectReversibleWeakHashMap.put(((JFXTextField)keyEvent.getSource()).getId(), keyEvent.getSource());
                setProgressBar();
            }else {
                if (((JFXTextField)keyEvent.getSource()).getText().isEmpty()){
                    objectReversibleWeakHashMap.remove(((JFXTextField)keyEvent.getSource()).getId());
                    setProgressBar();
                }

            }
        }

        if (keyEvent.getSource() == lastname){
            if (!objectReversibleWeakHashMap.containsKey(((JFXTextField)keyEvent.getSource()).getId())) {
                if (!((JFXTextField)keyEvent.getSource()).getText().isEmpty())
                    objectReversibleWeakHashMap.put(((JFXTextField)keyEvent.getSource()).getId(), keyEvent.getSource());
                setProgressBar();
            }else {
                if (((JFXTextField)keyEvent.getSource()).getText().isEmpty()){
                    objectReversibleWeakHashMap.remove(((JFXTextField)keyEvent.getSource()).getId());
                    setProgressBar();
                }

            }
        }

        if (keyEvent.getSource() == nationality){
            if (!objectReversibleWeakHashMap.containsKey(((JFXTextArea)keyEvent.getSource()).getId())) {
                if (!((JFXTextArea)keyEvent.getSource()).getText().isEmpty())
                    objectReversibleWeakHashMap.put(((JFXTextArea)keyEvent.getSource()).getId(), keyEvent.getSource());
                setProgressBar();
            }else {
                if (((JFXTextArea)keyEvent.getSource()).getText().isEmpty()){
                    objectReversibleWeakHashMap.remove(((JFXTextArea)keyEvent.getSource()).getId());
                    setProgressBar();
                }

            }
        }

        if (keyEvent.getSource() == nameOfParent){
            if (!objectReversibleWeakHashMap.containsKey(((JFXTextField)keyEvent.getSource()).getId())) {
                if (!((JFXTextField)keyEvent.getSource()).getText().isEmpty())
                    objectReversibleWeakHashMap.put(((JFXTextField)keyEvent.getSource()).getId(), keyEvent.getSource());
                setProgressBar();
            }else {
                if (((JFXTextField)keyEvent.getSource()).getText().isEmpty()){
                    objectReversibleWeakHashMap.remove(((JFXTextField)keyEvent.getSource()).getId());
                    setProgressBar();
                }

            }
        }

        if (keyEvent.getSource() == parentPhNo){
            if (!objectReversibleWeakHashMap.containsKey(((JFXTextField)keyEvent.getSource()).getId())) {
                if (!((JFXTextField)keyEvent.getSource()).getText().isEmpty())
                    objectReversibleWeakHashMap.put(((JFXTextField)keyEvent.getSource()).getId(), keyEvent.getSource());
                setProgressBar();
            }else {
                if (((JFXTextField)keyEvent.getSource()).getText().isEmpty()){
                    objectReversibleWeakHashMap.remove(((JFXTextField)keyEvent.getSource()).getId());
                    setProgressBar();
                }

            }
        }

        if (keyEvent.getSource() == parentEmail){
            if (!objectReversibleWeakHashMap.containsKey(((JFXTextField)keyEvent.getSource()).getId())) {
                if (!((JFXTextField)keyEvent.getSource()).getText().isEmpty())
                    objectReversibleWeakHashMap.put(((JFXTextField)keyEvent.getSource()).getId(), keyEvent.getSource());
                setProgressBar();
            }else {
                if (((JFXTextField)keyEvent.getSource()).getText().isEmpty()){
                    objectReversibleWeakHashMap.remove(((JFXTextField)keyEvent.getSource()).getId());
                    setProgressBar();
                }

            }
        }

        if (keyEvent.getSource() == disability){
            if (!objectReversibleWeakHashMap.containsKey(((JFXTextArea)keyEvent.getSource()).getId())) {
                if (!((JFXTextArea)keyEvent.getSource()).getText().isEmpty())
                    objectReversibleWeakHashMap.put(((JFXTextArea)keyEvent.getSource()).getId(), keyEvent.getSource());
                setProgressBar();
            }else {
                if (((JFXTextArea)keyEvent.getSource()).getText().isEmpty()){
                    objectReversibleWeakHashMap.remove(((JFXTextArea)keyEvent.getSource()).getId());
                    setProgressBar();
                }

            }
        }



    }

    public void progressWorkers(ActionEvent event) {

        if (event.getSource() == dateOfBirth){

            if (!objectReversibleWeakHashMap.containsKey(((JFXDatePicker)event.getSource()).getId())) {
                if (!((JFXDatePicker)event.getSource()).getEditor().getText().isEmpty())
                    objectReversibleWeakHashMap.put(((JFXDatePicker)event.getSource()).getId(), event.getSource());
                setProgressBar();
            }else {
                if (((JFXDatePicker)event.getSource()).getEditor().getText().isEmpty()){
                    objectReversibleWeakHashMap.remove(((JFXDatePicker)event.getSource()).getId());
                    setProgressBar();
                }

            }
        }

        if (event.getSource() == disabled){

            if (((JFXCheckBox)event.getSource()).isSelected()) {
                expectedSize = 22;
            } else {
                expectedSize = 21;
            }

            setProgressBar();

        }

        if (event.getSource() == gender){

            if (!objectReversibleWeakHashMap.containsKey(((JFXComboBox) event.getSource()).getId())) {
                if (!(((JFXComboBox) event.getSource()).getSelectionModel().getSelectedItem().toString().isEmpty())) {
                    System.out.println(event.getSource());
                    objectReversibleWeakHashMap.put(((JFXComboBox) event.getSource()).getId(), event.getSource());
                    setProgressBar();
                }
            }
            else if ((((JFXComboBox) event.getSource()).getSelectionModel().getSelectedItem().toString().isEmpty())){
                objectReversibleWeakHashMap.remove(((JFXComboBox) event.getSource()).getId());
                setProgressBar();
            }
        }

        if (event.getSource() == selectSchool){

            if (!objectReversibleWeakHashMap.containsKey(((JFXComboBox) event.getSource()).getId())) {
                if (!(((JFXComboBox) event.getSource()).getSelectionModel().getSelectedItem().toString().isEmpty())) {
                    System.out.println(event.getSource());
                    objectReversibleWeakHashMap.put(((JFXComboBox) event.getSource()).getId(), event.getSource());
                    setProgressBar();
                }
            }
            else if ((((JFXComboBox) event.getSource()).getSelectionModel().getSelectedItem().toString().isEmpty())){
                objectReversibleWeakHashMap.remove(((JFXComboBox) event.getSource()).getId());
                setProgressBar();
            }
        }

        if (event.getSource() == selectClass){
            if (!objectReversibleWeakHashMap.containsKey(((JFXComboBox) event.getSource()).getId())) {
                if (!(((JFXComboBox) event.getSource()).getSelectionModel().getSelectedItem().toString().isEmpty())) {
                    System.out.println(event.getSource());
                    objectReversibleWeakHashMap.put(((JFXComboBox) event.getSource()).getId(), event.getSource());
                    setProgressBar();
                }
            }
            else if ((((JFXComboBox) event.getSource()).getSelectionModel().getSelectedItem().toString().isEmpty())){
                objectReversibleWeakHashMap.remove(((JFXComboBox) event.getSource()).getId());
                setProgressBar();
            }
        }

    }

    public void listViewProgress(MouseEvent event) {
        System.out.println(event.getSource());
        if (event.getSource() == subjectSelection){
            if (!objectReversibleWeakHashMap.containsKey(((ListSelectionView)event.getSource()).getId())) {
                if (!((ListSelectionView) event.getSource()).getTargetItems().isEmpty() || ((ListSelectionView) event.getSource()).getTargetItems().size() < 14)
                    objectReversibleWeakHashMap.put(((ListSelectionView) event.getSource()).getId(), event.getSource());
                setProgressBar();
            }else if (((ListSelectionView)event.getSource()).getTargetItems().isEmpty()){
                    objectReversibleWeakHashMap.remove(((ListSelectionView)event.getSource()).getId());
                    setProgressBar();
                }
            }
        }
}