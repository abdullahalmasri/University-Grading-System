package edu.university.schools.models;

public class Dean {

    private int id;
    private String name;

    public Dean(String name) {

        this.name = name;
    }

    public Dean() {
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

}
