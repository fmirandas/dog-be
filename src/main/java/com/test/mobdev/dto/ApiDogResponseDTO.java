package com.test.mobdev.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Api response Class
 * 
 * @author fmirands
 *
 */
@Data
public class ApiDogResponseDTO implements Serializable {

    private static final long serialVersionUID = -5792291034975609809L;

    private List<String> message;
    private String status;

}
