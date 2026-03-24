package com.ipms.model;

public class Company {
    private String name, role;
    private int pkg;

    public Company(String name, String role, int pkg) {
        this.name = name;
        this.role = role;
        this.pkg = pkg;
    }

    public String getName() { return name; }
    public String getRole() { return role; }
    public int getPkg() { return pkg; }
}