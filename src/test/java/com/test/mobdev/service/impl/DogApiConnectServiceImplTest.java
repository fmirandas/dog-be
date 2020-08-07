package com.test.mobdev.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.test.mobdev.dto.ApiDogResponseDTO;
import com.test.mobdev.exception.BusinessException;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class DogApiConnectServiceImplTest {

    @InjectMocks
    DogApiConnectServiceImpl dogApiConnectService;

    @Mock
    RestTemplate restTemplate;

    private String url = "http://localhost";

    private String subBreed = "/api/breed/{subBreed}/list";

    private String images = "/api/breed/{subBreed}/images";

    private String list = "/api/breeds/list/all";

    private String breedName = "bulldog";

    @BeforeEach
    public void initialData() {
        ReflectionTestUtils.setField(dogApiConnectService, "url", url);
        ReflectionTestUtils.setField(dogApiConnectService, "subBreed", subBreed);
        ReflectionTestUtils.setField(dogApiConnectService, "images", images);
        ReflectionTestUtils.setField(dogApiConnectService, "list", list);
    }

    @Test
    public void getSubBreedByBreedName_OK() {

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).path(subBreed).buildAndExpand(breedName);

        ApiDogResponseDTO responseExpected = new ApiDogResponseDTO();
        responseExpected.setStatus("OK");
        List<String> subBreeds = new ArrayList<>();
        subBreeds.add("boston");
        subBreeds.add("english");
        subBreeds.add("french");
        responseExpected.setMessage(subBreeds);

        ResponseEntity<ApiDogResponseDTO> expected = new ResponseEntity<>(responseExpected, HttpStatus.OK);

        when(restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, null, ApiDogResponseDTO.class))
                .thenReturn(expected);

        ApiDogResponseDTO response = dogApiConnectService.getSubBreedByBreedName(breedName);

        assertNotNull(response);
        assertEquals(responseExpected.getStatus(), response.getStatus());
        assertEquals(responseExpected.getMessage().size(), response.getMessage().size());
        assertEquals(responseExpected.getMessage().get(0), response.getMessage().get(0));

    }

    @Test
    public void getSubBreedByBreedName_Exception1() {

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).path(subBreed).buildAndExpand(breedName);

        HttpClientErrorException restException = new HttpClientErrorException(HttpStatus.NOT_FOUND);

        when(restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, null, ApiDogResponseDTO.class))
                .thenThrow(restException);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            dogApiConnectService.getSubBreedByBreedName(breedName);
        });

        String expectedMessage = "Breed not found: " + breedName;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void getSubBreedByBreedName_Exception2() {

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).path(subBreed).buildAndExpand(breedName);

        HttpClientErrorException restException = new HttpClientErrorException(HttpStatus.NO_CONTENT);

        when(restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, null, ApiDogResponseDTO.class))
                .thenThrow(restException);

        assertThrows(HttpClientErrorException.class, () -> {
            dogApiConnectService.getSubBreedByBreedName(breedName);
        });

    }

    @Test
    public void getImagesByBreedName_OK() {

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).path(images).buildAndExpand(breedName);

        ApiDogResponseDTO responseExpected = new ApiDogResponseDTO();
        responseExpected.setStatus("OK");
        List<String> images = new ArrayList<>();
        images.add("https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg");
        images.add("https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg");
        images.add("https://images.dog.ceo/breeds/hound-afghan/n02088094_1023.jpg");
        responseExpected.setMessage(images);

        ResponseEntity<ApiDogResponseDTO> expected = new ResponseEntity<>(responseExpected, HttpStatus.OK);

        when(restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, null, ApiDogResponseDTO.class))
                .thenReturn(expected);

        ApiDogResponseDTO response = dogApiConnectService.getImagesByBreedName(breedName);

        assertNotNull(response);
        assertEquals(responseExpected.getStatus(), response.getStatus());
        assertEquals(responseExpected.getMessage().size(), response.getMessage().size());
        assertEquals(responseExpected.getMessage().get(0), response.getMessage().get(0));

    }

    @Test
    public void getImagesByBreedName_Exception1() {

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).path(images).buildAndExpand(breedName);

        HttpClientErrorException restException = new HttpClientErrorException(HttpStatus.NOT_FOUND);

        when(restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, null, ApiDogResponseDTO.class))
                .thenThrow(restException);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            dogApiConnectService.getImagesByBreedName(breedName);
        });

        String expectedMessage = "Images not found: " + breedName;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void getImagesByBreedName_Exception2() {

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).path(images).buildAndExpand(breedName);

        HttpClientErrorException restException = new HttpClientErrorException(HttpStatus.NO_CONTENT);

        when(restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, null, ApiDogResponseDTO.class))
                .thenThrow(restException);

        assertThrows(HttpClientErrorException.class, () -> {
            dogApiConnectService.getImagesByBreedName(breedName);
        });

    }
    
    
    @Test
    public void getAllBreeds_OK() {

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).path(list).build();
        
        Object object = new Object();

        ResponseEntity<Object> expected = new ResponseEntity<>(object, HttpStatus.OK);

        when(restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, null, Object.class))
                .thenReturn(expected);

        Object response = dogApiConnectService.getAllBreeds();

        assertNotNull(response);

    }
    
    
    @Test
    public void getAllBreeds_Exception() {

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).path(list).build();

        HttpClientErrorException restException = new HttpClientErrorException(HttpStatus.NOT_FOUND);

        when(restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, null, Object.class))
                .thenThrow(restException);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            dogApiConnectService.getAllBreeds();
        });

        String expectedMessage = "Error List All Breeds";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

}
