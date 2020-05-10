package main.resources.utilities.handles;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.resources.models.TableData;
import main.resources.models.UserData;
import main.resources.utilities.collections.ReversibleWeakHashMap;


abstract public class DashboardHandle extends MainPageHandle{


    /*
    * Dashboard view items
    * */
    @FXML
    protected HBox topCardList;

    @FXML
    protected VBox schoolsCard;

    @FXML
    protected Label schoolsCount;

    @FXML
    protected VBox studentCard;

    @FXML
    protected Label studentsCount;

    @FXML
    protected VBox teacherCard;

    @FXML
    protected Label teachersCount;

    @FXML
    protected VBox classCard;

    @FXML
    protected Label classesCount;

    @FXML
    protected VBox usersCard;

    @FXML
    protected Label adminCount;

    @FXML
    protected VBox bottomCard00;

    @FXML
    protected Label headerName;

    @FXML
    protected Label id;

    @FXML
    protected Label currentUsername;

    @FXML
    protected Label currentEmail;

    @FXML
    protected JFXButton userEdit;

    @FXML
    protected VBox bottomCard01;

    @FXML
    protected Label buttomMiddleLabel;

    @FXML
    protected ListView<String> subSchoolList;

    @FXML
    protected JFXButton subSchoolEdit;

    @FXML
    protected VBox bottomCard02;

    @FXML
    protected Label buttomRightLabel;

    @FXML
    protected ListView<String> teacherList;

    @FXML
    protected JFXButton teacherEdit;

    @FXML
    protected VBox bottomCard03;

    @FXML
    protected Label buttomLastLabel;

    @FXML
    protected ListView<String> classList;

    @FXML
    protected JFXButton classEdit;
    
    /*
    * Dashboard items
    * */


    @FXML
    protected BorderPane dashboardMain;

    @FXML
    protected VBox dashboardSidebar;

    @FXML
    protected JFXButton home;

    @FXML
    protected JFXButton viewPayments;

    @FXML
    protected JFXButton addSchool;

    @FXML
    protected JFXButton viewSchools;

    @FXML
    protected JFXButton viewStudents;

    @FXML
    protected JFXButton logout;

    @FXML
    protected FontAwesomeIconView minimize;

    @FXML
    protected FontAwesomeIconView close;
  

    protected static ReversibleWeakHashMap<Integer, TableData> schools = new ReversibleWeakHashMap<>();
    protected static ReversibleWeakHashMap<Integer, String> subject = new ReversibleWeakHashMap<>();
    protected static ReversibleWeakHashMap<Integer, String> className = new ReversibleWeakHashMap<>();
    protected static ReversibleWeakHashMap<Integer, String> teacher = new ReversibleWeakHashMap<>();
    protected static ReversibleWeakHashMap<Integer, UserData> user = new ReversibleWeakHashMap<>();


    protected static char[] stack = new char[15];
    
    
    


}
