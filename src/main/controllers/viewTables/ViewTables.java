package main.controllers.viewTables;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import main.resources.models.pdf.FullPageTable;
import main.resources.utilities.LoadData;
import main.resources.utilities.collections.ReversibleWeakHashMap;
import main.resources.utilities.connection.DatabaseConnect;
import main.resources.models.*;
import main.resources.utilities.notification.Alerts;
import main.controllers.dialogs.ShowDialog;
import main.controllers.dialogs.events.DialogAction;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;




public class ViewTables extends TableViews implements Initializable {

    private static ViewTables viewSchool = null;
    private static ViewSelect member;


    @SuppressWarnings("unused")
    public static void setLogin(String login) {
    }

    private ViewSelect getView() {
        return member;
    }

    public static void setView(ViewSelect object) {
        member = object;
    }

    private DialogAction dialogAction;

    @SuppressWarnings("unused")
    public static ViewTables view(){
        if (viewSchool == null){
            viewSchool = new ViewTables();
        }
        return viewSchool;
    }


    private void setStudentTableValues() {

        id = new JFXTreeTableColumn<>("Student ID");
        id.setPrefWidth(150);
        id.setCellValueFactory(param -> param.getValue().getValue().userIdProperty().asString());

        name =  new JFXTreeTableColumn<>("Full Name");
        name.setPrefWidth(150);
        name.setCellValueFactory(param -> param.getValue().getValue().fullnameProperty());

        gender= new JFXTreeTableColumn<>("Gender");
        gender.setPrefWidth(150);
        gender.setCellValueFactory(param -> param.getValue().getValue().genderProperty());

        owner = new JFXTreeTableColumn<>("Date of Birth");
        owner.setPrefWidth(150);
        owner.setCellValueFactory(param -> param.getValue().getValue().date_of_birthProperty().asString());

        principal = new JFXTreeTableColumn<>("Disabled");
        principal.setPrefWidth(150);
        principal.setCellValueFactory(param -> param.getValue().getValue().disabledProperty());

        nationality = new JFXTreeTableColumn<>("Nationality");
        nationality.setPrefWidth(150);
        nationality.setCellValueFactory(param -> param.getValue().getValue().disabledProperty());

        className = new JFXTreeTableColumn<>("Class");
        className.setPrefWidth(150);
        className.setCellValueFactory(param -> param.getValue().getValue().classNameProperty());

        school = new JFXTreeTableColumn<>("School");
        school.setPrefWidth(150);
        school.setCellValueFactory(param -> param.getValue().getValue().schoolProperty());

        parent_name = new JFXTreeTableColumn<>("Parent/Guardian");
        parent_name.setPrefWidth(150);
        parent_name.setCellValueFactory(param -> param.getValue().getValue().parent_nameProperty());

        phone = new JFXTreeTableColumn<>("Phone Number");
        phone.setPrefWidth(150);
        phone.setCellValueFactory(param -> param.getValue().getValue().phone_numberProperty());

        email = new JFXTreeTableColumn<>("Email");
        email.setPrefWidth(150);
        email.setCellValueFactory(param -> param.getValue().getValue().getStudentEmail());

        time_added = new JFXTreeTableColumn<>("Time Added");
        time_added.setPrefWidth(150);
        time_added.setCellValueFactory(param -> param.getValue().getValue().time_addedProperty());

        date_added  = new JFXTreeTableColumn<>("Date Added");
        date_added.setPrefWidth(150);
        date_added.setCellValueFactory(param -> param.getValue().getValue().date_addedProperty());


        setStudentInfo();
    }

