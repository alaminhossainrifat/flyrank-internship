package com.flyrank.crudapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// Request payload used for both POST /tasks and PUT /tasks/{id}.
// title is mandatory; done is optional (server decides the default).
@Data
public class TaskRequest {

    @NotBlank(message = "title must not be empty")
    private String title;

    private Boolean done;
}