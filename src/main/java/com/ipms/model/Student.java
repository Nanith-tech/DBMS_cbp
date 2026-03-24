package com.ipms.model;

public class Student {
    private String name, email, branch;

    public Student(String name, String email, String branch) {
        this.name = name;
        this.email = email;
        this.branch = branch;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getBranch() { return branch; }
}