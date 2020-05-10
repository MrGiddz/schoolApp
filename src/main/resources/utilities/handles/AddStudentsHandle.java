package main.resources.utilities.handles;


import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.ListSelectionView;
import main.resources.utilities.collections.ReversibleWeakHashMap;
import main.resources.models.TableData;
import main.controllers.dialogs.events.DialogAction;

abstract public class AddStudentsHandle {


    @FXML
    protected JFXTextField firstname;

    @FXML
    protected JFXTextField middlename;

    @FXML
    protected JFXTextField lastname;

    @FXML
    protected HBox personalInfo;

    @FXML
    protected JFXDatePicker dateOfBirth;

    @FXML
    protected JFXComboBox<String> gender;

    @FXML
    protected JFXTextArea nationality;

    @FXML
    protected JFXCheckBox disabled;

    @FXML
    protected JFXTextArea disability;

    @FXML
    protected VBox schoolData;

    @FXML
    protected JFXComboBox<String> selectSchool;

    @FXML
    protected JFXComboBox<String> selectClass;

    @FXML
    protected JFXTextField nameOfParent;

    @FXML
    protected JFXTextField parentPhNo;

    @FXML
    protected JFXTextField parentEmail;

    @FXML
    protected ListSelectionView<String> subjectSelection;

    @FXML
    protected Label schoolFee;

    @FXML
    protected JFXTextField amount;

    @FXML
    protected JFXTextField balance;

    @FXML
    protected ProgressBar progressBar;

    @FXML
    protected Label progressIndiator;

    protected static ReversibleWeakHashMap<Integer, String> schoolCombo;

    protected static ReversibleWeakHashMap<Integer, String> classCombo;

    protected abstract void setSelectSchool();

    protected abstract void setSubjectSelection();

    public abstract void setStudent(TableData school, DialogPane pane, DialogAction addStudent);
}
