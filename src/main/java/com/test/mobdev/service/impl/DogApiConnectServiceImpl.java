package com.test.mobdev.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.test.mobdev.dto.ApiDogResponseDTO;
import com.test.mobdev.exception.BusinessException;
import com.test.mobdev.service.DogApiConnectService;

import lombok.extern.log4j.Log4j2;

/**
 * Implementation of the Service that connects to the dog API
 * 
 * @author fmirands
 *
 */
@Service
@Log4j2
public class DogApiConnectServiceImpl implements DogApiConnectService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${server.dog.api}")
    private String url;

    @Value("${dog.api.sub-breed}")
    private String subBreed;

    @Value("${dog.api.images}")
    private String images;

    @Value("${dog.api.list}")
    private String list;

    /**
     * Method that obtains the subBreed by Breed Name
     * 
     * @param breedName Dog Breed
     * @return Api response Object
     * @throws BusinessException custom exception
     */
    @Override
    public ApiDogResponseDTO getSubBreedByBreedName(String breedName) throws BusinessException {

        try {
            UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).path(subBreed)
                    .buildAndExpand(breedName);

            ResponseEntity<ApiDogResponseDTO> response = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET,
                    null, ApiDogResponseDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException restException) {
            log.error(restException);
            if (restException.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new BusinessException("Breed not found: " + breedName);
            } else {
                throw restException;
            }
        }

    }

    /**
     * Method that obtains the images of dog
     * 
     * @param breedName Dog Breed
     * @return Api response Object
     * @throws BusinessException custom exception
     */
    @Override
    public ApiDogResponseDTO getImagesByBreedName(String breedName) throws BusinessException {

        try {
            UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).path(images).buildAndExpand(breedName);

            ResponseEntity<ApiDogResponseDTO> response = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET,
                    null, ApiDogResponseDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException restException) {
            log.error(restException);
            if (restException.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new BusinessException("Images not found: " + breedName);
            } else {
                throw restException;
            }
        }

    }

    /**
     * Get All Breeds
     * 
     * @param breedName Dog Breed
     * @return Api response Object
     * @throws BusinessException custom exception
     */
    @Override
    public String getAllBreeds() throws BusinessException {

        try {
            UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).path(list).build();

            ResponseEntity<Object> response = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, null,
                    Object.class);
            return response.getBody().toString();
        } catch (Exception restException) {
            log.error(restException);
            throw new BusinessException("Error List All Breeds");
        }

    }

}
