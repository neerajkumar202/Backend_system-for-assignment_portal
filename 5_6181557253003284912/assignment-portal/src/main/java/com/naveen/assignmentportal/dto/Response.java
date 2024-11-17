package com.naveen.assignmentportal.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Response {
    private int statusCode;
    private String message;
    private List<?> data;
    private int page;
    private int size;
    private long totalElements;
}
