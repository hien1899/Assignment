package com.example.clientdemo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {
    private String lName;
    private String fName;
    private Integer age;
    private String dob;
}
