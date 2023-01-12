package com.souravbose.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    String resourceName;
    String resourceFieldName;
    String fieldValue;

    public ResourceNotFoundException(String resourceName, String resourceFieldName, String fieldValue) {
        super(String.format("%s not found with %s : %s", resourceName,resourceFieldName, fieldValue));
        this.resourceName = resourceName;
        this.resourceFieldName = resourceFieldName;
        this.fieldValue = fieldValue;
    }
}
