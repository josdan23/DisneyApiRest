package com.jdyapura.api.disney.controllers.responsedto;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailResponse {
    public Integer id;
    public String title;
    public String creationDate;
    public int qualifiers;
    public String image;
    public String genre;
    public List<String> characters = new ArrayList<>();
}
