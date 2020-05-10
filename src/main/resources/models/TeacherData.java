package main.resources.models;

import javafx.beans.property.*;

public class TeacherData extends DashboardViewElements {

    private IntegerProperty id, subject, schoolId ;
    private StringProperty name;

    public TeacherData(int id, int subject, int schoolId, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.subject = new SimpleIntegerProperty(subject) ;
        this.schoolId = new SimpleIntegerProperty(schoolId);
        this.name = new SimpleStringProperty(name);
    }

    public TeacherData() {
        this(0, 0, 0, "");
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

    public int getSubject() {
        return subject.get();
    }

    public IntegerProperty subjectProperty() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject.set(subject);
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
