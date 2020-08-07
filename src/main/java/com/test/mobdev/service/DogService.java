package com.test.mobdev.service;

import com.test.mobdev.dto.DogDTO;
import com.test.mobdev.exception.BusinessException;

/**
 * Service that contains the logic to obtain the information of the dog
 * @author fmirands
 *
 */
public interface DogService {
    
    /**
     * Method that allows to search the information of a dog
     * @param breedName Dog Breed
     * @return DogDTO Dog Information
     * @throws BusinessException 
     */
    DogDTO getDogInfo (String breedName) throws BusinessException;

}
