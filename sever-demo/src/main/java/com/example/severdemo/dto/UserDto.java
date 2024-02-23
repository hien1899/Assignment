package com.example.severdemo.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class UserDto implements Serializable {
    public static final long serialVersionUID = 1L;
    private Integer userId;
    private String lName;
    private String fName;
    private Integer age;
    private Instant dob;
}
