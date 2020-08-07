package com.test.mobdev.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.test.mobdev.dto.ApiDogResponseDTO;
import com.test.mobdev.dto.DogDTO;
import com.test.mobdev.dto.ImagesDTO;
import com.test.mobdev.exception.BusinessException;
import com.test.mobdev.service.DogApiConnectService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class DogServiceImplTest {

    @InjectMocks
    DogServiceImpl dogService;

    @Mock
    DogApiConnectService dogApiConnectService;

    private static final String LIST_ALL = "{message={australian=[shepherd], "
            + "mastiff=[bull, english, tibetan], african=[]}, status=success}";

    @Test
    public void getDogInfo_OnlyLetters() {

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            dogService.getDogInfo("12321");
        });

        String expectedMessage = "only letters is allowed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getDogInfo_SearchAll() {

        String breedName = "australian";

        List<String> images = new ArrayList<>();
        images.add("https://images.dog.ceo/breeds/australian-shepherd/leroy.jpg");
        images.add("https://images.dog.ceo/breeds/australian-shepherd/pepper.jpg");
        images.add("https://images.dog.ceo/breeds/australian-shepherd/pepper2.jpg");
        images.add("https://images.dog.ceo/breeds/australian-shepherd/sadie.jpg");

        ApiDogResponseDTO responseExpected = new ApiDogResponseDTO();
        responseExpected.setStatus("OK");
        responseExpected.setMessage(images);

        List<String> australianSubBreed = new ArrayList<>();
        australianSubBreed.add("shepherd");

        List<ImagesDTO> imagesDTOList = new ArrayList<>();
        ImagesDTO firstImagen = new ImagesDTO("https://images.dog.ceo/breeds/australian-shepherd/leroy.jpg");
        ImagesDTO secondImagen = new ImagesDTO("https://images.dog.ceo/breeds/australian-shepherd/pepper.jpg");
        ImagesDTO thirdImagen = new ImagesDTO("https://images.dog.ceo/breeds/australian-shepherd/pepper2.jpg");
        ImagesDTO fourthImagen = new ImagesDTO("https://images.dog.ceo/breeds/australian-shepherd/sadie.jpg");
        imagesDTOList.add(firstImagen);
        imagesDTOList.add(secondImagen);
        imagesDTOList.add(thirdImagen);
        imagesDTOList.add(fourthImagen);

        DogDTO australianInfo = new DogDTO();
        australianInfo.setBreed(breedName);
        australianInfo.setSubBreeds(australianSubBreed);
        australianInfo.setImages(imagesDTOList);

        ReflectionTestUtils.setField(dogService, "searchByListAll", true);

        when(dogApiConnectService.getAllBreeds()).thenReturn(LIST_ALL);

        when(dogApiConnectService.getImagesByBreedName(breedName)).thenReturn(responseExpected);

        DogDTO response = dogService.getDogInfo(breedName);

        assertNotNull(response);
        assertEquals(australianInfo.getBreed(), response.getBreed());
        assertEquals(australianInfo.getImages().size(), response.getImages().size());
        assertEquals(australianInfo.getImages().get(3), response.getImages().get(3));
        assertEquals(australianInfo.getSubBreeds().size(), response.getSubBreeds().size());
        assertEquals(australianInfo.getSubBreeds().get(0), response.getSubBreeds().get(0));

    }

    @Test
    public void getDogInfo_SearchAll_BreedEmpty() {

        String breedName = "african";

        List<String> images = new ArrayList<>();
        images.add("https://images.dog.ceo/breeds/african/n02116738_10024.jpg");
        images.add("https://images.dog.ceo/breeds/african/n02116738_10038.jpg");

        ApiDogResponseDTO responseExpected = new ApiDogResponseDTO();
        responseExpected.setStatus("OK");
        responseExpected.setMessage(images);

        List<String> africanSubBreed = new ArrayList<>();

        List<ImagesDTO> imagesDTOList = new ArrayList<>();
        ImagesDTO firstImagen = new ImagesDTO("https://images.dog.ceo/breeds/african/n02116738_10024.jpg");
        ImagesDTO secondImagen = new ImagesDTO("https://images.dog.ceo/breeds/african/n02116738_10038.jpg");

        imagesDTOList.add(firstImagen);
        imagesDTOList.add(secondImagen);

        DogDTO africanInfo = new DogDTO();
        africanInfo.setBreed(breedName);
        africanInfo.setSubBreeds(africanSubBreed);
        africanInfo.setImages(imagesDTOList);

        ReflectionTestUtils.setField(dogService, "searchByListAll", true);

        when(dogApiConnectService.getAllBreeds()).thenReturn(LIST_ALL);

        when(dogApiConnectService.getImagesByBreedName(breedName)).thenReturn(responseExpected);

        DogDTO response = dogService.getDogInfo(breedName);

        assertNotNull(response);
        assertEquals(africanInfo.getBreed(), response.getBreed());
        assertEquals(africanInfo.getImages().size(), response.getImages().size());
        assertEquals(africanInfo.getImages().get(1), response.getImages().get(1));
        assertEquals(africanInfo.getSubBreeds(), response.getSubBreeds());
        assertTrue(response.getSubBreeds().isEmpty());

    }

    @Test
    public void getDogInfo_SearchAll_NotFound() {

        String breedName = "newbreed";

        ApiDogResponseDTO responseExpected = new ApiDogResponseDTO();
        responseExpected.setStatus("OK");
        responseExpected.setMessage(new ArrayList<>());

        DogDTO newbreed = new DogDTO();
        newbreed.setBreed(breedName);
        newbreed.setSubBreeds(new ArrayList<>());
        newbreed.setImages(new ArrayList<>());

        ReflectionTestUtils.setField(dogService, "searchByListAll", true);

        when(dogApiConnectService.getAllBreeds()).thenReturn("test");

        when(dogApiConnectService.getImagesByBreedName(breedName)).thenReturn(responseExpected);

        DogDTO response = dogService.getDogInfo(breedName);

        assertNotNull(response);
        assertEquals(newbreed.getBreed(), response.getBreed());
        assertEquals(newbreed.getImages(), response.getImages());
        assertTrue(response.getImages().isEmpty());
        assertEquals(newbreed.getSubBreeds(), response.getSubBreeds());
        assertTrue(response.getSubBreeds().isEmpty());

    }

    @Test
    public void getDogInfo_SearchByBreedName_Ok() {

        ReflectionTestUtils.setField(dogService, "searchByListAll", false);

        String breedName = "mastiff";

        List<String> images = new ArrayList<>();
        images.add("https://images.dog.ceo/breeds/mastiff-bull/n02108422_1013.jpg");
        images.add("https://images.dog.ceo/breeds/mastiff-bull/n02108422_1016.jpg");

        ApiDogResponseDTO responseExpected = new ApiDogResponseDTO();
        responseExpected.setStatus("OK");
        responseExpected.setMessage(images);

        List<String> mastiffSubBreed = new ArrayList<>();
        mastiffSubBreed.add("bull");
        mastiffSubBreed.add("english");
        mastiffSubBreed.add("tibetan");

        List<ImagesDTO> imagesDTOList = new ArrayList<>();
        ImagesDTO firstImagen = new ImagesDTO("https://images.dog.ceo/breeds/mastiff-bull/n02108422_1013.jpg");
        ImagesDTO secondImagen = new ImagesDTO("https://images.dog.ceo/breeds/mastiff-bull/n02108422_1016.jpg");

        imagesDTOList.add(firstImagen);
        imagesDTOList.add(secondImagen);

        DogDTO mastiffInfo = new DogDTO();
        mastiffInfo.setBreed(breedName);
        mastiffInfo.setSubBreeds(mastiffSubBreed);
        mastiffInfo.setImages(imagesDTOList);

        ApiDogResponseDTO apiSubBreedResponse = new ApiDogResponseDTO();
        apiSubBreedResponse.setStatus("OK");
        apiSubBreedResponse.setMessage(mastiffSubBreed);

        when(dogApiConnectService.getSubBreedByBreedName(breedName)).thenReturn(apiSubBreedResponse);

        when(dogApiConnectService.getImagesByBreedName(breedName)).thenReturn(responseExpected);

        DogDTO response = dogService.getDogInfo(breedName);

        assertNotNull(response);
        assertEquals(mastiffInfo.getBreed(), response.getBreed());
        assertEquals(mastiffInfo.getImages().size(), response.getImages().size());
        assertEquals(mastiffInfo.getImages().get(1), response.getImages().get(1));
        assertEquals(mastiffInfo.getSubBreeds(), response.getSubBreeds());
    }

}
