package com.jdyapura.api.disney.controllers.responsedto;

import com.jdyapura.api.disney.entities.Movie;

public class MovieResponse {
    private int id;
    private String title;
    private String image;
    private String creationDate;

    public MovieResponse(Movie movie) {
        this.id = movie.getIdMovie();
        this.title = movie.getTitle();
        this.image = movie.getImage();
        this.creationDate = movie.getCreationDate().toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
