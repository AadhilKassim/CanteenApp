package com.canteen.model;

public class User {
    private String email;
    private String password;
    private String name;
    private String department;
    private String semester;
    private boolean isAdmin;

    public User(String email, String password, String name, String department, String semester, boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.department = department;
        this.semester = semester;
        this.isAdmin = isAdmin;
    }

    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getSemester() { return semester; }
    public boolean isAdmin() { return isAdmin; }
}