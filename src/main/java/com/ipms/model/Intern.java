package com.ipms.model;

public class Intern {
    private String name, email, branch, companyName, internshipRole, status;
    private int stipend;

    public Intern(String name, String email, String branch, String companyName, String internshipRole, int stipend, String status) {
        this.name = name;
        this.email = email;
        this.branch = branch;
        this.companyName = companyName;
        this.internshipRole = internshipRole;
        this.stipend = stipend;
        this.status = status;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getBranch() { return branch; }
    public String getCompanyName() { return companyName; }
    public String getInternshipRole() { return internshipRole; }
    public int getStipend() { return stipend; }
    public String getStatus() { return status; }
}
