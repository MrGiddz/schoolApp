package main.resources.models;

import javafx.beans.property.*;

public class SubjectData extends DashboardViewElements {

    private IntegerProperty id, schoolId;
    private StringProperty subjectName, compulsory;



    public SubjectData(int id, int schoolId,  String subjectName, String compulsory) {
        this.id = new SimpleIntegerProperty(id);
        this.schoolId = new SimpleIntegerProperty(schoolId);
        this.subjectName = new SimpleStringProperty(subjectName);
        this.compulsory = new SimpleStringProperty(compulsory);
    }


    public SubjectData() {
        this(0, 0, "", "");
    }

    public int getSchoolId() {
        return schoolId.get();
    }

    public IntegerProperty schoolIdProperty() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId.set(schoolId);
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

    public String getSubjectName() {
        return subjectName.get();
    }

    public StringProperty subjectNameProperty() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName.set(subjectName);
    }

    public String getCompulsory() {
        return compulsory.get();
    }

    public StringProperty compulsoryProperty() {
        return compulsory;
    }

    public void setCompulsory(String compulsory) {
        this.compulsory.set(compulsory);
    }
}
