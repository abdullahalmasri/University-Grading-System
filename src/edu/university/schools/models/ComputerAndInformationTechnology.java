package edu.university.schools.models;

import edu.university.schools.Enums.ProgramSD;

import java.sql.Timestamp;

public class ComputerAndInformationTechnology extends School implements Semester {
    public ComputerAndInformationTechnology(String name, int dean) {
        super(name, dean);
    }

    public static void print(String courseName) {
        System.out.println("Welcome to Computer Information Technology,you selected right program: " + courseName);
        System.out.println("please fill the course id & course name & credit hour");
    }

    public ComputerAndInformationTechnology() {
    }

    @Override
    public Timestamp startTime(Timestamp timestamp) {
        return timestamp;
    }

    @Override
    public Timestamp endTime(Timestamp timestamp) {
        return timestamp;
    }

    public static ProgramSD selectProgram(ProgramSD programSD) {
        if (ProgramSD.COMPUTER_SCIENCE.equals(programSD)) {
            print(programSD.toString());


        } else if (ProgramSD.CYBER_SECURITY.equals(programSD)) {
            print(programSD.toString());


        } else if (ProgramSD.DATA_SCIENCE.equals(programSD)) {
            print(programSD.toString());

        } else {
            System.out.println("you didn't select Programs in our School :( ");
        }
        return programSD;
    }

}
