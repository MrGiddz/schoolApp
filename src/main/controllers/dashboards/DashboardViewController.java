package main.controllers.dashboards;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import main.controllers.dialogs.ShowDialog;
import main.controllers.dialogs.events.DialogAction;
import main.resources.models.*;
import main.resources.utilities.LoadData;
import main.resources.utilities.collections.ReversibleWeakHashMap;
import main.resources.utilities.connection.DatabaseConnect;
import main.resources.utilities.handles.DashboardHandle;
import main.resources.utilities.notification.Alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardViewController extends DashboardHandle implements Initializable {

    private static LoginAction action;
    private MenuItem editSchool;
    private MenuItem editTeacher;
    private MenuItem editClass = new MenuItem();
    private DialogAction dialogAction;
    private TableData tableData;
    private ReversibleWeakHashMap<Integer, String> subjectSchoolHolder;
    private ReversibleWeakHashMap<Integer, String> classHolder;
    private ReversibleWeakHashMap<Integer, String> teacherHolder;

    private void setInfo(){

        try {
            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT COUNT(ID) COUNT FROM SCHOOL");
            DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();
            if (DatabaseConnect.res.next()){
                schoolsCount.setText(String.valueOf(DatabaseConnect.res.getInt("COUNT")));
            }

            DatabaseConnect.close();

            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT COUNT(ID) AS COUNT FROM STUDENTS");
            DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();
            if (DatabaseConnect.res.next()){
                studentsCount.setText(String.valueOf(DatabaseConnect.res.getInt("COUNT")));
            }
            DatabaseConnect.close();

            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT COUNT(ID) AS COUNT FROM USERS");
            DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();
            if (DatabaseConnect.res.next()){
                adminCount.setText(String.valueOf(DatabaseConnect.res.getInt("COUNT")));
            }
            DatabaseConnect.close();

            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT id, user_name, email  FROM USERS WHERE USER_NAME= ?");
            DatabaseConnect.preparedStatement.setString(1, userData.getUserName());
            DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();
            while (DatabaseConnect.res.next()){

                user.put(DatabaseConnect.res.getInt("id"), new UserData(DatabaseConnect.res.getInt("id"), DatabaseConnect.res.getString("user_name"), DatabaseConnect.res.getString("email")));
               id.textProperty().bind(user.get(DatabaseConnect.res.getInt("id")).idProperty().asString());
                currentUsername.textProperty().bind(user.get(DatabaseConnect.res.getInt("id")).usernameProperty());
                currentEmail.textProperty().bind(user.get(DatabaseConnect.res.getInt("id")).emailProperty());
            }
            DatabaseConnect.close();
        }catch (Exception e){
            Alerts.errorAlert("A Technical error has occurred...", "Please check your network main.resources.utilities.connection or contact administrator");
            e.printStackTrace();
        }

        schools.clear();
    }

    static void setStatus(LoginAction actions){
        action = actions;
    }

    public static LoginAction getStatus(){ return action;}

    private void setCards(){
        if (action == LoginAction.SCHOOL) {
            topCardList.getChildren().remove(schoolsCard);
            topCardList.getChildren().remove(usersCard);
            buttomMiddleLabel.setText("SUBJECTS");
        }
        try {
            setLists();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setLists() throws SQLException {
        subSchoolList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        classList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        teacherList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //SchoolSub populating

        if (action.equals(LoginAction.SCHOOL)){

            subjectSchoolHolder = new ReversibleWeakHashMap<>();

            for (Map.Entry<Integer, SubjectData> s: LoadData.getSubjectBasicInfo().entrySet()){
                subjectSchoolHolder.put(s.getKey(), s.getValue().getSubjectName());
                System.out.println(s.getValue().getSubjectName().isEmpty() ? "null" : s.getValue().getSubjectName() + " Key = " + s.getKey());
                subSchoolList.getItems().add(s.getValue().getSubjectName());
            }
            ////end of subSchoolList

            //ClassList populating
            classHolder = new ReversibleWeakHashMap<>();

            for (Map.Entry<Integer, ClassData> s: LoadData.getClassBasicInfo().entrySet()){
                classHolder.put(s.getKey(), s.getValue().getName());
                classList.getItems().add(s.getValue().getName());
            }
            //end of classList

            //teachers list populating
            teacherHolder = new ReversibleWeakHashMap<>();

            for (Map.Entry<Integer, TeacherData> s: LoadData.getTeacherBasicInfo().entrySet()){
                teacherHolder.put(s.getKey(), s.getValue().getName());
                teacherList.getItems().add(s.getValue().getName());
            }
            //end of teacher list populating
            
        }else if (action.equals(LoginAction.ADMIN)) {

            subjectSchoolHolder = new ReversibleWeakHashMap<>();System.out.println("04");
            for (Map.Entry<Integer, TableData> s: LoadData.getSchoolBasicInfo().entrySet()){
                subjectSchoolHolder.put(s.getKey(), s.getValue().getSchoolName().get());
                subSchoolList.getItems().add(s.getValue().getSchoolName().get());
            }
            /////////////////////////

            //ClassList populating

            classHolder = new ReversibleWeakHashMap<>();
            for (Map.Entry<Integer, ClassData> s: LoadData.getClassFullInfo().entrySet()){
                classHolder.put(s.getKey(), s.getValue().getName());
                classList.getItems().add(s.getValue().getName());
            }

            //end of classList

            //teachers list populating
            teacherHolder = new ReversibleWeakHashMap<>();
            for (Map.Entry<Integer, TeacherData> s: LoadData.getTeacherFullInfo().entrySet()){
                teacherHolder.put(s.getKey(), s.getValue().getName());
                teacherList.getItems().add(s.getValue().getName());
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInfo();
        setCards();
        initPopUp();
    }

    public void initPopUp(){
        ContextMenu menu = new ContextMenu();
        ContextMenu userMenu = new ContextMenu();
        ContextMenu classMenu = new ContextMenu();
        ContextMenu teacherMenu = new ContextMenu();
        menu.setPrefSize(150, 50);
        userMenu.setPrefSize(150, 50);
        classMenu.setPrefSize(150, 50);
        teacherMenu.setPrefSize(150, 50);
        MenuItem addSchool = new MenuItem();
        editSchool = new MenuItem();
        MenuItem addteacher = new MenuItem();
        editTeacher = new MenuItem();
        MenuItem addUser = new MenuItem();
        MenuItem editUser = new MenuItem();
        MenuItem addClass = new MenuItem();


        addSchool.setText("Add");
        editSchool.setText("Edit");
        editSchool.setDisable(true);
        addteacher.setText("Add");
        editTeacher.setText("Edit");
        editTeacher.setDisable(true);
        addUser.setText("Add");
        editUser.setText("Edit");
        editUser.setDisable(true);
        addClass.setText("Add");
        editClass.setText("Edit");
        editClass.setDisable(true);


        if (action.equals(LoginAction.SCHOOL)){

            addSchool.setOnAction(event -> {
                dialogAction = DialogAction.ADD_SUBJECT;
                title = "Add new School";
                SubjectData subjectData = new SubjectData();
                try {
                        new ShowDialog(dialogAction, subjectData);

                    for (Map.Entry<Integer, SubjectData> s: LoadData.getSubjectBasicInfo().entrySet()){

                        if (!(subjectSchoolHolder.containsKey(s.getKey()))) {
                            subjectSchoolHolder.put(s.getKey(), s.getValue().getSubjectName());
                                subSchoolList.getItems().add(s.getValue().getSubjectName());
                        }
                    }

                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            });


            editSchool.setOnAction(event -> {
                dialogAction = DialogAction.EDIT_SUBJECT;
                title = "Edit School";
                TableData tableData;
                try {
                        tableData = LoadData.getSchoolFullInfo().get(subjectSchoolHolder.getKey(subSchoolList.getSelectionModel().getSelectedItem()));
                        new ShowDialog(dialogAction, tableData);


                    for (Map.Entry<Integer, SubjectData> s: LoadData.getSubjectFullInfo().entrySet()){
                        if (!subjectSchoolHolder.containsValue(s.getValue())) {
                            subSchoolList.getItems().add(s.getValue().getSubjectName());
                        }
                    }

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }

            });

            addteacher.setOnAction(event1 -> {
                dialogAction = DialogAction.ADD_TEACHER;
                title = "Add Teacher";
                TeacherData teacherData = new TeacherData();
                try {
                        new ShowDialog(dialogAction, teacherData);
                    for (Map.Entry<Integer, TeacherData> s: LoadData.getTeacherFullInfo().entrySet()){

                        if (!(teacherHolder.containsKey(s.getKey()))) {
                            teacherHolder.put(s.getKey(), s.getValue().getName());
                            teacherList.getItems().add(s.getValue().getName());
                        }
                    }
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            });


            editTeacher.setOnAction(event1 ->{
                dialogAction = DialogAction.EDIT_TEACHER;
                title = "Edit Teacher";
                TeacherData teacherData;
                try {
                    teacherData = LoadData.getTeacherBasicInfo().get(teacherHolder.getKey(teacherList.getSelectionModel().getSelectedItem()));
                    new ShowDialog(dialogAction, teacherData);
                    for (Map.Entry<Integer, TeacherData> s: LoadData.getTeacherFullInfo().entrySet()){
                        if (!(teacherHolder.containsValue(s.getValue()))) {
                            teacherList.getItems().add(s.getValue().getName());
                        }
                    }
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            });

            addUser.setOnAction(event1 -> System.out.println("Menu Item 5 Pressed"));
            editUser.setOnAction(event1 -> System.out.println("Menu Item 6 Pressed"));

            addClass.setOnAction(event1 ->{
                dialogAction = DialogAction.ADD_CLASS;
                title = "Add Class";
                ClassData classData = new ClassData();
                try {
                    new ShowDialog(dialogAction, classData);
                    for (Map.Entry<Integer, ClassData> s: LoadData.getClassBasicInfo().entrySet()){
                        if (!(classHolder.containsKey(s.getKey()))) {
                            classHolder.put(s.getKey(), s.getValue().getName());
                            classList.getItems().add(s.getValue().getName());
                        }
                    }
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            });

            editClass.setOnAction(event1 ->{
                dialogAction = DialogAction.EDIT_CLASS;
                title = "Edit Class";
                ClassData classData;
                try {
                    classData = LoadData.getClassBasicInfo().get(classHolder.getKey(classList.getSelectionModel().getSelectedItem()));

                    new ShowDialog(dialogAction, classData);
                /*    for (Map.Entry<Integer, ClassData> s: LoadData.getClassBasicInfo().entrySet()){
                        if ((classHolder.containsKey(s.getKey()))) {

                            classList.getItems().remove(classHolder.get(s.getKey()));
                            classHolder.remove(s.getKey());

                            classHolder.put(s.getKey(), s.getValue().getName());
                            classList.getItems().add(s.getValue().getName());

                        }
                    }*/
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            });

        }else if (action.equals(LoginAction.ADMIN)){

            addSchool.setOnAction(event -> {
                dialogAction = DialogAction.ADD_SCHOOL;
                title = "Add new School";
                try {
                    tableData = new TableData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tableData.setPassword("");
                try {
                    new ShowDialog(dialogAction, tableData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


            editSchool.setOnAction(event -> {
                dialogAction = DialogAction.EDIT_SCHOOL;
                title = "Edit School";
                TableData tableData;
                try {

                    tableData = LoadData.getSchoolFullInfo().get(subjectSchoolHolder.getKey(subSchoolList.getSelectionModel().getSelectedItem()));
                    new ShowDialog(dialogAction, tableData);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }

            });

            addteacher.setOnAction(event1 -> System.out.println("Add teacher Menu Item 3 Pressed"));
            editTeacher.setOnAction(event1 -> System.out.println("Menu Item 4 Pressed"));
            addUser.setOnAction(event1 -> System.out.println("Menu Item 5 Pressed"));
            editUser.setOnAction(event1 -> System.out.println("Menu Item 6 Pressed"));
            addClass.setOnAction(event1 -> System.out.println("Menu Item 7 Pressed"));
            editClass.setOnAction(event1 -> System.out.println("Menu Item 8 Pressed"));
        }


        menu.getItems().addAll(addSchool,editSchool);
        teacherMenu.getItems().addAll(addteacher,editTeacher);
        userMenu.getItems().addAll(addUser, editUser);
        classMenu.getItems().addAll(addClass,editClass);

        classEdit.setContextMenu(classMenu);
        teacherEdit.setContextMenu(teacherMenu);
        userEdit.setContextMenu(userMenu);
        subSchoolEdit.setContextMenu(menu);
    }

    @FXML
    private void loadInfo(MouseEvent event) {


        if (event.getSource() == subSchoolList){
            editSchool.setDisable(false);


        }else if (event.getSource() == classList){
            editClass.setDisable(false);
        }else if (event.getSource() == teacherList){
            editTeacher.setDisable(false);
        }
    }

}
