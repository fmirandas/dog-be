package com.test.mobdev.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DogDTO implements Serializable {

    private static final long serialVersionUID = -7861007524436742197L;
    private String breed;
    private List<String> subBreeds;
    private List<Images> images;
}
