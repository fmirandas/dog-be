package com.test.mobdev.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.test.mobdev.dto.DogDTO;
import com.test.mobdev.dto.ImagesDTO;
import com.test.mobdev.service.DogService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class DogControllerTest { 
    
    @Mock
    DogService dogService;
    
    @InjectMocks
    DogController dogController;
    
    private final static String BREED_NAME = "terrier";
    
    
    @Test
    public void getProductList_NoContent() throws Exception {
        when(dogService.getDogInfo(BREED_NAME)).thenReturn(null);
        ResponseEntity<DogDTO> response =  dogController.getProductList(BREED_NAME);
        
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    
    @Test
    public void getProductList_OK() throws Exception {
        DogDTO terrier = new DogDTO();
        ImagesDTO imagesDto = new ImagesDTO("https://images.dog.ceo/breeds/terrier-american/n02093428_10164.jpg");
        
        List<String> subBreed = new ArrayList<>();
        subBreed.add("staffordshire");
        
        List<ImagesDTO> imagesList = new ArrayList<>();
  
        imagesList.add(imagesDto);
        terrier.setBreed(BREED_NAME);
        terrier.setSubBreeds(subBreed);
        terrier.setImages(imagesList);
        
        ResponseEntity<DogDTO> expected = new ResponseEntity<>(terrier, HttpStatus.OK);
        when(dogService.getDogInfo(BREED_NAME)).thenReturn(terrier);
        ResponseEntity<DogDTO> response =  dogController.getProductList(BREED_NAME);
        
        assertEquals(expected.getStatusCode(), response.getStatusCode());
        assertEquals(terrier.getBreed(), response.getBody().getBreed());
        assertEquals(terrier.getImages().size(), response.getBody().getImages().size());
        assertEquals(terrier.getSubBreeds().size(), response.getBody().getSubBreeds().size());
 
    }
    
    
    

}
