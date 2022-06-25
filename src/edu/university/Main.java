package edu.university;

import edu.university.schools.Enums.ProgramSD;
import edu.university.schools.Enums.Programs;
import edu.university.schools.models.*;
import edu.university.schools.services.InstructorServices;
import edu.university.schools.services.StudentServices;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

import static edu.university.schools.SQLConn.*;

public class Main {


    public static void main(String[] args) throws SQLException, FileNotFoundException {
        // write your code here
        instructor instructor = new instructor();
        InstructorServices InstructorServices = new InstructorServices(instructor);
        Student student=new Student();
        StudentServices studentServices = new StudentServices(student);
        //clear database
//        SQLDrop();
        //create new database
        SqlConnection();
        //set up dean and school and program
//        setUpDeanAndSchoolAndProgram();
        Scanner start = new Scanner(System.in);
        System.out.println("Welcome to our School please be attention to question below \nif you wanna Exit type exit or Exit else press any key");
        String Exit = start.nextLine();
        while (!Exit.toUpperCase().equals("EXIT")) {
            System.out.println("select number of which role you are : \n1- Instructor \n2- Student");
            NotNumber(start);
            int selectedRole = start.nextInt();
            System.out.println("Enter your name Please: ");
            String name = start.next();
            System.out.println("Welcome again " + name + " to our School");
            roleEntered:
            if (selectedRole == 1) {
                instructor = InstructorServices.CheckInstructorWithNameExists(name);
                if (instructor.getId() > 0) {
                    InstructorServices.ExsitsInstrurctorMenu(start, instructor);
                } else {
                    InstructorServices.InstructorSchool(start, name);
                }
                break roleEntered;
            } else if (selectedRole == 2) {
                student = studentServices.CheckStudentWithNameExists(name);
                if (student.getId() > 0) {
                    studentServices.StudentMethods(start, student);
                } else {
                    studentServices.studentSchool(start, name);
                }
            } else {
                System.out.println("you either entered wrong number or either entered a text check again");
            }
        }


    }

    private static void setUpDeanAndSchoolAndProgram() throws SQLException {
        Dean deanSD = new Dean();
        deanSD.setName("Pro.Isa Curl");
        executeStatDean(deanSD);
        int dean_id = 0;
        dean_id = getDeanId(deanSD);
        ComputerAndInformationTechnology computerAndInformationTechnology =
                new ComputerAndInformationTechnology();
        computerAndInformationTechnology.setName("Computer And Information Technology");
        computerAndInformationTechnology.setDean_id(dean_id);
        executeStatSchool(computerAndInformationTechnology);
        Program program = new Program();
        program.setProgramId(1);
        program.setProgramName(ProgramSD.COMPUTER_SCIENCE.toString());
        program.setProgramCreditHourFees(200);
        program.setHeadDep("Software Science");
        executeStatProgram(program);
        program = new Program();
        program.setProgramId(2);
        program.setProgramName(ProgramSD.DATA_SCIENCE.toString());
        program.setProgramCreditHourFees(220);
        program.setHeadDep("Data Science");
        executeStatProgram(program);
        program = new Program();
        program.setProgramId(3);
        program.setProgramName(ProgramSD.CYBER_SECURITY.toString());
        program.setProgramCreditHourFees(250);
        program.setHeadDep("Security Science");
        executeStatProgram(program);
        Dean deanEng = new Dean();
        deanEng.setName("Pro.Moral Hunter");
        executeStatDean(deanEng);
        dean_id = getDeanId(deanEng);
        EngineeringTechnology engineeringTechnology =
                new EngineeringTechnology();
        engineeringTechnology.setName("Engineering Technology");
        engineeringTechnology.setDean_id(dean_id);
        executeStatSchool(engineeringTechnology);
        program = new Program();
        program.setProgramId(4);
        program.setProgramName(Programs.ELECTRICAL_ENGINEERING.toString());
        program.setProgramCreditHourFees(260);
        program.setHeadDep("Electric Technology");
        executeStatProgram(program);
        program = new Program();
        program.setProgramId(5);
        program.setProgramName(Programs.ENERGY_ENGINEERING.toString());
        program.setProgramCreditHourFees(280);
        program.setHeadDep("Energy Technology");
        executeStatProgram(program);
        program = new Program();
        program.setProgramId(6);
        program.setProgramName(Programs.MECHANICAL_ENGINEERING.toString());
        program.setProgramCreditHourFees(300);
        program.setHeadDep("Physics Technology");
        executeStatProgram(program);
    }


    private static void NotNumber(Scanner start) {
        if (!start.hasNextInt()) {
            System.out.println("That's not a number!");
            start.next();
        }
    }
}

