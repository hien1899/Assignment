package com.example.clientdemo.model;

import lombok.Data;

@Data
public class FindByNameRequest {
    private String name;
    private String field;
}
