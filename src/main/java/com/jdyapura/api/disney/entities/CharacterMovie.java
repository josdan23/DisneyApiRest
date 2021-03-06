package com.jdyapura.api.disney.entities;

import javax.persistence.*;

@Entity
@Table(name = "character_movie")
public class CharacterMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_character_movie")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

    public CharacterMovie(){

    }

    public CharacterMovie(Movie movie, Character character) {
        this.movie = movie;
        this.character = character;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return "CharacterMovie{" +
                "id=" + id +
                ", movie=" + movie +
                ", character=" + character +
                '}';
    }
}
