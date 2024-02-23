package com.example.severdemo.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MessageResponseDto implements Serializable {
    public static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    private String time;
}
