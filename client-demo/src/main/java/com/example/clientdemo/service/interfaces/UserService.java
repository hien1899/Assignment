package com.example.clientdemo.service.interfaces;


import com.example.clientdemo.entity.Message;
import com.example.clientdemo.entity.User;
import com.example.clientdemo.model.*;

import java.util.List;

public interface UserService {
    User findById(Integer id);
    List<User> findByName(FindByNameRequest requestModel);
    List<User> findByDob(FindByDobRequest requestModel);
    Message createNewUser(CreateUserRequest requestModel);
    List<Message> createMultiNewUser(List<CreateUserRequest> requests);
    Message deleteUser(int id);
    List<Message> deleteMultipleUsers(List<DeleteMultiUserRequest> userIds);
    Message updateUser(UserUpdateRequest user);
    List<Message> updateMultipleUser(List<UserUpdateRequest> users);
}