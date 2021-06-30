package com.jdyapura.api.disney.controllers;

import com.jdyapura.api.disney.controllers.responsedto.CharacterDetailResponse;
import com.jdyapura.api.disney.controllers.responsedto.CharacterResponse;
import com.jdyapura.api.disney.entities.Character;
import com.jdyapura.api.disney.entities.Movie;
import com.jdyapura.api.disney.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class CharacterController {

    @Autowired
    private CharacterService service;

    @GetMapping("/characters")
    public ResponseEntity<List<CharacterResponse>> getAllCharacters(){

        List<CharacterResponse> characterResponseList = new ArrayList<>();

        for (Character allCharacter : service.findAllCharacters()) {

            CharacterResponse characterResponse = new CharacterResponse();
            characterResponse.name = allCharacter.getName();
            characterResponse.imagen = allCharacter.getImage();

            characterResponseList.add(characterResponse);
        }

        return new ResponseEntity<>(characterResponseList, HttpStatus.OK);
    }

    @GetMapping("/characters/{id}")
    public ResponseEntity<CharacterDetailResponse> getCharacter(@PathVariable("id") int idCharacter){

        Character character = service.findCharacterById(idCharacter);
        List<Movie> movieList = service.findMoviesToCharacterByIdCharacter(idCharacter);

        CharacterDetailResponse response = new CharacterDetailResponse();
        response.idCharacter = character.getIdCharacter();
        response.name = character.getName();
        response.age = character.getAge();
        response.weight = character.getWeight();
        response.history = character.getHistory();
        response.image = character.getImage();

        for (Movie movie : movieList) {
            response.movies.add(movie.getTitle());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/movies/{idMovie}/characters")
    public ResponseEntity<Character> postCharacter(
            @PathVariable("idMovie") int idMovie,
            @RequestBody Character character) {

        return new ResponseEntity<>(service.saveCharacter(idMovie, character), HttpStatus.CREATED);
    }

    @PutMapping("/characters/{id}")
    public ResponseEntity<Character> updateCharacter(
            @PathVariable int idCharacter,
            @RequestBody Character updatedCharacter) {
        return new ResponseEntity<>(service.updateCharacter(idCharacter, updatedCharacter), HttpStatus.OK);
    }

    @DeleteMapping("/characters/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable("id") int idCharacter) {
        service.deleteCharacter(idCharacter);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
