package com.example.clientdemo.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserUpdateRequest implements Serializable {
    public static final long serialVersionUID = 1L;
    private Integer userId;
    private String fName;
    private String lName;
    private Integer age;
    private String dob;
}
