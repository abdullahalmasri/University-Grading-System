package edu.university.schools.services;

import edu.university.schools.models.Courses;
import edu.university.schools.Enums.ProgramSD;
import edu.university.schools.Enums.Programs;
import edu.university.schools.models.Student;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static edu.university.schools.SQLConn.*;

public class StudentServices implements StudentServicesImp {
    private Student student;

    public StudentServices(Student student) {
        this.student = student;
    }

    @Override
    public Student CheckStudentWithNameExists(String name) throws SQLException {
        student = getStudentFromDB(name);
        if (student.getId() == 0) {
            System.out.println("Student with name: " + name + " does not Exists");
        } else {
            System.out.println("Student with name: " + name + " does Exists");
        }
        return student;
    }

    @Override
    public void studentSchool(Scanner start, String name) throws SQLException {
        System.out.println("please " + name + " enter number of the school : " +
                "\n1- Computing and Informatics" +
                "\n2- Engineering Technology");
        NotNumber(start);
        int enteredSchool = start.nextInt();
        if (enteredSchool == 1) {
            System.out.println("please " + name + " enter number of the program : " +
                    "\n1- " + ProgramSD.COMPUTER_SCIENCE +
                    "\n2- " + ProgramSD.DATA_SCIENCE +
                    "\n3- " + ProgramSD.CYBER_SECURITY);
            NotNumber(start);
            int selectedProgram = start.nextInt();

            studentProgram(name, enteredSchool, selectedProgram, start);
            return;

        } else if (enteredSchool == 2) {
            System.out.println("please " + name +
                    " enter number of the program : 1 - " +
                    Programs.ELECTRICAL_ENGINEERING +
                    "\n 2- " +
                    Programs.ENERGY_ENGINEERING + "\n 3- " +
                    Programs.MECHANICAL_ENGINEERING);
            NotNumber(start);
            int selectedProgram = start.nextInt();

            studentProgram(name, enteredSchool, selectedProgram, start);
            return;
        } else {
            System.out.println("Wrong entered");
        }
    }

    @Override
    public void studentProgram(String name, int enteredSchool, int selectedProgram, Scanner start) throws SQLException {
        student = getStudent(name, enteredSchool, selectedProgram);
        StudentMethods(start, student);

    }

    @Override
    public void StudentMethods(Scanner start, Student student) throws SQLException {
        int selectedMethod = getSelectedMethod(start, "Please choice the method to apply: \n"
                + "1- Add Course" + "\n" + "2- Drop Course" + "\n" + "3- Calculate AVG"
                + "\n" + "4- Calculate Fees" + "\n" + "5- Exit");
        if (selectedMethod == 1) {
            Courses courses = new Courses();
            System.out.println("Enter id of course");
            NotNumber(start);
            int course_id = start.nextInt();
            courses.setId(course_id);
            System.out.println("enter name of course");
            String courseName = start.next();
            courses.setName(courseName);
            System.out.println("enter credit hour");
            NotNumber(start);
            int credit_hour = start.nextInt();
            courses.setCreditHour(credit_hour);
            //                  courses = new Courses(course_id, courseName, credit_hour);
            String studentAdd = student.add();
            executeStatCourses(courses, student);
        } else if (selectedMethod == 2) {
            System.out.println("Enter id of course");
            NotNumber(start);
            int course_id = start.nextInt();
            String Drop = student.drop();
            int CourseDrop = DeleteFromDB(course_id, Drop);
            System.out.println("you have Delete " + CourseDrop + " perfectly");
        } else if (selectedMethod == 3) {
            List<Integer> list = new ArrayList<>();
            System.out.println("Please enter id of courses");
            NotNumber(start);
            while (start.hasNextInt()) {
                System.out.println("please when you finish type the courses id's type *  and hit enter");
                NotNumber(start);
                list.add(start.nextInt());
            }

            Map<Integer, BigDecimal> avg = student.CalAvg(list);
            avg.forEach((key, value) -> {
                if (value.compareTo(BigDecimal.ZERO) > 0 && value.compareTo(BigDecimal.valueOf(50)) < 0) {
                    System.out.println("the course id: " + key + " have the got F");
                } else if (value.compareTo(BigDecimal.valueOf(50)) >= 0 && value.compareTo(BigDecimal.valueOf(60)) < 0) {
                    System.out.println("the course id: " + key + " have the got D");
                } else if (value.compareTo(BigDecimal.valueOf(60)) >= 0 && value.compareTo(BigDecimal.valueOf(70)) < 0) {
                    System.out.println("the course id: " + key + " have the got C");
                } else if (value.compareTo(BigDecimal.valueOf(70)) >= 0 && value.compareTo(BigDecimal.valueOf(80)) < 0) {
                    System.out.println("the course id: " + key + " have the got B");
                } else if (value.compareTo(BigDecimal.valueOf(80)) >= 0 && value.compareTo(BigDecimal.valueOf(90)) < 0) {
                    System.out.println("the course id: " + key + " have the got A-");
                } else if (value.compareTo(BigDecimal.valueOf(90)) >= 0 && value.compareTo(BigDecimal.valueOf(100)) <= 0) {
                    System.out.println("the course id: " + key + " have the got A");
                }
//                System.out.println("key is " + key + " and value is " + value);
            });
            start = new Scanner(System.in);
//            System.out.println("your Avg is : " + avg);
        } else if (selectedMethod == 4) {
            System.out.println(
                    "Please enter id of programs");
            NotNumber(start);
            int id = start.nextInt();
            double Fees = student.CalFees(id);
            System.out.println("your Fees is : " + Fees);
        } else if (selectedMethod == 5) {
            return;
        } else {
            System.out.println("wrong entered");
        }
        repeatMneuStudent(start, student);
    }

