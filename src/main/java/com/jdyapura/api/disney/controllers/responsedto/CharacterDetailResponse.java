package com.jdyapura.api.disney.controllers.responsedto;

import com.jdyapura.api.disney.entities.Character;
import com.jdyapura.api.disney.entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class CharacterDetailResponse {

    private Integer idCharacter;
    private String name;
    private int age;
    private double weight;
    private String history;
    private String image;
    private List<String> movies = new ArrayList<>();


    public CharacterDetailResponse(Character character, List<Movie> movieList) {
        this.idCharacter = character.getIdCharacter();
        this.name = character.getName();
        this.age = character.getAge();
        this.weight = character.getWeight();
        this.history = character.getHistory();
        this.image = character.getImage();


        movieList.forEach( m -> {
            movies.add(m.getTitle());
        });
    }

    public Integer getIdCharacter() {
        return idCharacter;
    }

    public void setIdCharacter(Integer idCharacter) {
        this.idCharacter = idCharacter;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getMovies() {
        return movies;
    }

    public void setMovies(List<String> movies) {
        this.movies = movies;
    }
}
