package com.test.mobdev.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mobdev.dto.ApiDogResponseDTO;
import com.test.mobdev.dto.DogDTO;
import com.test.mobdev.dto.ImagesDTO;
import com.test.mobdev.exception.BusinessException;
import com.test.mobdev.service.DogApiConnectService;
import com.test.mobdev.service.DogService;
import com.test.mobdev.utils.DogUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class DogServiceImpl implements DogService {

    @Autowired
    DogApiConnectService dogApiConnectService;

    @Override
    public DogDTO getDogInfo(String breedName) throws BusinessException {

        log.debug("Ingreso a para obtener la informacion del perro");

        if (!DogUtils.onlyLetters(breedName)) {
            throw new BusinessException("only letters is allowed");
        }

        DogDTO dogInfo = new DogDTO();
        dogInfo.setBreed(breedName);
        dogInfo.setSubBreeds(getSubBreedsByAll(breedName));
        dogInfo.setImages(getImages(breedName));

        return dogInfo;

    }

    private List<String> getSubBreeds(String breedName) {
        ApiDogResponseDTO subBreeds = dogApiConnectService.getSubBreedByBreedName(breedName);

        return subBreeds.getMessage();
    }

    
    private List<String> getSubBreedsByAll(String breedName) {
        
        List<String> subBreedsList = new ArrayList<>();
        
        Object subBreedsresponse = dogApiConnectService.getAllBreeds();

        String responseApi = subBreedsresponse.toString();
            
        if (responseApi.contains(breedName)) { 
       
            String[] subBreedArray = DogUtils.getSubBreedArray(responseApi, breedName);
            
            for (String breeds:subBreedArray) {
                if (!breeds.isEmpty()) {
                    subBreedsList.add(breeds.trim());
                }
               
            }
            
            return subBreedsList;
                  
        }

        return new ArrayList<>();
    }

    private List<ImagesDTO> getImages(String breedName) {
        ApiDogResponseDTO images = dogApiConnectService.getImagesByBreedName(breedName);

        List<ImagesDTO> imagesDog = images.getMessage().stream().map(url -> new ImagesDTO(url))
                .collect(Collectors.toList());

        return imagesDog;
    }

}
