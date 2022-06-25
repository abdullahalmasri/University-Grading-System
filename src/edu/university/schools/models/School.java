package edu.university.schools.models;

public class School {

    private int id;
    private String name;
    private int dean_id;

    public School() {
    }

    public School(String name, int dean_id) {
        this.name = name;
        this.dean_id = dean_id;
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

    public int getDean_id() {
        return dean_id;
    }

    public void setDean_id(int dean_id) {
        this.dean_id = dean_id;
    }
}
