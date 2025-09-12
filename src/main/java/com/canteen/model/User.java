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
    
    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    
    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }
}