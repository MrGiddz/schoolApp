package main.resources.utilities;

import main.resources.models.*;
import main.resources.utilities.collections.ReversibleWeakHashMap;
import main.resources.utilities.connection.DatabaseConnect;
import main.resources.utilities.handles.MainPageHandle;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Objects;

public class LoadData extends MainPageHandle {

    private static ReversibleWeakHashMap<Integer, TableData> holder;
    private static ReversibleWeakHashMap<Integer, ClassData> classHolder;
    private static ReversibleWeakHashMap<Integer, TeacherData> teacherHolder;
    private static ReversibleWeakHashMap<Integer, SubjectData> subjectHolder;
    private static ReversibleWeakHashMap<Integer, PaymentData> paymentHolder;

    public static ReversibleWeakHashMap<Integer, TableData> getStudentFullInfo() throws SQLException, ParseException {
        ReversibleWeakHashMap<Integer, TableData> schools = new ReversibleWeakHashMap<>();

        DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT id, surname, firstname, middlename, date_of_birth, gender, disabled, school, class, nationality, parent_name, phone_number, email, time_added, date_added FROM students");
        DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();
        while (DatabaseConnect.res.next()){
            schools.put(DatabaseConnect.res.getInt("ID"), new TableData(DatabaseConnect.res.getInt("ID"),DatabaseConnect.res.getString("surname"),
                    DatabaseConnect.res.getString("firstname"), DatabaseConnect.res.getString("middlename"),
                    DatabaseConnect.res.getString("gender"), DatabaseConnect.res.getString("disabled"),
                    DatabaseConnect.res.getString("school"), DatabaseConnect.res.getString("class"),
                    DatabaseConnect.res.getString("nationality"), DatabaseConnect.res.getString("parent_name"),
                    DatabaseConnect.res.getString("phone_number"), DatabaseConnect.res.getString("email"), DatabaseConnect.res.getString("date_of_birth"),
                    DatabaseConnect.res.getString("time_added"), DatabaseConnect.res.getString("date_added")));
        }
        DatabaseConnect.close();
        return schools;
    }


    public static ReversibleWeakHashMap<Integer, TableData> getSchoolFullInfo() throws SQLException {
        holder =new ReversibleWeakHashMap<>();
        DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID, name, address, name_of_owner, name_of_principal, phone_no, email, can_login FROM SCHOOL");
        DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();

        while (DatabaseConnect.res.next()){
            TableData tableData = new TableData(DatabaseConnect.res.getInt("ID"),DatabaseConnect.res.getString("name"), DatabaseConnect.res.getString("address"), DatabaseConnect.res.getString("name_of_owner"), DatabaseConnect.res.getString("name_of_principal"),
                    DatabaseConnect.res.getString("phone_no"), DatabaseConnect.res.getString("email"));
            tableData.setCanLogin(DatabaseConnect.res.getString("CAN_LOGIN"));
            holder.put(DatabaseConnect.res.getInt("ID"), tableData);
        }
        DatabaseConnect.close();
        return holder;
    }

    public static ReversibleWeakHashMap<Integer, TableData> getSchoolBasicInfo() throws SQLException {
        holder =new ReversibleWeakHashMap<>();
        DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID, name FROM SCHOOL");
        DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();
        while (DatabaseConnect.res.next()){
            holder.put(DatabaseConnect.res.getInt("ID"),  new TableData(DatabaseConnect.res.getInt("ID"),DatabaseConnect.res.getString("name")));
        }
        DatabaseConnect.close();
        return holder;
    }

    public static ReversibleWeakHashMap<Integer, ClassData> getClassFullInfo() throws SQLException {
        classHolder =new ReversibleWeakHashMap<>();
        DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID, SCHOOL_ID, NAME, TUITION FROM CLASSES");
        DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();

        while (DatabaseConnect.res.next()){
            classHolder.put(DatabaseConnect.res.getInt("ID"), new ClassData(DatabaseConnect.res.getInt("ID"), userData.getUserId(), DatabaseConnect.res.getString("NAME"), DatabaseConnect.res.getInt("TUITION")));
        }
        DatabaseConnect.close();
        return classHolder;
    }

