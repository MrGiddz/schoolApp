package main.resources.utilities.actions;

import main.resources.utilities.connection.DatabaseConnect;
import org.h2.engine.Database;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AddAction {


    void clearEntries(){

    };

    boolean addSubject(String subjectName, boolean isCompulsory, int schoolID) throws SQLException {
        DatabaseConnect.preparedStatement = DatabaseConnect.connect().prepareStatement("INSERT INTO SUBJECT ( SUBJECT_NAME , COMPULSORY , SCHOOL_ID , TIME_ADDED , DATE_ADDED  ) VALUES ( ? , ? , ? , ? , ? ) ");
        DatabaseConnect.preparedStatement.setString(1, subjectName);
        DatabaseConnect.preparedStatement.setBoolean(2, isCompulsory);
        DatabaseConnect.preparedStatement.setInt(3, schoolID);
        DatabaseConnect.preparedStatement.setString(4, LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
        DatabaseConnect.preparedStatement.setString(5, new SimpleDateFormat("yyyy/mm/dd").format(new Date()));

        int i = DatabaseConnect.preparedStatement.executeUpdate();

        if (i > 0){
            return true;
        }
        DatabaseConnect.close();
        return false;
    }

    boolean addClass(String className, int schoolID, int tuition) throws SQLException {
        DatabaseConnect.preparedStatement = DatabaseConnect.connect().prepareStatement("INSERT INTO CLASSES ( NAME , SCHOOL_ID , TUITION , TIME , DATE  ) VALUES ( ? , ? , ? , ? , ? ) ");
      ;
        DatabaseConnect.preparedStatement.setString(1, className);
        DatabaseConnect.preparedStatement.setInt(2, schoolID);
        DatabaseConnect.preparedStatement.setInt(3, tuition);
        DatabaseConnect.preparedStatement.setString(4, LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
        DatabaseConnect.preparedStatement.setString(5, new SimpleDateFormat("yyyy/mm/dd").format(new Date()));

        int i = DatabaseConnect.preparedStatement.executeUpdate();

        if (i > 0){
            return true;
        }
        DatabaseConnect.close();
        return false;
    }

    boolean addTeacher(String teacherName, int schoolID, int subjectID) throws SQLException {
        DatabaseConnect.preparedStatement = DatabaseConnect.connect().prepareStatement("INSERT INTO TEACHER ( NAME , SCHOOL_ID , SUBJECT , TIME , DATE ) VALUES ( ? , ? ,? , ? , ? )");
        ;
        DatabaseConnect.preparedStatement.setString(1, teacherName);
        DatabaseConnect.preparedStatement.setInt(2, schoolID);
        DatabaseConnect.preparedStatement.setInt(3, subjectID);
        DatabaseConnect.preparedStatement.setString(4, LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
        DatabaseConnect.preparedStatement.setString(5, new SimpleDateFormat("yyyy/mm/dd").format(new Date()));

        int i = DatabaseConnect.preparedStatement.executeUpdate();

        if (i > 0){
            return true;
        }
        DatabaseConnect.close();
        return false;
    }


}
