package edu.university.schools.services;

import edu.university.schools.models.Courses;
import edu.university.schools.models.Student;
import edu.university.schools.models.instructor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static edu.university.schools.SQLConn.*;

public class InstructorServices implements InstructorServicesImp {
    private instructor instructor;

    public InstructorServices(instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public instructor CheckInstructorWithNameExists(String name) throws SQLException {
//        instructor instructor;
        instructor = getInstructorFromDB(name);
        if (instructor.getId() > 0) {
            System.out.println("Instructor with name: " + name + " does Exists");
        } else {
            System.out.println("Instructor with name: " + name + " does not Exists");
        }
        return instructor;
    }

    @Override
    public void InstructorSchool(Scanner scanner,String name)throws SQLException {
//        instructor instructor = new instructor();
        System.out.println("please Enter your Salary");
        NotNumber(scanner);
        int Salary = scanner.nextInt();

        System.out.println("Please " + name + " enter number of the program : \n1- Computing and Informatics" +
                "\n2- Engineering Technology");
        NotNumber(scanner);
        int enteredSchool = scanner.nextInt();
        if (enteredSchool == 1) {
            instructor.setName(name);
            instructor.setSalary(Salary);
            instructor.setSchool_id(enteredSchool);
//            instructor instructor = new instructor(name, salary, enteredSchool);
            executeStatInstructor(instructor);
        } else if (enteredSchool == 2) {
            instructor.setName(name);
            instructor.setSalary(Salary);
            instructor.setSchool_id(enteredSchool);
//            instructor instructor = new instructor(name, salary, enteredSchool);
            executeStatInstructor(instructor);
        } else {
            System.out.println("Wrong entered");
        }
    }

    @Override
    public void CreateTableMarks(Scanner start, instructor instructor, int selectedExaminationChoices, Student student) throws SQLException {
        String Query = "CREATE TABLE IF NOT EXISTS marks AS SELECT instructor.instructor_id , student.student_id , student.program_id , course.course_id , course.name  FROM instructor" +
                " JOIN student ON instructor.school_id = student.school_id " +
                "JOIN course ON student.student_id = course.student_id GROUP BY instructor_id,student.student_id,course_id";
        if (student.getId() > 0) {
            stmt.executeUpdate(Query);
            Query = "ALTER TABLE marks ADD COLUMN IF NOT EXISTS first_exam DECIMAL";
            stmt.execute(Query);
            Query = "ALTER TABLE marks ADD COLUMN IF NOT EXISTS mid_exam DECIMAL constraint SET DEFAULT 0.0";
            stmt.execute(Query);
            Query = "ALTER TABLE marks ADD COLUMN IF NOT EXISTS final_exam DECIMAL constraint SET DEFAULT 0.0";
            stmt.execute(Query);

            String sql = "SELECT Count(course_id) AS counter FROM marks WHERE student_id = 1";
            int count = 0;
            ResultSet affectedrows = stmt.executeQuery(sql);
            while (affectedrows.next()) {
                count = affectedrows.getInt("counter");
            }
            WRONG_COURSE_ID:
            for (int i = 0; i < count; i++) {
                System.out.println("please enter the id of course");
                NotNumber(start);
                int courseId = start.nextInt();
                Courses c = getCourse(courseId);
                if (c.getId() > 0) {
                    if (selectedExaminationChoices == 1) {
                        System.out.println("please fill the first Exam mark");
                        NotNumber(start);
                        BigDecimal firstExam = start.nextBigDecimal();
                        System.out.println("please fill the mid Exam mark");
                        NotNumber(start);
                        BigDecimal midExam = start.nextBigDecimal();
                        System.out.println("please fill the final Exam mark");
                        NotNumber(start);
                        BigDecimal finalExam = start.nextBigDecimal();
                        updateMark(courseId, firstExam, midExam, finalExam);
                    } else {
                        System.out.println("please fill the mid Exam mark");
                        NotNumber(start);
                        BigDecimal midExam = start.nextBigDecimal();
                        System.out.println("please fill the final Exam mark");
                        NotNumber(start);
                        BigDecimal finalExam = start.nextBigDecimal();
                        updateMark(courseId, null, midExam, finalExam);
                    }
                } else {
                    System.out.println("The is not provided correctly please try again");
                    i = i - 1;
                    continue WRONG_COURSE_ID;
                }
            }
            System.out.println("if you would like to return to menu press * else press any key to finish");
            String repeat = start.next();
            if (repeat.equals("*"))
                ExsitsInstrurctorMenu(start, instructor);
        } else {
            System.out.println("please be sure you entered right id of student");
            enterRightStudentIdAndSelectedExaminationMethod(start, instructor, selectedExaminationChoices);
        }
    }

    @Override
    public void enterRightStudentIdAndSelectedExaminationMethod(Scanner start, instructor instructor, int selectedExaminationChoices) throws SQLException {
        System.out.println("please Enter id of student");
        NotNumber(start);
        int studentId = start.nextInt();
        Student student;
        student = getStudentById(studentId);
        if (selectedExaminationChoices == 1) {

            CreateTableMarks(start, instructor, selectedExaminationChoices, student);
        } else if (selectedExaminationChoices == 2) {

            CreateTableMarks(start, instructor, selectedExaminationChoices, student);
        } else {
            System.out.println("you entered wrong entry of choices");
        }
    }

    @Override
    public void ExsitsInstrurctorMenu(Scanner start, instructor instructor) throws SQLException {
        System.out.println("please select one of these methods" +
                "\n1- Add mark to Student"
//                +"\n2- Assign Weights of Exams "
                );
        NotNumber(start);
        int selectedMethod = start.nextInt();

        if (selectedMethod == 1) {
            System.out.println("Please select from these choices" +
                    "\n1- First And Mid And Final\n" +
                    "2- Mid and Final");
            NotNumber(start);
            int selectedExaminationChoices = start.nextInt();

            enterRightStudentIdAndSelectedExaminationMethod(start, instructor, selectedExaminationChoices);
        }
        //else if (selectedMethod == 2) {
          //  instructor.wightExam();
        //}
        else {
            System.out.println("you entered wrong number");
            ExsitsInstrurctorMenu(start, instructor);
        }
    }

    private static void NotNumber(Scanner start) {
        if (!start.hasNextInt()) {
            System.out.println("That's not a number!");
            start.next();
        }
    }
}

