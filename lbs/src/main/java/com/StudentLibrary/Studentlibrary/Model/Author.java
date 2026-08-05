package com.StudentLibrary.Studentlibrary.Model;
import javax.persistence.*;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import javax.validation.Valid;



@Entity
public class Author {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @NotBlank(message = "Author Name is required")
    private String name;

    @Column(unique = true)
    @Email(message = "Invalid Email")
    private String email;

    private int age;
    private String country;


    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Book> books_written;

    public Author(){}

    public Author(String name, String email, int age, String country) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Book> getBooks_written() {
        return books_written;
    }

    public void setBooks_written(List<Book> books_written) {
        this.books_written = books_written;
    }
}
