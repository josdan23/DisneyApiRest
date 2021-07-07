package com.jdyapura.api.disney.services;

import com.jdyapura.api.disney.entities.Genre;
import com.jdyapura.api.disney.repositories.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private GenreRepository repository;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public GenreService(GenreRepository repository){
        this.repository = repository;
    }


    public List<Genre> findAllGenres() throws RuntimeException {

        List<Genre> allGenresSavedList = repository.findAll();

        if (allGenresSavedList.size() == 0 ){
            log.info("No hay generos registrados en el repositorio");
            throw new RuntimeException("There are no registred genres");
        }

        return allGenresSavedList;
    }

    public Genre findGenreById(int idGenre) throws RuntimeException {

        if ( idGenre <= 0){
            log.info("El parametro recibido es invalido");
            throw new RuntimeException("Parametro incorrecto");
        }

        Optional<Genre> result = repository.findById(idGenre);

        if (!result.isPresent())
            throw new RuntimeException("El genero solicitado no existe");

        return result.get();
    }

    public Genre findGenreByName(String genreName) throws RuntimeException {

        if (genreName.isEmpty() || genreName.isBlank() || genreName == null)
            throw new RuntimeException("Argumento invalido");

        // TODO: debería devolver un optional por si no existe
        Genre genre = repository.findByName(genreName);

        if ( genre == null ){
            log.info("El geneor buscado no existe");
            throw new RuntimeException("El genero buscado no existe");
        }

        return genre;
    }

    public Genre saveGenre(Genre newGenre) throws RuntimeException {

        if (newGenre == null
                || newGenre.getName().isEmpty()
                || newGenre.getName().isBlank()
                || newGenre.getName() == null ) {

            log.info("El genero a guardar tiene argumentos no validos");
            throw new RuntimeException("Argumento invalido");
        }

        if (repository.exists(Example.of(newGenre))){
            log.info("El genero a guardar ya existe en el repositorio");
            throw new RuntimeException("El genero a guardar ya existe");
        }

        Genre genreSaved = repository.save(newGenre);

        if(  genreSaved == null){
            log.info("No se guardo el nuevo genero");
            throw new RuntimeException("No se guardo el nuevo genero");
        }

        return genreSaved;
    }

    public Genre updateGenre(int idGenre, Genre newGenreUpdate) {

        Genre oldGenre = null;

        try {
            oldGenre = findGenreById(idGenre);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(oldGenre == null)
            return null;

        oldGenre.setName(newGenreUpdate.getName());
        if (newGenreUpdate.getImage() != null)
            oldGenre.setImage(newGenreUpdate.getImage());
        return repository.save(oldGenre);
    }

    public void deleteGenre(int idGenre) throws RuntimeException {

        if (idGenre < 0) {
            log.info("El argumento es invalido");
            throw new RuntimeException("El argumento es invalido");
        }

        if (!repository.existsById(idGenre)){
            log.info("El genero ha elimnar no existe");
            throw new RuntimeException("El genero no existe");
        }

        repository.deleteById(idGenre);
    }

}
