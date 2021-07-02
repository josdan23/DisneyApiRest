package com.jdyapura.api.disney.services;

import com.jdyapura.api.disney.entities.Genre;
import com.jdyapura.api.disney.repositories.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GenreServiceTest {

    GenreRepository repository;
    GenreService genreService;

    List<Genre> genreListInDb;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(GenreRepository.class);
        genreService = new GenreService(repository);

        genreListInDb = new ArrayList<>();

        Genre genre = new Genre("Terror", "image");
        genre.setIdGenre(1);
        genreListInDb.add(genre);

        Genre genre1 = new Genre("Action", "image");
        genre1.setIdGenre(2);
        genreListInDb.add(genre1);
    }

    @Test()
    public void should_throw_exception_when_there_are_no_genre_records(){

        //return list empty
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        assertThrows( RuntimeException.class, () -> {
            genreService.findAllGenres();
        });
    }

    @Test
    public void should_return_list_of_genres_when_exist_records_in_db() throws Exception {

        Mockito.when(repository.findAll()).thenReturn(genreListInDb);

        assertTrue(genreService.findAllGenres().size() > 0);
    }

    @Test
    public void should_return_genre_with_id_2_when_id_2_is_provided() throws Exception {

        int idSearched = 2;

        Genre genreExpected = new Genre("Action", "image");
        genreExpected.setIdGenre(2);

        Optional<Genre> objectReturn = Optional.of(genreExpected);

        Mockito.when(repository.findById(2)).thenReturn(objectReturn);

        // TODO: cambiar excepcion a excepcion no hay registro
        assertTrue( genreService.findGenreById(idSearched).getIdGenre() == idSearched);
    }

    @Test
    public void should_throw_an_exception_when_the_searched_genre_does_not_exist(){

        int idSearched = 2;
        Mockito.when(repository.findById(idSearched)).thenReturn(Optional.empty());

        // TODO: cambiar excepcion a excepcion no hay registro
        assertThrows(RuntimeException.class, () -> {
           genreService.findGenreById(idSearched);
        });
    }

    @Test
    public void should_throw_when_find_genre_with_negative_argument() {
        int idSearched = -2;

        Genre genreExpected = new Genre("Action", "image");
        genreExpected.setIdGenre(2);

        Optional<Genre> objectReturn = Optional.of(genreExpected);

        Mockito.when(repository.findById(2)).thenReturn(objectReturn);

        assertThrows(RuntimeException.class, () -> {
           genreService.findGenreById(idSearched);
        });
    }

    @Test
    public void should_throw_when_find_genre_with_empty_argument_name() {
        String name = "";

        Genre genreExpected = new Genre("Action", "image");
        genreExpected.setIdGenre(2);

        Optional<Genre> objectReturn = Optional.of(genreExpected);

        Mockito.when(repository.findByName(name)).thenReturn(genreExpected);

        assertThrows(RuntimeException.class, () -> {
            genreService.findGenreByName(name);
        });
    }

    @Test
    public void true_cuando_se_registra_un_genero_valido() throws Exception {

        Genre genre = new Genre("Terror", "no image");
        genre.setIdGenre(1);

        Mockito.when(repository.save(genre)).thenReturn(genre);

        Genre genreActual = genreService.saveGenre(genre);
        assertEquals(genre, genreActual);
    }

    @Test
    public void lanzar_excepcion_cuando_se_intenta_registrar_genre_sin_atributos() {

        Genre genre = new Genre();

        Genre genreSaved = new Genre();
        genreSaved.setIdGenre(1);

        Mockito.when(repository.save(genre)).thenReturn(genre);

        assertThrows(RuntimeException.class, () -> {
            genreService.saveGenre(genre);
        });
    }

    @Test
    public void lanzar_excepcion_cuando_el_genero_ya_esta_registrado() {
        Genre genre = new Genre("Terror", "image");
        genre.setIdGenre(1);

        Mockito.when(repository.exists(Example.of(genre))).thenReturn(true);
        Mockito.when(repository.save(genre)).thenReturn(genre);

        assertThrows(RuntimeException.class, () -> {
            genreService.saveGenre(genre);
        });
    }

    @Test
    public void lanzar_excepcion_cuando_se_el_repostorio_devuelve_null_al_guardar_el_nuevo_genero() {

        Genre genre = new Genre("Terror", "image");
        genre.setIdGenre(1);

        Mockito.when(repository.save(genre)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            genreService.saveGenre(genre);
        });

    }

    @Test
    public void lanzar_excepcion_si_el_genero_id_2_no_existe_al_eliminar_del_repo() {
        int idGenre = 2;
        assertThrows( RuntimeException.class, () -> {
            genreService.deleteGenre(2);
        });
    }

    @Test
    public void lanzar_excepcion_si_se_solicita_eliminar_un_genero_con_argumento_id_negativo() {
        int idGenre = -2;

        assertThrows( RuntimeException.class, () -> {
            genreService.deleteGenre(idGenre);
        });
    }


    @Test
    public void actualizar_genre_correctamente() {
        Genre genreStored= new Genre("Terror", "image");
        genreStored.setIdGenre(1);

        Genre genreToUpdate = new Genre("Action", "image");
        genreToUpdate.setIdGenre(1);

        Mockito.when(repository.findById(genreStored.getIdGenre())).thenReturn(Optional.of(genreStored));
        Mockito.when(repository.save(genreStored)).thenReturn(genreToUpdate);

        Genre genreUpdated = genreService.updateGenre(1, genreToUpdate);

        assertSame(genreToUpdate, genreUpdated, "los obtos no son iguales");

    }

    @Test
    public void lanzar_excepcion_si_el_genre_no_existe(){

        assertThrows(RuntimeException.class, () -> {
            genreService.deleteGenre(2);
        });
    }


}