    private void setSchoolTableValues() {
        id= new JFXTreeTableColumn<>("School ID");
        id.setPrefWidth(200);
        id.setCellValueFactory(param -> param.getValue().getValue().getSchoolId().asString());

        name= new JFXTreeTableColumn<>("School Name");
        name.setPrefWidth(200);
        name.setCellValueFactory(param -> param.getValue().getValue().getSchoolName());

        address= new JFXTreeTableColumn<>("Address");
        address.setPrefWidth(200);
        address.setCellValueFactory(param -> param.getValue().getValue().getAddress());

        owner= new JFXTreeTableColumn<>("Name of Owner");
        owner.setPrefWidth(200);
        owner.setCellValueFactory(param -> param.getValue().getValue().getName_of_owner());

        principal= new JFXTreeTableColumn<>("Principal Name");
        principal.setPrefWidth(200);
        principal.setCellValueFactory(param -> param.getValue().getValue().getName_of_principal());

        phone= new JFXTreeTableColumn<>("Phone Number");
        phone.setPrefWidth(200);
        phone.setCellValueFactory(param -> param.getValue().getValue().getPhone_no());

        email= new JFXTreeTableColumn<>("Email");
        email.setPrefWidth(200);
        email.setCellValueFactory(param -> param.getValue().getValue().SchoolEmailProperty());

        setSchoolView();
    }

    @Override
    protected void setTableValues() {
        holder = new ReversibleWeakHashMap<>();

        switch (getView()){
            case SCHOOL:
                setSchoolTableValues();
                addSchoolSearch();
                schoolInfoPane.setVisible(true);
                adminFunctions.setVisible(true);
                studentInfoPane.setVisible(false);
                studentFeePane.setVisible(false);
                break;
            case STUDENT:
                setStudentTableValues();
                addStudentSearch();
                schoolInfoPane.setVisible(false);
                adminFunctions.setVisible(false);
                studentInfoPane.setVisible(true);
                studentFeePane.setVisible(true);
                break;

            case PAYMENT:
                setPaymentTable();
                addPayment();
                tableViewAnchor.getChildren().remove(studentInfo);
                tableAnchorHeader.getChildren().remove(add);
                tableAnchor.setPrefHeight(568);
                tableAnchor.setPrefWidth(1002);
                tableAnchorHeader.setPrefWidth(1002);
                tableAnchorHeader.setPrefHeight(67);
                tableScrollPane.setPrefWidth(1002);
                tableScrollPane.setPrefHeight(515);
                treeView.setPrefWidth(1000);
                treeView.setPrefHeight(510);
                tableAnchor.setLayoutX(40);
                tableAnchor.setLayoutY(25);
                printGeneralReport.setLayoutX(795);
                searchBar.setLayoutY(18);
                printGeneralReport.setLayoutY(18);
                tableScrollPane.setLayoutY(68);
                schoolInfoPane.setVisible(false);
                adminFunctions.setVisible(false);
                studentInfoPane.setVisible(false);
                studentFeePane.setVisible(false);
            default:
                break;
        }
        System.gc();
    }

    private void addPayment() {
    }

    private void setPaymentTable() {
        id = new JFXTreeTableColumn<>("Payment ID");
        id.setPrefWidth(200);
        id.setCellValueFactory(param -> param.getValue().getValue().getSchoolId().asString());

        name = new JFXTreeTableColumn<>("Account Name");
        name.setPrefWidth(200);
        name.setCellValueFactory(param -> param.getValue().getValue().getSchoolName());

        address = new JFXTreeTableColumn<>("Account Number");
        address.setPrefWidth(200);
        address.setCellValueFactory(param -> param.getValue().getValue().getAddress());

        owner = new JFXTreeTableColumn<>("Student Name");
        owner.setPrefWidth(200);
        owner.setCellValueFactory(param -> param.getValue().getValue().getName_of_owner());

        principal = new JFXTreeTableColumn<>("Amount Paid");
        principal.setPrefWidth(200);
        principal.setCellValueFactory(param -> param.getValue().getValue().getName_of_principal());

        phone = new JFXTreeTableColumn<>("Initial Debt");
        phone.setPrefWidth(200);
        phone.setCellValueFactory(param -> param.getValue().getValue().getPhone_no());

        email = new JFXTreeTableColumn<>("Remainder");
        email.setPrefWidth(200);
        email.setCellValueFactory(param -> param.getValue().getValue().SchoolEmailProperty());

        time_added = new JFXTreeTableColumn<>("Time");
        time_added.setPrefWidth(200);
        time_added.setCellValueFactory(param -> param.getValue().getValue().SchoolEmailProperty());

        date_added = new JFXTreeTableColumn<>("Date");
        date_added.setPrefWidth(200);
        date_added.setCellValueFactory(param -> param.getValue().getValue().SchoolEmailProperty());

        setPaymentView();
    }

