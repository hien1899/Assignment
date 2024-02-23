package com.example.clientdemo.service;

import com.example.clientdemo.constant.ServerConst;
import com.example.clientdemo.entity.Message;
import com.example.clientdemo.entity.User;
import com.example.clientdemo.model.*;
import com.example.clientdemo.model.UserUpdateRequest;
import com.example.clientdemo.service.interfaces.UserService;
import com.example.user.*;
import com.linecorp.armeria.client.grpc.GrpcClients;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;
    private final UserServiceGrpc.UserServiceStub userServiceStub;

    @Autowired
    public UserServiceImpl() {
        this.userServiceBlockingStub = GrpcClients.newClient(ServerConst.SERVER_URI, UserServiceGrpc.UserServiceBlockingStub.class);
        this.userServiceStub = GrpcClients.newClient(ServerConst.SERVER_URI, UserServiceGrpc.UserServiceStub.class);
    }

    public User findById(Integer id) {
        var request = FindUserByIdRequest.newBuilder().setUserId(id).build();
        var response = userServiceBlockingStub.findUserById(request);
        var user = User.builder()
                            .userId(response.getUserId())
                            .fName(response.getFName())
                            .lName(response.getLName())
                            .age(response.getAge())
                            .dob(Instant.parse(response.getDob()))
                            .build();
        System.out.println(response);
        return user;
    }

    public List<User> findByName(FindByNameRequest requestModel){
        List<User> users = new ArrayList<>();
        var request = FindUserByNameRequest.newBuilder()
                .setName(requestModel.getName())
                .setField(requestModel.getField())
                .build();
        var response = userServiceBlockingStub.findUserByName(request);
        response.getUserDetailList().forEach(u -> users.add(User.builder()
                .userId(u.getUserId())
                        .fName(u.getFName())
                        .lName(u.getLName())
                        .age(u.getAge())
                        .dob(Instant.parse(u.getDob()))
                .build()));
        return users;
    }

    @Override
    public List<User> findByDob(FindByDobRequest requestModel){
        var countDown = new CountDownLatch(1);
        List<User> users = new ArrayList<>();
        var request = FindUserByDobRequest.newBuilder()
                .setBeginDate(requestModel.getBeginDate())
                .setEndDate(requestModel.getEndDate())
                .build();
        var response = userServiceBlockingStub.findUserByDob(request);
        response.getUserDetailList().forEach(u -> users.add(User.builder()
                .userId(u.getUserId())
                .fName(u.getFName())
                .lName(u.getLName())
                .age(u.getAge())
                .dob(Instant.parse(u.getDob()))
                .build()));
        return users;
    }

    @Override
    public Message createNewUser(CreateUserRequest requestModel){
        var request = NewUserRequest.newBuilder()
                .setFName(requestModel.getFName())
                .setLName(requestModel.getLName())
                .setAge(requestModel.getAge())
                .setDob(requestModel.getDob())
                .build();
        var response = userServiceBlockingStub.createUser(request);
        var message = Message.builder()
                .code(response.getCode())
                .message(response.getMessage())
                .build();
        return message;
    }

    @Override
    public List<Message> createMultiNewUser(List<CreateUserRequest> requests){
        var list = requests.stream()
                .map(re -> NewUserRequest.newBuilder()
                .setFName(re.getFName())
                .setLName(re.getLName())
                .setAge(re.getAge())
                .setDob(re.getDob()).build()).collect(Collectors.toList());
        var request = NewUserListRequest.newBuilder()
                .addAllNewUserRequest(list)
                .build();
        var response = userServiceBlockingStub.createMultipleUser(request);
        List<Message> messages = new ArrayList<>();
        while (response.hasNext()){
            MessageResponse ms = response.next();
            messages.add(Message.builder()
                    .code(ms.getCode())
                    .message(ms.getMessage())
                    .build());
        }
        return messages;
    }

    @Override
    public Message deleteUser(int id){
        var request = DeleteUserRequest.newBuilder().setUserId(id).build();
        var response = userServiceBlockingStub.deleteUser(request);
        var message = Message.builder()
                .code(response.getCode())
                .message(response.getMessage())
                .build();
        return message;
    }

    @Override
    public List<Message> deleteMultipleUsers(List<DeleteMultiUserRequest> userIds){
        var list = userIds.stream().map(userId -> DeleteUserRequest.newBuilder()
                .setUserId(userId.getUserId())
                .build()).collect(Collectors.toList());
        var request = DeleteUserListRequest.newBuilder().addAllDeleteUserRequests(list).build();
        var response = userServiceBlockingStub.deleteMultipleUser(request);
        List<Message> messages = new ArrayList<>();
        for (MessageResponse ms: response.getMessageResponsesList()) {
            messages.add(Message.builder()
                    .code(ms.getCode())
                    .message(ms.getMessage())
                    .build());
        }
        return messages;
    }

    @Override
    public Message updateUser(UserUpdateRequest user){
        var request = UpdateUserRequest
                .newBuilder()
                .setUserId(user.getUserId())
                .setFName(user.getFName())
                .setLName(user.getLName())
                .setAge(user.getAge())
                .setDob(user.getDob())
                .build();
        var response = userServiceBlockingStub.updateUser(request);
        var message = Message.builder()
                .code(response.getCode())
                .message(response.getMessage())
                .build();
        return message;
    }

    @Override
    public List<Message> updateMultipleUser(List<UserUpdateRequest> users){
        var list = users.stream().map(user -> UpdateUserRequest.newBuilder()
                .setUserId(user.getUserId())
                .setFName(user.getFName())
                .setLName(user.getLName())
                .setAge(user.getAge())
                .setDob(user.getDob())
                .build()).collect(Collectors.toList());
        var request = UpdateUserListRequest.newBuilder().addAllUpdateUserRequest(list).build();
        var response = userServiceBlockingStub.updateMultipleUser(request);
//        List<Message> messages = new ArrayList<>();
//        for (MessageResponse ms: response.getMessageResponsesList()) {
//            messages.add(Message.builder()
//                    .code(ms.getCode())
//                    .message(ms.getMessage())
//                    .build());
//        }
        return getMessageResponseFromServer(response);
    }

    private List<Message> getMessageResponseFromServer(MessageListResponse response){
        List<Message> messages = new ArrayList<>();
        for (MessageResponse ms: response.getMessageResponsesList()) {
            messages.add(Message.builder()
                    .code(ms.getCode())
                    .message(ms.getMessage())
                    .build());
        }
        return messages;
    }
}
