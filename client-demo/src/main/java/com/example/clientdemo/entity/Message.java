package com.example.clientdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message implements Serializable {
    public static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
}