    private void setPaymentView() {
        ObservableList<TableData> lists = FXCollections.observableArrayList();
        try {
            for (Map.Entry<Integer, TableData> map : LoadData.getSchoolFullInfo().entrySet()){
                lists.add(map.getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final TreeItem<TableData> root = new RecursiveTreeItem<>(lists, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(id,name, address, owner, principal, phone, email, time_added, date_added);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        DatabaseConnect.close();
    }

    public void setInfo(){

    }



    private void setStudentInfo() {
        ObservableList<TableData> schools = FXCollections.observableArrayList();
        try {
                for (Map.Entry<Integer, TableData> map : LoadData.getStudentFullInfo().entrySet()){
                    schools.add(map.getValue());
                }

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        final TreeItem<TableData> root = new RecursiveTreeItem<>(schools, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(name, owner, gender, principal, school, className, nationality, parent_name, phone, email, time_added, date_added);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }




    private void setSchoolView(){

        ObservableList<TableData> lists = FXCollections.observableArrayList();
        try {
            for (Map.Entry<Integer, TableData> map : LoadData.getSchoolFullInfo().entrySet()){
                lists.add(map.getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final TreeItem<TableData> root = new RecursiveTreeItem<>(lists, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(id,name, address, owner, principal, phone, email);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        DatabaseConnect.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableValues();

    }

    public void loadInfo(MouseEvent event) {
        switch (getView()){
            case SCHOOL:
                tableData = treeView.getSelectionModel().getSelectedItem().getValue();
                schoolEmailLabel.textProperty().bind(tableData.SchoolEmailProperty());
                schoolName.textProperty().bind(tableData.getSchoolName());
                phoneNumber.textProperty().bind(tableData.getPhone_no());
                principalName.textProperty().bind(tableData.getName_of_principal());
                ownerName.textProperty().bind(tableData.getName_of_owner());
                if (tableData.getCanLogin().get().equals("yes") || tableData.getCanLogin().get().equals("YES")){
                    red.setVisible(false);
                    loginToggle.setSelected(true);
                    green.setVisible(true);
                }else if (tableData.getCanLogin().get().equals("no") || tableData.getCanLogin().get().equals("NO")){
                    red.setVisible(true);
                    green.setVisible(false);
                    loginToggle.setSelected(false);
                }
                break;
            case STUDENT:
                tableData = treeView.getSelectionModel().getSelectedItem().getValue();
                studentName.textProperty().bind(tableData.fullnameProperty());
                studentEmail.textProperty().bind(tableData.getStudentEmail());
                studentPhone.textProperty().bind(tableData.phone_numberProperty());

                break;
            default:
                break;
        }
    }

    @Override
    protected void handleTableEvent(ActionEvent event) throws ParseException {

        if (event.getSource() == edit){
            dialogAction = DialogAction.EDIT_SCHOOL;
            title = "Edit School";
            tableData = treeView.getSelectionModel().getSelectedItem().getValue();

        }else if (event.getSource().equals(add) && getView().equals(ViewSelect.SCHOOL) ){
            dialogAction = DialogAction.ADD_SCHOOL;
            title = "Add new School";
            tableData = new TableData();
            tableData.setPassword("");
        }else if (event.getSource().equals(add) && getView().equals(ViewSelect.STUDENT)){
            dialogAction = DialogAction.ADD_STUDENT;
            title = "Add new Student";
            tableData = new TableData();
        }else if (event.getSource().equals(editStudentBtn) && getView().equals(ViewSelect.STUDENT)){
            dialogAction = DialogAction.EDIT_STUDENT;
            title = "Edit student";
            tableData = treeView.getSelectionModel().getSelectedItem().getValue();
        }else if (event.getSource().equals(printGeneralReport)){
            try {
                if (FullPageTable.manipulatePdf()){
                    FullPageTable.print();
                }
                    ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            if (!event.getSource().equals(printGeneralReport))
            new <TableData>ShowDialog(dialogAction, tableData);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void authorizeLogin(ActionEvent event) {
        try {
        if (loginToggle.isSelected()){
            System.out.println("Is selected");
                    DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("UPDATE SCHOOL SET  can_login = ? WHERE ID = ?");
                    DatabaseConnect.preparedStatement.setString(1, "YES");
                    DatabaseConnect.preparedStatement.setInt(2, tableData.getSchoolId().get());
                    int i = DatabaseConnect.preparedStatement.executeUpdate();

                    if (i > 0){
                        DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT CAN_LOGIN FROM SCHOOL WHERE ID = ? ");
                        DatabaseConnect.preparedStatement.setInt(1, tableData.getSchoolId().get());
                        DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();

                    }
                    DatabaseConnect.close();

        }else if (!loginToggle.isSelected()){
            DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("UPDATE SCHOOL SET  can_login = ? WHERE ID = ?");
            DatabaseConnect.preparedStatement.setString(1, "NO");
            DatabaseConnect.preparedStatement.setInt(2, tableData.getSchoolId().get());
            int i = DatabaseConnect.preparedStatement.executeUpdate();

            System.out.println("not selected");
            if (i > 0){
                DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT CAN_LOGIN FROM SCHOOL WHERE ID = ? ");
                DatabaseConnect.preparedStatement.setInt(1, tableData.getSchoolId().get());
                DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();

            }

            DatabaseConnect.close();
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void deleteRecord(ActionEvent event){
        Optional<ButtonType> isDelete = Alerts.confirmationDialog("Delete?","Are you sure you want to delete this record?");

        if (isDelete.isPresent())
        if (isDelete.get() == ButtonType.OK){
            TableData data = treeView.getSelectionModel().getSelectedItem().getValue();
            int id = data.getSchoolId().get();
            try {
                if (delete(id)){
                    Alerts.successAlert("Success", "Record deleted successfully");
                    setTableValues();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean delete(int id) throws SQLException {
        DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("DELETE FROM SCHOOL WHERE ID = ?");
            DatabaseConnect.preparedStatement.setInt(1, id);

            int i = DatabaseConnect.preparedStatement.executeUpdate();

            if (i > 0){
                return true;
            }
        DatabaseConnect.close();
            return false;
    }

    @FXML
    protected void editInfo(ActionEvent event){

    }

    private void addStudentSearch(){
        searchField.setPromptText("SEARCH BY STUDENT NAME/PARENT NAME/ID ");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> treeView.setPredicate(schoolsDataTreeItem -> schoolsDataTreeItem.getValue().fullnameProperty().getValue().contains(newValue) | schoolsDataTreeItem.getValue().schoolProperty().getValue().contains(newValue) | schoolsDataTreeItem.getValue().parent_nameProperty().getValue().contains(newValue)));
    }

    private void addSchoolSearch(){
        searchField.setPromptText("SEARCH BY SCHOOL/OWNER/PRINCIPAL NAME");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> treeView.setPredicate(schoolsDataTreeItem -> schoolsDataTreeItem.getValue().getSchoolName().getValue().contains(newValue) | schoolsDataTreeItem.getValue().getName_of_owner().getValue().contains(newValue)));
    }

}
