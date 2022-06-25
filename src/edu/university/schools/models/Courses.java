package edu.university.schools.models;

public class Courses {

    private int id;
    private String name;
    private int student_id;
    private int program_id;

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getProgram_id() {
        return program_id;
    }

    public void setProgram_id(int program_id) {
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

    public int getCreditHour() {
        return creditHour;
    }

    public void setCreditHour(int creditHour) {
        this.creditHour = creditHour;
    }

    private int creditHour;

    public Courses() {
    }


    public Courses(int id, String name, int creditHour) {
        this.id = id;
        this.name = name;
        this.creditHour = creditHour;
    }
}
