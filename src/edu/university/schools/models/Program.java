package edu.university.schools.models;

public class Program {

    private int programId;
    private String programName;
    private int programCreditHourFees;
    private String headDep;

    public Program() {
    }

    public Program(int programId, String programName, int programCreditHourFees, String headDep) {
        this.programId = programId;
        this.programName = programName;
        this.programCreditHourFees = programCreditHourFees;
        this.headDep = headDep;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getProgramCreditHourFees() {
        return programCreditHourFees;
    }

    public void setProgramCreditHourFees(int programCreditHourFees) {
        this.programCreditHourFees = programCreditHourFees;
    }

    public String getHeadDep() {
        return headDep;
    }

    public void setHeadDep(String headDep) {
        this.headDep = headDep;
    }
}
