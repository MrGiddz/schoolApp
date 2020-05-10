package main.resources.models;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.text.ParseException;

abstract public class TableViews extends TableHolder {


    protected abstract void setTableValues();

    protected abstract void setInfo();

    @FXML
    protected abstract void authorizeLogin(ActionEvent event) throws SQLException;

    @FXML
    protected abstract void deleteRecord(ActionEvent event);

    @FXML
    protected abstract void editInfo(ActionEvent event);

    @FXML
    protected abstract void loadInfo(MouseEvent event);

    @FXML
    protected abstract void handleTableEvent(ActionEvent event) throws ParseException;
}
