package edu.university.schools.services;

import edu.university.schools.models.Student;

import java.sql.SQLException;
import java.util.Scanner;

public interface StudentServicesImp {
    Student CheckStudentWithNameExists(String name) throws SQLException;
    void studentSchool(Scanner start, String name) throws SQLException;
    void studentProgram(String name, int enteredSchool, int selectedProgram, Scanner start) throws SQLException;
    void StudentMethods(Scanner start, Student student) throws SQLException;
    Student getStudent(String name, int enteredSchool, int selectedProgram) throws SQLException;
}
