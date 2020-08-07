package com.test.mobdev.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

/**
 * Implementation of the Service that contains the logic to obtain the
 * information of the dog
 * 
 * @author fmirands
 *
 */
@Service
public class DogServiceImpl implements DogService {

    private static final Logger LOGGER = LogManager.getLogger(DogServiceImpl.class);
    
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

        LOGGER.info("Ingreso a para obtener la informacion del perro");

        if (!DogUtils.onlyLetters(breedName)) {
            throw new BusinessException("only letters is allowed");
        }

        DogDTO dogInfo = new DogDTO();
        dogInfo.setBreed(breedName);
        dogInfo.setSubBreeds(searchByListAll ? getSubBreedsByAll(breedName) : getSubBreedsByBreedName(breedName));
        dogInfo.setImages(getImages(breedName));

        return dogInfo;

    }

    /**
     * Method that allows obtaining the sub breeds according to the breed of the dog
     * 
     * @param breedName Dog Breed
     * @return List of Sub Breed
     */
    private List<String> getSubBreedsByBreedName(String breedName) {
        
        LOGGER.info("Ingreso al metodo getSubBreedsByBreedName: "+breedName);
        
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
        
        LOGGER.info("Ingreso al metodo getSubBreedsByAll()");

        String responseApi = dogApiConnectService.getAllBreeds();

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
     * @param breedName Dog Breed
     * @return imagesList
     */
    private List<ImagesDTO> getImages(String breedName) {
        
        LOGGER.info("Ingreso al metodo getImages: "+breedName);
        
        ApiDogResponseDTO images = dogApiConnectService.getImagesByBreedName(breedName);

        List<ImagesDTO> imagesDog = images.getMessage().stream().map(url -> new ImagesDTO(url))
                .collect(Collectors.toList());

        return imagesDog;
    }

}
