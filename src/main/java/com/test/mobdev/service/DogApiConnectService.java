package com.test.mobdev.service;

import com.test.mobdev.dto.ApiDogResponseDTO;
import com.test.mobdev.exception.BusinessException;

public interface DogApiConnectService {

    
    ApiDogResponseDTO getSubBreedByBreedName (String breedName) throws BusinessException;
    
    ApiDogResponseDTO getImagesByBreedName (String breedName) throws BusinessException;
    
    Object getAllBreeds () throws BusinessException;
    
    
}
