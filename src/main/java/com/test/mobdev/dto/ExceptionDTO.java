package com.test.mobdev.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Exception Class
 * @author fmirands
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDTO {

    private String type;
    private String description;

}
