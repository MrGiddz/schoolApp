package main.resources.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TableData extends RecursiveTreeObject<TableData> {

    private StringProperty schoolName, address, name_of_owner, name_of_principal, schoolPhone_no, schoolEmail, schoolPassword, canLogin;

    private IntegerProperty schoolId, userId;
    private IntegerProperty studentId;
    private StringProperty userName, userEmail, dateGenerated, timeGenerated;
    private byte[] studentImage, schoolImage;
    private ZoneId defaultZoneId = ZoneId.systemDefault();


    private StringProperty fullname, surname, firstName, middlename, gender, disabled, school, className, nationality, parent_name, phone_number, studentEmail, time_added, date_added, subject_1, subject_2, subject_3, subject_4, subject_5, subject_6, subject_7, subject_8, subject_9, subject_10, subject_11;

    private ObjectProperty<LocalDate> date_of_birth;

    {
        this.subject_1 = new SimpleStringProperty("");
        this.subject_2 = new SimpleStringProperty("");
        this.subject_3 = new SimpleStringProperty("");
        this.subject_4 = new SimpleStringProperty("");
        this.subject_5 = new SimpleStringProperty("");
        this.subject_6 = new SimpleStringProperty("");
        this.subject_7 = new SimpleStringProperty("");
        this.subject_8 = new SimpleStringProperty("");
        this.subject_9 = new SimpleStringProperty("");
        this.subject_10 = new SimpleStringProperty("");
        this.subject_11 = new SimpleStringProperty("");
    }

    public TableData(int studentId, String surname, String firstname, String middlename, String gender, String disabled, String school, String className,
                     String nationality, String parent_name, String phone_number, String studentEmail, String date_of_birth,  String stdTime_added, String stdDate_added) throws ParseException {
        this.studentId = new SimpleIntegerProperty(studentId);
        this.surname = new SimpleStringProperty(surname);
        this.firstName = new SimpleStringProperty(firstname);
        this.middlename = new SimpleStringProperty(middlename);
        this.gender = new SimpleStringProperty(gender);
        this.disabled = new SimpleStringProperty(disabled);
        this.school = new SimpleStringProperty(school);
        this.className = new SimpleStringProperty(className);
        this.nationality = new SimpleStringProperty(nationality);
        this.parent_name = new SimpleStringProperty(parent_name);
        date_of_birth = (date_of_birth.equals("") ? new SimpleDateFormat("yyyy/mm/dd").format(new Date()) : date_of_birth);
        Instant instant = new SimpleDateFormat("yyyy/mm/dd").parse( date_of_birth.replace('-', '/')).toInstant();
        this.date_of_birth = new SimpleObjectProperty<>(instant.atZone(defaultZoneId).toLocalDate());
        this.phone_number = new SimpleStringProperty(phone_number);
        this.studentEmail = new SimpleStringProperty(studentEmail);
        this.time_added = new SimpleStringProperty(stdTime_added);
        this.date_added = new SimpleStringProperty(stdDate_added);
        this.fullname = new SimpleStringProperty(surname + " " + firstname + " " + middlename);
    }


    public TableData(String subject_1, String subject_2, String subject_3, String subject_4, String subject_5, String subject_6, String subject_7, String subject_8, String subject_9, String subject_10, String subject_11) {
        this.subject_1 = new SimpleStringProperty(subject_1);
        this.subject_2 = new SimpleStringProperty(subject_2);;
        this.subject_3 = new SimpleStringProperty(subject_3);;
        this.subject_4 = new SimpleStringProperty(subject_4);;
        this.subject_5 = new SimpleStringProperty(subject_5);;
        this.subject_6 = new SimpleStringProperty(subject_6);;
        this.subject_7 = new SimpleStringProperty(subject_7);;
        this.subject_8 = new SimpleStringProperty(subject_8);;
        this.subject_9 = new SimpleStringProperty(subject_9);;
        this.subject_10 = new SimpleStringProperty(subject_10);;
        this.subject_11 = new SimpleStringProperty(subject_11);;
    }

    public TableData() throws ParseException {
        this(0,"","","", "","","","","","","","","","", "1990/12/12");
    }

    public byte[] getSchoolImage() {
        return schoolImage;
    }

    public void setSchoolImage(byte[] schoolImage) {
        this.schoolImage = schoolImage;
    }

    public byte[] getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(byte[] studentImage) {
        this.studentImage = studentImage;
    }

    public String getDateGenerated() {
        return dateGenerated.get();
    }

    public StringProperty dateGeneratedProperty() {
        return dateGenerated;
    }

    public void setDateGenerated(String dateGenerated) {
        this.dateGenerated.set(dateGenerated);
    }

    public String getTimeGenerated() {
        return timeGenerated.get();
    }

    public StringProperty timeGeneratedProperty() {
        return timeGenerated;
    }

    public void setTimeGenerated(String timeGenerated) {
        this.timeGenerated.set(timeGenerated);
    }

    public TableData(int schoolId, String name) {
        this.schoolId = new SimpleIntegerProperty(schoolId);
        this.schoolName = new SimpleStringProperty(name);
    }

    public void setSchoolName(String schoolName) {
        this.schoolName.set(schoolName);
    }

    public TableData(int schoolId, String schoolName, String address, String name_of_owner, String name_of_principal, String schoolPhone_no, String schoolEmail) {
         this.schoolId = new SimpleIntegerProperty(schoolId);
        this.schoolName = new SimpleStringProperty(schoolName);
        this.address = new SimpleStringProperty(address);
        this.name_of_owner = new SimpleStringProperty(name_of_owner);
        this.name_of_principal = new SimpleStringProperty(name_of_principal);
        this.schoolPhone_no = new SimpleStringProperty(schoolPhone_no);
        this.schoolEmail = new SimpleStringProperty(schoolEmail);
     }

    public TableData(int schoolId, String name, String address, String name_of_owner, String name_of_principal, String schoolPhone_no, String schoolEmail, String schoolPassword) {
        this.schoolId = new SimpleIntegerProperty(schoolId);
        this.schoolName = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.name_of_owner = new SimpleStringProperty(name_of_owner);
        this.name_of_principal = new SimpleStringProperty(name_of_principal);
        this.schoolPhone_no = new SimpleStringProperty(schoolPhone_no);
        this.schoolEmail = new SimpleStringProperty(schoolEmail);
        this.schoolPassword = new SimpleStringProperty(schoolPassword);
    }

    public TableData(int userId, String name, String userEmail) {
        this.userId = new SimpleIntegerProperty(userId);
        this.userName = new SimpleStringProperty(name);
        this.userEmail = new SimpleStringProperty(userEmail);
    }

    public StringProperty schoolNameProperty() {
        return schoolName;
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getDate_of_birth() {
        return new SimpleDateFormat("yyyy/mm/dd").format( Date.from(date_of_birth.get().atStartOfDay(defaultZoneId).toInstant()));
    }

    public ObjectProperty<LocalDate> date_of_birthProperty() {
        if(date_of_birth == null){
            date_of_birth = new SimpleObjectProperty<>();
        }
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth.set(date_of_birth);
    }

    public void setCanLogin(String canLogin){
        this.canLogin = new SimpleStringProperty(canLogin);
    }

    public StringProperty getCanLogin(){
        return canLogin;
    }

    public void setPassword(String password) {
        this.schoolPassword = new SimpleStringProperty(password);
    }

    public StringProperty getSchoolName() {
        return schoolName;
    }

    public StringProperty getAddress() {
        return address;
    }

    public StringProperty getName_of_owner() {
        return name_of_owner;
    }

    public StringProperty getName_of_principal() {
        return name_of_principal;
    }

    public StringProperty getPhone_no() {
        return schoolPhone_no;
    }

    public StringProperty getStudentEmail() {
        return studentEmail;
    }

    public IntegerProperty getSchoolId() { return schoolId; }

    public StringProperty getPassword() { return schoolPassword; }

    //////Student Properties



    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getMiddlename() {
        return middlename.get();
    }

    public StringProperty middlenameProperty() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename.set(middlename);
    }

    public StringProperty fullnameProperty() {
        return fullname;
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public StringProperty disabledProperty() {
        return disabled;
    }

    public String getSchool() {
        return school.get();
    }

    public StringProperty schoolProperty() {
        return school;
    }

    public StringProperty classNameProperty() {
        return className;
    }

    public StringProperty nationalityProperty() {
        return nationality;
    }

    public StringProperty parent_nameProperty() {
        return parent_name;
    }

    public StringProperty phone_numberProperty() {
        return phone_number;
    }

    public String getSchoolEmail() {
        return schoolEmail.get();
    }

    public StringProperty SchoolEmailProperty() {
        return schoolEmail;
    }

    public StringProperty time_addedProperty() {
        return time_added;
    }

    public StringProperty date_addedProperty() {
        return date_added;
    }

    public StringProperty subject_1Property() {
        return subject_1;
    }

    public StringProperty subject_2Property() {
        return subject_2;
    }

    public StringProperty subject_3Property() {
        return subject_3;
    }

    public StringProperty subject_4Property() {
        return subject_4;
    }

    public StringProperty subject_5Property() {
        return subject_5;
    }

    public StringProperty subject_6Property() {
        return subject_6;
    }

    public StringProperty subject_7Property() {
        return subject_7;
    }

    public StringProperty subject_8Property() {
        return subject_8;
    }

    public StringProperty subject_9Property() {
        return subject_9;
    }

    public StringProperty subject_10Property() {
        return subject_10;
    }

    public StringProperty subject_11Property() {
        return subject_11;
    }
}
