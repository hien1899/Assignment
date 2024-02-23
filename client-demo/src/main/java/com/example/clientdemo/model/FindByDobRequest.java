package com.example.clientdemo.model;

import lombok.Data;

@Data
public class FindByDobRequest {
    private String beginDate;
    private String endDate;
}
