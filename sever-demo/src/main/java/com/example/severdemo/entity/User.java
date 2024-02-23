package com.example.severdemo.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "L_NAME")
    private String lName;

    @Column(name = "F_NAME")
    private String fName;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "DOB")
    private Instant dob;
}