    public static ReversibleWeakHashMap<Integer, ClassData> getClassBasicInfo() throws SQLException {
        classHolder =new ReversibleWeakHashMap<>();
        DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID, NAME, TUITION FROM CLASSES WHERE SCHOOL_ID = ?");
        DatabaseConnect.preparedStatement.setInt(1, userData.getUserId());
        DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();

        while (DatabaseConnect.res.next()){
            classHolder.put(DatabaseConnect.res.getInt("ID"), new ClassData(DatabaseConnect.res.getInt("ID"), userData.getUserId(),  DatabaseConnect.res.getString("NAME"), DatabaseConnect.res.getInt("TUITION")));
        }

        DatabaseConnect.close();
        return classHolder;
    }

    public static ReversibleWeakHashMap<Integer, TeacherData> getTeacherFullInfo() throws SQLException {
        teacherHolder =new ReversibleWeakHashMap<>();
        DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID , SCHOOL_ID, NAME, SUBJECT  FROM TEACHER");
        DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();

        while (DatabaseConnect.res.next()){
            teacherHolder.put(DatabaseConnect.res.getInt("ID"), new TeacherData(DatabaseConnect.res.getInt("ID"),  DatabaseConnect.res.getInt("SUBJECT"), userData.getUserId(),  DatabaseConnect.res.getString("NAME")));
        }
        DatabaseConnect.close();
        return teacherHolder;
    }

    public static ReversibleWeakHashMap<Integer, TeacherData> getTeacherBasicInfo() throws SQLException {
        teacherHolder =new ReversibleWeakHashMap<>();
        DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID , NAME, SUBJECT  FROM TEACHER WHERE SCHOOL_ID = ?");
        DatabaseConnect.preparedStatement.setInt(1, userData.getUserId());
        DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();

        while (DatabaseConnect.res.next()){
            teacherHolder.put(DatabaseConnect.res.getInt("ID"), new TeacherData(DatabaseConnect.res.getInt("ID"),  DatabaseConnect.res.getInt("SUBJECT"), userData.getUserId(), DatabaseConnect.res.getString("NAME")));
        }
        DatabaseConnect.close();
        return teacherHolder;
    }


    public static ReversibleWeakHashMap<Integer, SubjectData> getSubjectFullInfo() throws SQLException {
        subjectHolder =new ReversibleWeakHashMap<>();
        DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID , SUBJECT_NAME , COMPULSORY , SCHOOL_ID  FROM SUBJECT");
        DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();

        while (DatabaseConnect.res.next()){
            subjectHolder.put(DatabaseConnect.res.getInt("ID"), new SubjectData(DatabaseConnect.res.getInt("ID"),  DatabaseConnect.res.getInt("SCHOOL_ID"), DatabaseConnect.res.getString("SUBJECT_NAME"), DatabaseConnect.res.getString("COMPULSORY")));
        }
        DatabaseConnect.close();
        return subjectHolder;
    }


    public static ReversibleWeakHashMap<Integer, SubjectData> getSubjectBasicInfo() throws SQLException {
        subjectHolder =new ReversibleWeakHashMap<>();
        DatabaseConnect.preparedStatement = Objects.requireNonNull(DatabaseConnect.connect()).prepareStatement("SELECT ID , SUBJECT_NAME , COMPULSORY  FROM SUBJECT WHERE SCHOOL_ID = ?");
        DatabaseConnect.preparedStatement.setInt(1, userData.getUserId());
        DatabaseConnect.res = DatabaseConnect.preparedStatement.executeQuery();

        while (DatabaseConnect.res.next()){
            SubjectData subjectData = new SubjectData(DatabaseConnect.res.getInt("ID"), userData.getUserId(), DatabaseConnect.res.getString("SUBJECT_NAME"),  DatabaseConnect.res.getString("COMPULSORY"));
            System.out.println("getSubjectBasicInfo" + " subjectName = " + subjectData.getSubjectName());

            subjectHolder.put(DatabaseConnect.res.getInt("ID"), subjectData);
        }
        DatabaseConnect.close();
        return subjectHolder;
    }

}
