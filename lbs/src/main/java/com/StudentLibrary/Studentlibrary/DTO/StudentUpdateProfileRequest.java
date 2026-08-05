package com.StudentLibrary.Studentlibrary.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;



public class StudentUpdateProfileRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 5, message = "Minimum age is 5")
    private int age;

    @NotBlank(message = "Country is required")
    private String country;


    public StudentUpdateProfileRequest() {
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }


    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }
}
