package com.jdyapura.api.disney.controllers.responsedto;

import java.util.ArrayList;
import java.util.List;

public class CharacterDetailResponse {

    public Integer idCharacter;
    public String name;
    public int age;
    public double weight;
    public String history;
    public String image;
    public List<String> movies = new ArrayList<>();
}
