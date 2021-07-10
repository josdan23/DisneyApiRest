package com.jdyapura.api.disney.controllers.responsedto;

import com.jdyapura.api.disney.entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailResponse {
    private Integer id;
    private String title;
    private String creationDate;
    private int calification;
    private String image;
    private String genre;
    private String type;
    private List<String> characters = new ArrayList<>();

    public MovieDetailResponse(Movie movie) {
        this.id = movie.getIdMovie();
        this.title = movie.getTitle();
        this.creationDate = movie.getCreationDate().toString();
        this.calification = movie.getCalification();
        this.image = movie.getImage();
        this.genre = movie.getGenre().getName();
        this.type = movie.getType().toUpperCase();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getCalification() {
        return calification;
    }

    public void setCalification(int calification) {
        this.calification = calification;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }
}
