package edu.university.schools.models;

import edu.university.schools.SQLConn;

import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.university.schools.SQLConn.*;

public class Student implements StudentAbility {

    private int id;
    private String name;
    private int school_id;
    private int program_id;

    public Student() {

    }

    public Student(String name, int school_id, int program_id) {
        this.name = name;
        this.school_id = school_id;
        this.program_id = program_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public int getProgram_id() {
        return program_id;
    }

    public void setProgram_id(int program_id) {
        this.program_id = program_id;
    }

    @Override
    public String add() {
        return "INSERT INTO course(course_id,name,credit_hour) "
                + "VALUES(?,?,?)";
    }

    @Override
    public String drop() {
        return "DELETE FROM course WHERE course_id=?";

    }

    @Override
    public Map<Integer,BigDecimal> CalAvg(List<Integer> id) {
        Map<Integer,BigDecimal> marksList = new HashMap<>();
        String QUERY = "SELECT SUM(COALESCE(first_exam,0))+SUM(mid_exam)+SUM(final_exam) As avg FROM marks WHERE course_id=?";

        try (Connection connection = DriverManager.getConnection(SQLConn.url, SQLConn.user, SQLConn.password);
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY);) {
            for (int j = 0; j < id.size(); j++) {
                preparedStatement.setInt(1, id.get(j));
                // Step 3: Execute the query or update query
                ResultSet rs = preparedStatement.executeQuery();
                // Step 4: Process the ResultSet object.
                while (rs.next()) {
                    marksList.put(id.get(j),rs.getBigDecimal("avg"));
                }
            }


        } catch (SQLException e) {
            System.out.println(e);
        }

        return marksList;

    }

    @Override
    public double CalFees(int id) throws SQLException {
        List<Courses> coursesList = null;
        Student student;
        student = getStudentFromDB(this.getName());
        String QUERY = "SELECT credit_hour_fees FROM program WHERE program_id=?";
        Courses courses;
        Program program;
        program = getProgram(id);
        double avg = 0.0;
        coursesList = getCourses(student.getId());
        for (Courses courses1 : coursesList) {
            courses = courses1;
            avg += program.getProgramCreditHourFees() * courses.getCreditHour();

        }
        return avg / coursesList.size();
    }
}
