package com.jdyapura.api.disney.services;

import com.jdyapura.api.disney.entities.Genre;
import com.jdyapura.api.disney.entities.Movie;
import com.jdyapura.api.disney.entities.TypeMovie;
import com.jdyapura.api.disney.repositories.CharacterMovieRepository;
import com.jdyapura.api.disney.repositories.GenreRepository;
import com.jdyapura.api.disney.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {

    MovieRepository movieRepository;
    GenreRepository genreRepository;
    CharacterMovieRepository characterMovieRepository;

    MovieService service;

    @BeforeEach
    void setUp() {

        movieRepository = Mockito.mock(MovieRepository.class);
        genreRepository = Mockito.mock(GenreRepository.class);
        characterMovieRepository = Mockito.mock(CharacterMovieRepository.class);

        service = new MovieService(
                movieRepository,
                genreRepository,
                characterMovieRepository);
    }

    // casos de prueba para findAllMovies
    @Test
    public void lanzar_excepcion_si_no_hay_movies_registradas() {

        Mockito.when(movieRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(RuntimeException.class, () -> {
           service.findAllMovies();
        });
    }

    @Test
    public void es_verdader_si_el_tamaño_de_la_lista_de_movies_de_retorno_es_mayor_a_cero() {

        Date today = new Date();
        Genre exampleGenre = new Genre("Action", "imageGenre");


        List<Movie> movieList = new ArrayList<>();
        movieList.add(
                new Movie("Cars",
                        new Date(),
                        2,
                        "image",
                        "movie",
                        exampleGenre));

        movieList.add(
                new Movie("Dinos",
                        new Date(),
                        3,
                        "image",
                        "movie",
                        exampleGenre));

        Mockito.when(movieRepository.findAll()).thenReturn(movieList);


        assertTrue(service.findAllMovies().size() > 0);
    }



    //casos de prueba para findMovieById
    @Test
    public void lanzar_excepcion_si_no_se_encuentra_la_movie_id_1 () {

        int idSearch = 1;
        Mockito.when(movieRepository.getById(idSearch)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            service.findMovieById(idSearch);
        });
    }

    @Test
    public void lanzar_excepcion_si_id_no_es_valido() {
        int idSearch = -2;
        Mockito.when(movieRepository.getById(idSearch)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            service.findMovieById(idSearch);
        });
    }

    @Test
    public void es_verdadero_si_regresa_movie_id_2() {
        Movie movieStored = new Movie();
        movieStored.setIdMovie(2);

        int idMovieSearch = 2;

        Mockito.when(movieRepository.findById(2)).thenReturn(Optional.of(movieStored));
        assertTrue(service.findMovieById(idMovieSearch) != null);
    }

    @Test
    public void lanzar_excepcion_si_movie_id_2_no_es_encontrado() {
        Movie movieStored = new Movie();
        movieStored.setIdMovie(2);

        int idMovieSearch = 2;

        Mockito.when(movieRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
           service.findMovieById(idMovieSearch);
        });
    }

    //casos de prueba para saveMovie(movie)

    @Test
    public void es_verdadero_si_movie_id_2_es_registrado() {

        Genre genre = new Genre();
        genre.setIdGenre(1);
        genre.setName("Action");
        genre.setImage("imagen");

        Movie movieToSave = new Movie();
        movieToSave.setTitle("Cars");
        movieToSave.setCalification(2);
        movieToSave.setCreationDate(new Date());
        movieToSave.setImage("imagen");
        movieToSave.setType("Movie");
        movieToSave.setGenre(genre);
        movieToSave.setIdMovie(1);

        Mockito.when(genreRepository.findByName(genre.getName())).thenReturn(genre);
        Mockito.when(movieRepository.save(movieToSave)).thenReturn(movieToSave);

        Movie movieSaved = service.saveMovie(movieToSave);
        assertEquals(movieToSave, movieSaved);

    }

    @Test
    public void lanzar_excepcion_si_argumento_movie_no_es_valido(){
        Movie movieToSave = null;
        assertThrows(RuntimeException.class, () -> {
           service.saveMovie(movieToSave);
        });
    }

    @Test
    public void lanzar_excepcion_si_argumento_movie_no_tiene_atributos_validos() {
        Movie movieToSave = new Movie();
        assertThrows(RuntimeException.class, () -> {
            service.saveMovie(movieToSave);
        });
    }

    //casos de prueba para deleteMovie(idmovie)
    @Test
    public void lanzar_excepcion_si_movie_id_2_no_esta_registrado() {

        assertThrows(RuntimeException.class, () -> {
           service.deleteMovie(2);
        });
    }

    @Test
    public void lanzar_excepcion_al_borrar_movie_si_id_es_negativo(){
        int idMovieToDelete = -1;

        assertThrows(RuntimeException.class, () -> {
           service.deleteMovie(idMovieToDelete);
        });
    }

    //casos de prueba para findCharactersInMovieByIdMovie()
    @Test
    public void lanzar_excepcion_si_id_es_negativo(){
        int idMovieToFindCharacters = -1;

        assertThrows(RuntimeException.class, () -> {
            service.findCharactersInMovieByIdMovie(idMovieToFindCharacters);
        });
    }

    @Test
    public void lanzar_excepcion_si_al_buscar_characters_la_movie_id_2_no_existe() {
        int idMovie = 2;


        assertThrows(RuntimeException.class, () -> {
           service.findCharactersInMovieByIdMovie(idMovie);
        });
    }
}