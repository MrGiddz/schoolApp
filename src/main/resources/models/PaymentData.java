package main.resources.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PaymentData {

    IntegerProperty id, amount, studentId, accountNo;
    StringProperty  accountName, date, time;

    public PaymentData(int id, int amount, int accountNo, String accountName, int studentId, String date, String time) {
        this.id = new SimpleIntegerProperty(id);
        this.amount = new SimpleIntegerProperty(amount);
        this.accountNo = new SimpleIntegerProperty(accountNo);
        this.studentId = new SimpleIntegerProperty(studentId);
        this.accountName = new SimpleStringProperty(accountName);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
    }

    public void setStudentId(int studentId) {
        this.studentId.set(studentId);
    }

    public int getAccountNo() {
        return accountNo.get();
    }

    public IntegerProperty accountNoProperty() {
        return accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo.set(accountNo);
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

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }
}
