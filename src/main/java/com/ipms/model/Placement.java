package com.ipms.model;

public class Placement {
    private int studentId;
    private int companyId;

    public Placement(int studentId, int companyId) {
        this.studentId = studentId;
        this.companyId = companyId;
    }

    public int getStudentId() { return studentId; }
    public int getCompanyId() { return companyId; }
}