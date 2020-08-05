package com.test.mobdev.service;

import com.test.mobdev.dto.DogDTO;
import com.test.mobdev.exception.BusinessException;

public interface DogService {
    
    /**
     * 
     * @param breedName Breed Name
     * @return DogDTO Dog Information
     * @throws BusinessException
     */
    DogDTO getDogInfo (String breedName) throws BusinessException;

}
