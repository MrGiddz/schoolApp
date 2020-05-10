package main.resources.models;

import javafx.beans.property.*;

public class ClassData extends DashboardViewElements {
    private IntegerProperty id, tuition, schoolId;
    private StringProperty	name;


    public ClassData(int id, int schoolId, String name, int tuition) {
        this.id = new SimpleIntegerProperty(id);
        this.schoolId = new SimpleIntegerProperty(schoolId);
        this.name = new SimpleStringProperty(name);
        this.tuition = new SimpleIntegerProperty(tuition);
    }

    public ClassData() {
        this(0, 0,  "", 0);
    }


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getTuition() {
        return tuition.get();
    }

    public IntegerProperty tuitionProperty() {
        return tuition;
    }

    public void setTuition(int tuition) {
        this.tuition.set(tuition);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
