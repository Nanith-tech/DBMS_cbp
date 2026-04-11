package com.ipms.model;

public class User {
    private int id;
    private Integer studentId;
    private String username, password, role;

    public User(String username, String password, String role) {
        this(0, username, password, role, null);
    }

    public User(int id, String username, String password, String role, Integer studentId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.studentId = studentId;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public Integer getStudentId() { return studentId; }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }

    public boolean isStudent() {
        return "STUDENT".equalsIgnoreCase(role);
    }
}
