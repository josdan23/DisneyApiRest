package com.jdyapura.api.disney.services;

import com.jdyapura.api.disney.models.Character;
import com.jdyapura.api.disney.repositories.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository repository;

    public List<Character> findAllCharacters() {
        return repository.findAll();
    }

    public Character findCharacterById(int idCharacter) {
        Optional<Character> result = repository.findById(idCharacter);
        if (!result.isPresent())
            return null;
        return result.get();
    }

    public Character saveCharacter(Character newCharacter) {
        //TODO: validar si existe
        return repository.save(newCharacter);
    }

    public void deleteCharacter(int idCharater) {
        if (!repository.existsById(idCharater))
            return;

        repository.deleteById(idCharater);
    }

    public Character updateCharacter(int idCharacter, Character newCharacter) {
        Character savedCharacter = findCharacterById(idCharacter);
        if( savedCharacter == null)
            return null;
        return repository.save(newCharacter);
    }
}
