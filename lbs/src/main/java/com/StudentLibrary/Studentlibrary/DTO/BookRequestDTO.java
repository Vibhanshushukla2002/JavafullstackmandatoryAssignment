package com.StudentLibrary.Studentlibrary.DTO;



import com.StudentLibrary.Studentlibrary.Model.Genre;


public class BookRequestDTO {


    private String name;
    private String authorName;
    private Genre genre;

    public BookRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

}
