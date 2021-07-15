package com.jdyapura.api.disney.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdyapura.api.disney.controllers.responsedto.CharacterDetailResponse;
import com.jdyapura.api.disney.controllers.responsedto.CharacterResponse;
import com.jdyapura.api.disney.entities.Character;
import com.jdyapura.api.disney.entities.Movie;
import com.jdyapura.api.disney.services.CharacterService;
import com.jdyapura.api.disney.util.ImageLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class CharacterController {


    private final String PATH_IMAGES = "./src/main/resources/static/";

    @Autowired
    private CharacterService service;

    @GetMapping("/characters")
    public ResponseEntity<?> getAllCharacters(){

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

        CharacterDetailResponse response = new CharacterDetailResponse(character, movieList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/movies/{idMovie}/characters")
    public ResponseEntity<?> postCharacter(
            @PathVariable("idMovie") int idMovie,
            @RequestParam String data,
            @RequestParam(required = false) MultipartFile imageFile) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            Character characterToSave = mapper.readValue(data, Character.class);

            characterToSave.setImage(ImageLoader.getStringPathOfImageUpload(imageFile));

            return new ResponseEntity<>(service.saveCharacter(idMovie, characterToSave ), HttpStatus.CREATED);

        }  catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
        }
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
