package com.example.clientdemo.api;

import com.example.clientdemo.entity.Message;
import com.example.clientdemo.entity.User;
import com.example.clientdemo.model.*;
import com.example.clientdemo.service.interfaces.UserService;
import io.grpc.StatusRuntimeException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserApi {
    private final UserService userService;

    @GetMapping("/user/find/{id}")
    public User findUserById(@PathVariable(name = "id") Integer id) throws StatusRuntimeException {
        var result = userService.findById(id);
        return result;
    }

    @PostMapping("/user/find/name")
    public List<User> findUserByName(@RequestBody FindByNameRequest request) throws StatusRuntimeException {
        return userService.findByName(request);
    }

    @PostMapping("/user/find/dob")
    public List<User> findUserByDob(@RequestBody FindByDobRequest request) throws StatusRuntimeException {
        return userService.findByDob(request);
    }

    @PostMapping("/user/add/single-user")
    public Message saveUser(@RequestBody CreateUserRequest request) throws InterruptedException {
        return userService.createNewUser(request);
    }

    @PostMapping("/user/add/multi-user")
    public List<Message> saveMultiUser(@RequestBody List<CreateUserRequest> request) throws InterruptedException {
        return userService.createMultiNewUser(request);
    }

    @DeleteMapping("/user/delete/single-user/{id}")
    public Message saveMultiUser(@PathVariable(name = "id") Integer id) throws InterruptedException {
        return userService.deleteUser(id);
    }

    @DeleteMapping("/user/delete/multi-user")
    public List<Message> deleteMultiUser(@RequestBody List<DeleteMultiUserRequest> request) throws InterruptedException {
        return userService.deleteMultipleUsers(request);
    }

    @PostMapping("/user/update/single-user")
    public Message updateSingleUser(@RequestBody UserUpdateRequest request) throws InterruptedException {
        return userService.updateUser(request);
    }

    @PostMapping("/user/update/multi-user")
    public List<Message> updateSingleUser(@RequestBody List<UserUpdateRequest> request) throws InterruptedException {
        return userService.updateMultipleUser(request);
    }

}