    private void repeatMneuStudent(Scanner start, Student student) throws SQLException {
        System.out.println("Please is there any Method would you like to check " +
                "write down * if you would like to go to menu again");
        String repeat = start.next();
        if (repeat.equals("*"))
            StudentMethods(start, student);
    }

    @Override
    public Student getStudent(String name, int enteredSchool, int selectedProgram) throws SQLException {
        if (selectedProgram == 1) {
            if (enteredSchool == 2) {
                selectedProgram = SelectProgramStmt(Programs.ELECTRICAL_ENGINEERING);
                student.setName(name);
                student.setSchool_id(enteredSchool);
                student.setProgram_id(selectedProgram);
//                student = new Student(name, enteredSchool, selectedProgram);
                executeStatStudent(student);
            } else {
                student.setName(name);
                student.setSchool_id(enteredSchool);
                student.setProgram_id(selectedProgram);
//                student = new Student(name, enteredSchool, selectedProgram);
                executeStatStudent(student);
            }
        } else if (selectedProgram == 2) {
            if (enteredSchool == 2) {
                selectedProgram =
                        SelectProgramStmt(Programs.ENERGY_ENGINEERING);
                student.setName(name);
                student.setSchool_id(enteredSchool);
                student.setProgram_id(selectedProgram);
//                student = new Student(name, enteredSchool, selectedProgram);
                executeStatStudent(student);
            } else {
//                student = new Student(name, enteredSchool, selectedProgram);
                student.setName(name);
                student.setSchool_id(enteredSchool);
                student.setProgram_id(selectedProgram);
                executeStatStudent(student);
            }
        } else if (selectedProgram == 3) {
            if (enteredSchool == 2) {
                selectedProgram =
                        SelectProgramStmt(Programs.MECHANICAL_ENGINEERING);
//                student = new Student(name, enteredSchool, selectedProgram);
                student.setName(name);
                student.setSchool_id(enteredSchool);
                student.setProgram_id(selectedProgram);
                executeStatStudent(student);
            } else {
//                student = new Student(name, enteredSchool, selectedProgram);
                student.setName(name);
                student.setSchool_id(enteredSchool);
                student.setProgram_id(selectedProgram);
                executeStatStudent(student);
            }
//            student = new Student(name, enteredSchool, selectedProgram);
            student.setName(name);
            student.setSchool_id(enteredSchool);
            student.setProgram_id(selectedProgram);
            executeStatStudent(student);
        } else {
            System.out.println("Entered wrong number");
        }
        return student;
    }

    private static int getSelectedMethod(Scanner start, String s) {
        System.out.println(s);
        NotNumber(start);
        return start.nextInt();
    }

    private static void NotNumber(Scanner start) {
        if (!start.hasNextInt()) {
            System.out.println("That's not a number!");
            start.next();
        }
    }
}
