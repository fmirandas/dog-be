package com.test.mobdev.service.impl;

import org.springframework.stereotype.Service;

import com.test.mobdev.dto.DogDTO;
import com.test.mobdev.exception.BusinessException;
import com.test.mobdev.service.DogService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class DogServiceImpl implements DogService {
    
    
    
    
    
    @Override
    public DogDTO getDogInfo(String breedName) throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }
    


}
