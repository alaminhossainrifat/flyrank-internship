package com.flyrank.crudapi_db.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// Data Transfer Object for creating and updating tasks.
@Data
public class TaskRequest {

    @NotBlank(message = "title must not be empty")
    private String title;

    private Boolean done;
}