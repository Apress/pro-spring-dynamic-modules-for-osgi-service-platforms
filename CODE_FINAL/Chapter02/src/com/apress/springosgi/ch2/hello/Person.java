package com.apress.springosgi.ch2.hello;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "FNAME")
    private String firstName;
    @Column(name = "LNAME")
    private String lastName;
    @Column(precision=4, scale=2)
    private double hourlyRate;

    public Person(String firstName, String lastName, double hourlyRate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.hourlyRate = hourlyRate;
    }

    public Person() {
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(long id) {
        this.id = id;
    }
 
    public long getId() {
        return id;
    }
}
