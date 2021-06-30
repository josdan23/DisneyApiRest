package com.jdyapura.api.disney.services;

import com.jdyapura.api.disney.entities.Genre;
import com.jdyapura.api.disney.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    private GenreRepository repository;

    public List<Genre> findAllGenres() {
        return repository.findAll();
    }

    public Genre findGenreById(int idGenre) {
        Optional<Genre> result = repository.findById(idGenre);
        if (!result.isPresent())
            return null;
        return result.get();
    }

    public Genre findGenreByName(String genreName) {
        Genre genre = repository.findByName(genreName);
        System.out.println(genre);
        return genre;
    }

    public Genre saveGenre(Genre newGenre) {
        return repository.save(newGenre);
    }

    public Genre updateGenre(int idGenre, Genre updatedGenre) {
        Genre savedGenre = findGenreById(idGenre);
        if(savedGenre == null)
            return null;

        savedGenre.setName(updatedGenre.getName());
        savedGenre.setImage(updatedGenre.getImage());
        return repository.save(savedGenre);
    }

    public void deleteGenre(int idGenre) {
        if (!repository.existsById(idGenre))
            return;
        
        repository.deleteById(idGenre);
    }
    
}
