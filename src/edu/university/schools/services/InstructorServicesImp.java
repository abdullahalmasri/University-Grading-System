package edu.university.schools.services;

import edu.university.schools.models.Student;
import edu.university.schools.models.instructor;

import java.sql.SQLException;
import java.util.Scanner;

public interface InstructorServicesImp {
    instructor CheckInstructorWithNameExists(String name) throws SQLException;
    void InstructorSchool(Scanner scanner,String name) throws SQLException;
    void CreateTableMarks(Scanner start, instructor instructor, int selectedExaminationChoices, Student student) throws SQLException;
    void enterRightStudentIdAndSelectedExaminationMethod(Scanner start, instructor instructor, int selectedExaminationChoices) throws SQLException;
    void ExsitsInstrurctorMenu(Scanner start, instructor instructor) throws SQLException;
}
