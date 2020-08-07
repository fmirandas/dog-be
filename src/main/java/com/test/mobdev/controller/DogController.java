package com.test.mobdev.controller;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.mobdev.dto.DogDTO;
import com.test.mobdev.exception.BusinessException;
import com.test.mobdev.service.DogService;

import io.swagger.annotations.ApiOperation;

/**
 * Controller for Dog method
 * 
 * @author fmirands
 *
 */
@RestController
@RequestMapping("/dog/")
public class DogController {
    
    private static final Logger LOGGER = LogManager.getLogger(DogController.class);

    @Autowired
    DogService dogService;

    /**
     * Method that allows obtaining information from a dog
     * 
     * @param breedName Breed Name of dog
     * @return dogInfo Dog Information
     * @throws BusinessException custom exception
     */
    @ApiOperation(value = "Find information about a dog, by breed")
    @GetMapping(path = "/{breedName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DogDTO> getProductList(@PathVariable(name = "breedName") String breedName)
            throws BusinessException {

        LOGGER.info("Ingreso para buscar la informacion del perro");

        DogDTO dogInfo = dogService.getDogInfo(breedName);

        if (Objects.nonNull(dogInfo)) {
            return new ResponseEntity<>(dogInfo, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
