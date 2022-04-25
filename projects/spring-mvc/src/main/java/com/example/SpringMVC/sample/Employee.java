package com.example.SpringMVC.sample;


public class Employee {

    private long id;
    private String name;
    private String department;

    public Employee(long id, String name, String department) {
        setId(id);
        setName(name);
        setDepartment(department);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
