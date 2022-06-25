package edu.university.schools.models;

import edu.university.schools.Enums.Programs;

import java.sql.Timestamp;

public class EngineeringTechnology extends School implements Semester {
    public EngineeringTechnology(String name, int dean_id) {
        super(name, dean_id);
    }

    public EngineeringTechnology() {
    }

    public static void print(Programs courseName){
        System.out.println("Welcome to Engineering Technology enrolled :" + courseName);
    }

    @Override
    public Timestamp startTime(Timestamp StartDate) {
        return StartDate;
    }

    @Override
    public Timestamp endTime(Timestamp EndDate) {
        return EndDate;
    }
}
