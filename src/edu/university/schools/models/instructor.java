package edu.university.schools.models;

public class instructor implements InstructorWork {

    private int id;
    private String name;
    private int salary;
    private int school_id;

    public instructor() {
    }

    public instructor(String name, int salary, int school_id) {
        this.name = name;
        this.salary = salary;
        this.school_id = school_id;
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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    @Override
    public void wightExam(int selectMethod) {
        if(selectMethod==1){

        }else if(selectMethod==2){

        }else {
            System.out.println("wrong entry");
        }
        final int max = 5;
    }

    @Override
    public void midExam() {

    }

    @Override
    public void finalExam() {

    }

    @Override
    public int firstExam() {
        return 0;
    }
}
