package com.test.mobdev.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * Class that stores the images of the Dog object
 * @author fmirands
 *
 */
@Data
public class ImagesDTO implements Serializable{

    private static final long serialVersionUID = -279812171685078860L;
    private String url;
    
    
    public ImagesDTO(String url) {
        super();
        this.url = url;
    }
    
    
    
}
