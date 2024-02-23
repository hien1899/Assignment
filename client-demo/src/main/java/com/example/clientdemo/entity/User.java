package com.example.clientdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    public static final long serialVersionUID = 1L;
    private Integer userId;
    private String lName;
    private String fName;
    private Integer age;
    private Instant dob;
}
