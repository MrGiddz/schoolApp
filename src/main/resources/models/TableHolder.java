package main.resources.models;

import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.resources.utilities.collections.ReversibleWeakHashMap;

public class TableHolder extends DashboardViewElements {

    protected TableData tableData = null;

    protected static String title;

    protected JFXTreeTableColumn<TableData, String> id;
    protected JFXTreeTableColumn<TableData, String>  name;
    protected JFXTreeTableColumn<TableData, String>  address;
    protected JFXTreeTableColumn<TableData, String> owner;
    protected JFXTreeTableColumn<TableData, String> principal;
    protected JFXTreeTableColumn<TableData, String> phone;
    protected JFXTreeTableColumn<TableData, String>  gender;
    protected JFXTreeTableColumn<TableData, String> school;
    protected JFXTreeTableColumn<TableData, String> className;
    protected JFXTreeTableColumn<TableData, String> nationality;
    protected JFXTreeTableColumn<TableData, String> parent_name;
    protected JFXTreeTableColumn<TableData, String> email;
    protected JFXTreeTableColumn<TableData, String> time_added;
    protected JFXTreeTableColumn<TableData, String> date_added;
    protected ReversibleWeakHashMap<Integer, TableData> holder;


     /*Table variables
     * 
     */

    @FXML
    protected AnchorPane tableAnchor, tableViewAnchor;

    @FXML
    protected AnchorPane tableAnchorHeader;

    @FXML
    protected ButtonBar searchBar;

    @FXML
    protected JFXTextField searchField;

    @FXML
    protected JFXButton printGeneralReport;

    @FXML
    protected JFXButton add;

    @FXML
    protected ScrollPane tableScrollPane;

    @FXML
    protected JFXTreeTableView<TableData> treeView;

    @FXML
    protected VBox studentInfo;

    @FXML
    protected FontAwesomeIconView red;

    @FXML
    protected FontAwesomeIconView green;

    @FXML
    protected Pane studentInfoPane;

    @FXML
    protected Label studentName;

    @FXML
    protected Label studentPhone;

    @FXML
    protected Label studentEmail;

    @FXML
    protected Label course;

    @FXML
    protected Label degree;

    @FXML
    protected Label initialDegree;

    @FXML
    protected Pane schoolInfoPane;

    @FXML
    protected Label schoolName;

    @FXML
    protected Label phoneNumber;

    @FXML
    protected Label schoolEmailLabel;

    @FXML
    protected Label ownerName;

    @FXML
    protected Label principalName;

    @FXML
    protected Pane studentFeePane;

    @FXML
    protected Label schoolFee;

    @FXML
    protected Label paid;

    @FXML
    protected Label balance;

    @FXML
    protected JFXButton editStudentBtn;

    @FXML
    protected JFXButton deleteStudentBtn;

    @FXML
    protected Pane adminFunctions;

    @FXML
    protected JFXToggleButton loginToggle;

    @FXML
    protected JFXButton printReport;

    @FXML
    protected JFXButton delete;

    @FXML
    protected JFXButton edit;

}
