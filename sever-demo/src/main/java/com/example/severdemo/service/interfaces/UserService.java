package com.example.severdemo.service.interfaces;

import com.example.severdemo.entity.User;

import java.util.List;

public interface UserService {
    User findUserById(int id);
    List<User> findUserByName(String name, String field);
    List<User> findUserByDob(String startDate, String endDate);
    int createNewUser(String fName, String lName, int age, String dob);
    List<Integer> createUsersUsingList(List<User> users);
    boolean deleteUser(int id);
    void updateUser(int id, String fName, String lName, int age, String dob);
}
