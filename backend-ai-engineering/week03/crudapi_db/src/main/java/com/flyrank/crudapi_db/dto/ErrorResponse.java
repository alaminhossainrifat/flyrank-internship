package com.flyrank.crudapi_db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// Standardized error response payload.
@Data
@AllArgsConstructor
public class ErrorResponse {
    private String error;
}