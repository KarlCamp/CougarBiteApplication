package com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels;

/**
 * The Employee class models the employee model detailed in Firebase.
 * In other words, the class is reflective of the JSON object stored in the
 * database and properly parses object values by having identical parameter names.
 * Stores basic information regarding an employee.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class Employee {
    private String name; //stores employee name
    private String password; //stores employee password (based on crf_email password)

    //generated appropriate setters and getters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //generated appropriate constructor initializing all class variables

    public Employee(String name, String password, String phone) {
        this.name = name;
        this.password = password;
    }

    //generated appropriate default constructor

    public Employee() {}
}