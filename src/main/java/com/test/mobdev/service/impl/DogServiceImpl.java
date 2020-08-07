package com.test.mobdev.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.test.mobdev.dto.ApiDogResponseDTO;
import com.test.mobdev.dto.DogDTO;
import com.test.mobdev.dto.ImagesDTO;
import com.test.mobdev.exception.BusinessException;
import com.test.mobdev.service.DogApiConnectService;
import com.test.mobdev.service.DogService;
import com.test.mobdev.utils.DogUtils;

import lombok.extern.log4j.Log4j2;

/**
 * Implementation of the Service that contains the logic to obtain the
 * information of the dog
 * 
 * @author fmirands
 *
 */
@Log4j2
@Service
public class DogServiceImpl implements DogService {

    @Autowired
    DogApiConnectService dogApiConnectService;

    @Value("${dog.sub-breed.byList}")
    private boolean searchByListAll;

    /**
     * Method that allows to search the information of a dog
     * 
     * @param breedName Breed Name
     * @return DogDTO Dog Information
     * @throws BusinessException custom exception
     */
    @Override
    @Cacheable("dogs")
    public DogDTO getDogInfo(String breedName) throws BusinessException {

        log.debug("Ingreso a para obtener la informacion del perro");

        if (!DogUtils.onlyLetters(breedName)) {
            throw new BusinessException("only letters is allowed");
        }

        DogDTO dogInfo = new DogDTO();
        dogInfo.setBreed(breedName);
        dogInfo.setSubBreeds(searchByListAll ? getSubBreedsByAll(breedName) : getSubBreeds(breedName));
        dogInfo.setImages(getImages(breedName));

        return dogInfo;

    }

    /**
     * Method that allows obtaining the sub breeds according to the breed of the dog
     * 
     * @param breedName Dog Breed
     * @return List of Sub Breed
     */
    private List<String> getSubBreeds(String breedName) {
        ApiDogResponseDTO subBreeds = dogApiConnectService.getSubBreedByBreedName(breedName);

        return subBreeds.getMessage();
    }

    /**
     * Method that allows to search the sub Breeds, using the endpoint List All
     * 
     * @param breedName Dog Breed
     * @return List of Sub Breed
     */
    private List<String> getSubBreedsByAll(String breedName) {

        Object subBreedsresponse = dogApiConnectService.getAllBreeds();

        String responseApi = subBreedsresponse.toString();

        if (responseApi.contains(breedName)) {

            List<String> subBreedsList = new ArrayList<>();

            String[] subBreedArray = DogUtils.getSubBreedArray(responseApi, breedName);

            for (String breeds : subBreedArray) {
                if (!breeds.isEmpty()) {
                    subBreedsList.add(breeds.trim());
                }

            }

            return subBreedsList;

        }

        return new ArrayList<>();
    }

    /**
     * Method that allows obtaining images of dogs
     * 
     * @param breedName
     * @return
     */
    private List<ImagesDTO> getImages(String breedName) {
        ApiDogResponseDTO images = dogApiConnectService.getImagesByBreedName(breedName);

        List<ImagesDTO> imagesDog = images.getMessage().stream().map(url -> new ImagesDTO(url))
                .collect(Collectors.toList());

        return imagesDog;
    }

}
