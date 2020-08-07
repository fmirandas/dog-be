package com.test.mobdev.service;

import com.test.mobdev.dto.ApiDogResponseDTO;
import com.test.mobdev.exception.BusinessException;

/**
 * Service that connects to the dog API 
 * @author fmirands
 *
 */
public interface DogApiConnectService {

    /**
     * Method that obtains the subBreed by Breed Name
     * @param breedName Dog Breed
     * @return Api response Object
     * @throws BusinessException custom exception
     */
    ApiDogResponseDTO getSubBreedByBreedName (String breedName) throws BusinessException;
    
    /**
     *  Method that obtains the images of dog
     * @param breedName Dog Breed
     * @return Api response Object
     * @throws BusinessException custom exception
     */
    ApiDogResponseDTO getImagesByBreedName (String breedName) throws BusinessException;
    
    /**
     * Get All Breeds
     * @param breedName Dog Breed
     * @return Api response Object
     * @throws BusinessException custom exception
     */
    Object getAllBreeds () throws BusinessException;
    
    
}
