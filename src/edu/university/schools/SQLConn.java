package edu.university.schools;

import edu.university.schools.Enums.Programs;
import edu.university.schools.Enums.TablesName;
import edu.university.schools.models.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class SQLConn {
    //path to your sqlFile.sql
    public static String aSQLScriptFilePath = "/path-to/Tables.sql";

    //you need create database with this name 'university'
    public static final String url = "jdbc:postgresql://localhost:5432/university";
    //user default
    public static final String user = "postgres";
    //your password. root is default
    public static final String password = "postgres";
    public static Connection conn;
    static TablesName[] tablesNames = TablesName.values();


    static {
        try {
            //open the connection
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Statement stmt = null;

    /**
     * here we use the Query to Drop all tables in DB
     */
    public static void SQLDrop() throws SQLException {

        ArrayList<String> list = new ArrayList<>();
        String statement;
        for (int i = 0; i < tablesNames.length; i++) {
            statement = "DROP TABLE " + tablesNames[i] + ";";
            list.add(statement);
        }
        stmt = conn.createStatement();

        list.forEach(x -> {
            try {
                stmt.executeUpdate(x);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

    }

    /**
     * the method here call the each query in sqlFile and read each query using
     * BufferReader and FileReader and create new Table in DB
     */
    public static void SqlConnection() {
        BufferedReader reader;
        String s;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(aSQLScriptFilePath));
            while ((s = reader.readLine()) != null) {
                sb.append(s);
            }
            reader.close();
            String[] inst = sb.toString().split(";");
            stmt = conn.createStatement();
            Arrays.stream(inst).map(String::trim).forEach(x -> {
                try {
                    stmt.executeUpdate(x);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            //another way but i like stream since it's less code

//            for(int i = 0; i<inst.length; i++)
//            {
//                // we ensure that there is no spaces before or after the request string
//                // in order to not execute empty statements
//                if(!inst[i].trim().equals(""))
//                {
//                    stmt.executeUpdate(inst[i]);
////                    System.out.println(">>"+inst[i]);
//                }
//            }

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

    }

    /**
     * the method here will execute the insert statement of Dean into dean Table in DB
     * while the values will come from method in main,where it have
     *
     * @param dean
     */
    public static void executeStatDean(Dean dean) throws SQLException {
        String SQL = "INSERT INTO dean(name)"
                + "VALUES(?);";


        PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, dean.getName());
        ps.executeUpdate();
    }

    /**
     * the query here related to get dean id from DB using name of dean and need
     *
     * @param dean
     */
    public static int getDeanId(Dean dean) throws SQLException {
        String QUERY = "SELECT dean_id FROM dean WHERE name=?";
        PreparedStatement preparedStatement = conn.prepareStatement(QUERY);
        preparedStatement.setString(1, dean.getName());
        // Step 3: Execute the query or update query
        ResultSet rs = preparedStatement.executeQuery();
        int dean_id = 0;
        // Step 4: Process the ResultSet object.
        while (rs.next()) {
            dean_id = rs.getInt("dean_id");
        }

        return dean_id;

    }

    /**
     * the method here is to execute to save school in DB, where need
     *
     * @param school
     */
    public static void executeStatSchool(School school) throws SQLException {
        String SQL = "INSERT INTO school(name,dean_id) "
                + "VALUES(?,?)";


        PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, school.getName());
        ps.setInt(2, school.getDean_id());
        ps.executeUpdate();
    }

    /**
     * the query here return integer value of program_id where it need
     *
     * @param programs which its value come from enum class and cast to string
     */
    public static int SelectProgramStmt(Programs programs) throws SQLException {
        String QUERY = "SELECT program_id FROM program WHERE name=?";
        PreparedStatement preparedStatement = conn.prepareStatement(QUERY);
        preparedStatement.setString(1, programs.toString());
        System.out.println(preparedStatement);
        // Step 3: Execute the query or update query
        ResultSet rs = preparedStatement.executeQuery();
        int program_id = 0;
        // Step 4: Process the ResultSet object.
        while (rs.next()) {
            program_id = rs.getInt("program_id");
        }

        return program_id;
    }

    /**
     * the method here execute insert statement to save new program in DB
     * and accept
     *
     * @param program
     */
    public static void executeStatProgram(Program program) throws SQLException {
        String
                SQL = "INSERT INTO program(program_id,name,credit_hour_fees,head_dep) "
                + "VALUES(?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, program.getProgramId());
        ps.setString(2, program.getProgramName());
        ps.setInt(3, program.getProgramCreditHourFees());
        ps.setString(4, program.getHeadDep());
        ps.executeUpdate();
    }

    /**
     * the method here is to get program from DB
     * and accept integer value to return object from type Program
     *
     * @param id
     */
    public static Program getProgram(int id) throws SQLException {
        Program program = new Program();
        String QUERY = "SELECT * FROM program WHERE program_id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(QUERY);
        preparedStatement.setInt(1, id);
        // Step 3: Execute the query or update query
        ResultSet rs = preparedStatement.executeQuery();
        // Step 4: Process the ResultSet object.
        while (rs.next()) {
            program.setProgramId((rs.getInt("program_id")));
            program.setProgramName(rs.getString("name"));
            program.setProgramCreditHourFees(rs.getInt("credit_hour_fees"));
            program.setHeadDep(rs.getString("head_dep"));
        }

        return program;
    }


    /**
     * courses sql where it save new course and need to be execute to save in DB
     *
     * @param courses
     * @param student
     */
    public static void executeStatCourses(Courses courses, Student student) throws SQLException {
        String SQL = "INSERT INTO course(course_id,name,credit_hour,student_id,program_id) "
                + "VALUES(?,?,?,?,?)";


        student = getStudentFromDB(student.getName());
        PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, courses.getId());
        ps.setString(2, courses.getName());
        ps.setInt(3, courses.getCreditHour());
        ps.setInt(4, student.getId());
        ps.setInt(5, student.getProgram_id());
        ps.executeUpdate();
    }

    /**
     * the method here is to get course from DB
     * and accept integer value to return object from type course
     *
     * @param id
     */
    public static Courses getCourse(int id) throws SQLException {
        Courses course = new Courses();
        String QUERY = "SELECT * FROM course WHERE course_id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(QUERY);
        preparedStatement.setInt(1, id);
        // Step 3: Execute the query or update query
        ResultSet rs = preparedStatement.executeQuery();
        int size = 0;
        while (rs.next()) {
            size++;
            Courses courses = new Courses();
            courses.setId((rs.getInt("course_id")));
            courses.setName(rs.getString("name"));
            courses.setCreditHour(rs.getInt("credit_hour"));
            courses.setStudent_id(rs.getInt("student_id"));
            courses.setProgram_id(rs.getInt("program_id"));
            course = courses;
        }

        return course;
    }

    /**
     * the method here is to get list of courses from DB
     * and accept integer value to return object from type list of courses
     *
     * @param id
     */
    public static List<Courses> getCourses(int id) throws SQLException {
        List<Courses> coursesArrayList = new ArrayList<>();
        String QUERY = "SELECT * FROM course WHERE student_id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(QUERY);
        preparedStatement.setInt(1, id);
        // Step 3: Execute the query or update query
        ResultSet rs = preparedStatement.executeQuery();
        int size = 0;
        while (rs.next()) {
            size++;
            Courses courses = new Courses();
            courses.setId((rs.getInt("course_id")));
            courses.setName(rs.getString("name"));
            courses.setCreditHour(rs.getInt("credit_hour"));
            courses.setStudent_id(rs.getInt("student_id"));
            courses.setProgram_id(rs.getInt("program_id"));
            coursesArrayList.add(courses);
        }

        return coursesArrayList;
    }


    //another way to fill insert statement with constructor with param
//    private static void executeStatCourse(int a, String str, int b, String SQL) throws SQLException {
//        Connection conn = null;
////        String SQL = "INSERT INTO course(course_id,name,credit_hour) "
////                + "VALUES(?,?,?)";
//        try {
//            conn = DriverManager.getConnection(url, user, password);
//            PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
//            ps.setInt(1, a);
//            ps.setString(2, str);
//            ps.setInt(3, b);
//            ps.executeUpdate();
//        } catch (SQLException se) {
//            System.out.println(se.getMessage());
//        } finally {
//            conn.close();
//        }
//
//    }

    /**
     * here the method do save for student in DB and execute insert statement
     *
     * @param student
     */
    public static void executeStatStudent(Student student) throws SQLException {
        String SQL = "INSERT INTO student(name,school_id,program_id) "
                + "VALUES(?,?,?)";

        PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, student.getName());
        ps.setInt(2, student.getSchool_id());
        ps.setInt(3, student.getProgram_id());
        ps.executeUpdate();
    }

    /**
     * the method return list of AssignMarkForStudent from DB where it need
     *
     * @param instructor
     * @param name
     */
    public static List assignMarkForStudent(instructor instructor, String name) throws SQLException {
        Student student;
        List<Courses> courses;
        student = getStudentFromDB(name);
        AtomicInteger studentId = new AtomicInteger();
        courses = getCourses(student.getId());
        courses.stream().forEach(x -> studentId.set(x.getStudent_id()));
        String SQL = "select * from instructor " +
                "JOIN student ON " + instructor.getSchool_id() + "=" + student.getSchool_id() +
                " JOIN course ON " + student.getId() + "=" + studentId + ";";
        PreparedStatement preparedStatement = conn.prepareStatement(SQL);
        ResultSet rs = preparedStatement.executeQuery();
        List<Integer> objects = new ArrayList<>();
        while (rs.next()) {
            objects.add(rs.getInt("instructor_id"));
            objects.add(rs.getInt("student_id"));
            objects.add(rs.getInt("course_id"));
            objects.add(rs.getInt("program_id"));
        }

        return objects;
    }

    /**
     * the method here are update marks of selected student and course and save it back ib DB
     *
     * @param courseId
     * @param a        the first mark optional (could be null)
     * @param b        the mid mark mandatory
     * @param c        the final mark mandatory
     */
    public static void updateMark(int courseId, BigDecimal a, BigDecimal b, BigDecimal c) throws SQLException {
        String SQL = "UPDATE marks SET first_exam = ?,mid_exam=?,final_exam=? WHERE course_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(SQL);
        preparedStatement.setBigDecimal(1, a);
        preparedStatement.setBigDecimal(2, b);
        preparedStatement.setBigDecimal(3, c);
        preparedStatement.setInt(4, courseId);
        preparedStatement.executeUpdate();
    }


    /**
     * here the method do get student from DB By name
     *
     * @param name
     */
    public static Student getStudentFromDB(String name) throws SQLException {
        Student student = new Student();
        String QUERY = "SELECT * FROM student WHERE name=?";
        PreparedStatement preparedStatement = conn.prepareStatement(QUERY);
        preparedStatement.setString(1, name);
        // Step 3: Execute the query or update query
        ResultSet rs = preparedStatement.executeQuery();
        // Step 4: Process the ResultSet object.
        while (rs.next()) {
            student.setId(rs.getInt("student_id"));
            student.setName(rs.getString("name"));
            student.setProgram_id(rs.getInt("program_id"));
            student.setSchool_id(rs.getInt("school_id"));
        }

        return student;
    }

    /**
     * here the method do get student from DB By Id
     *
     * @param id
     */
    public static Student getStudentById(int id) throws SQLException {
        Student student = new Student();
        String QUERY = "SELECT * FROM student WHERE student_id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(QUERY);
        preparedStatement.setInt(1, id);
        // Step 3: Execute the query or update query
        ResultSet rs = preparedStatement.executeQuery();
        // Step 4: Process the ResultSet object.
        while (rs.next()) {
            student.setId(rs.getInt("student_id"));
            student.setName(rs.getString("name"));
            student.setProgram_id(rs.getInt("program_id"));
            student.setSchool_id(rs.getInt("school_id"));
        }

        return student;
    }

    /**
     * here the method do save instructor in DB
     *
     * @param instructor
     */
    public static void executeStatInstructor(instructor instructor) throws SQLException {
        String SQL =
                "INSERT INTO instructor(name,salary,school_id) "
                        + "VALUES(?,?,?)";

        PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, instructor.getName());
        ps.setInt(2, instructor.getSalary());
        ps.setInt(3, instructor.getSchool_id());
        ps.executeUpdate();
    }

    /**
     * here the method do get instructor from DB By name
     *
     * @param name
     */
    public static instructor getInstructorFromDB(String name) throws SQLException {
        instructor instructor = new instructor();
        String QUERY = "SELECT * FROM instructor WHERE name=?";
        PreparedStatement preparedStatement = conn.prepareStatement(QUERY);
        preparedStatement.setString(1, name);
//        System.out.println(preparedStatement);
        // Step 3: Execute the query or update query
        ResultSet rs = preparedStatement.executeQuery();

        // Step 4: Process the ResultSet object.
        while (rs.next()) {
            instructor.setId(rs.getInt("instructor_id"));
            instructor.setName(rs.getString("name"));
            instructor.setSalary(rs.getInt("salary"));
            instructor.setSchool_id(rs.getInt("school_id"));

        }

        return instructor;
    }

    /**
     * the method here allow delete course from DB
     *
     * @param id
     * @param SQL
     */
    public static int DeleteFromDB(int id, String SQL) throws SQLException {
        int affectedrows = 0;
        PreparedStatement pstmt = conn.prepareStatement(SQL);
        pstmt.setInt(1, id);
        affectedrows = pstmt.executeUpdate();
        return affectedrows;
    }

    /**
     * Here we gonna use select statement to create table for Marks of Students
     */

    public static void createNewTableMarks(int instructorId, int studentId, int coursesId
            , double firstExam, double midExam, double finalExam) {
        String Query = "CREATE TABLE student_mark" +
                "SELECT * FROM (VALUES ROW(1,3,5), ROW(2,4,6)) AS v(First,Mid,Final)";
    }


}
