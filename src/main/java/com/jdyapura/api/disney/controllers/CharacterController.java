package com.jdyapura.api.disney.controllers;

import com.jdyapura.api.disney.models.Character;
import com.jdyapura.api.disney.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterService service;

    @GetMapping
    public ResponseEntity<List<Character>> getAllCharacters(){
        return new ResponseEntity<>(service.findAllCharacters(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacter(@PathVariable("id") int idCharacter){
        return new ResponseEntity<>(service.findCharacterById(idCharacter), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Character> postCharacter(@RequestBody Character character){
        return new ResponseEntity<>(service.saveCharacter(character), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(
            @PathVariable int idCharacter,
            @RequestBody Character updatedCharacter) {
        return new ResponseEntity<>(service.updateCharacter(idCharacter, updatedCharacter), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable("id") int idCharacter) {
        service.deleteCharacter(idCharacter);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
