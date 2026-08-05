package com.StudentLibrary.Studentlibrary.Model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Email(message = "Invalid Email")
    @Column(unique = true)
    private String emailId;

    @NotBlank(message = "Student Name is required")
    private String name;

    //Future scope adult books filter
    @Min(value = 5, message = "Minimum age is 5")
    @Max(value = 100, message = "Maximum age is 100")
    private int age;
    private String country;


    @OneToOne
    @JoinColumn
    private Card card;


    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    public Student(String emailId, String name, int age, String country) {
        this.emailId = emailId;
        this.name = name;
        this.age = age;
        this.country = country;
    }

    public Student(){

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
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

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
