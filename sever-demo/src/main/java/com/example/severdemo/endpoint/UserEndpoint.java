package com.example.severdemo.endpoint;

import com.example.severdemo.dto.MessageResponseDto;
import com.example.severdemo.entity.User;
import com.example.severdemo.service.interfaces.UserService;
import com.example.user.*;
import io.grpc.stub.ServerCalls;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserEndpoint extends UserServiceGrpc.UserServiceImplBase {

    private final UserService userService;

    @Override
    public void findUserById(FindUserByIdRequest request, StreamObserver<UserDetail> responseObserver) {
        var user = userService.findUserById(request.getUserId());
        var userDetail = UserDetail.newBuilder()
                .setUserId(user.getUserId())
                .setFName(user.getFName())
                .setLName(user.getLName())
                .setAge(user.getAge())
                .setDob(user.getDob().toString())
                .build();
        responseObserver.onNext(userDetail);
        responseObserver.onCompleted();
    }

    @Override
    public void findUserByName(FindUserByNameRequest request, StreamObserver<UserDetailsList> responseObserver) {
        var userList = userService.findUserByName(request.getName(), request.getField());
        UserDetailsList userDetails = buildUserDetailsList(userList);
        responseObserver.onNext(userDetails);
        responseObserver.onCompleted();
    }

    @Override
    public void findUserByDob(FindUserByDobRequest request, StreamObserver<UserDetailsList> responseObserver) {
        var users = userService.findUserByDob(request.getBeginDate(), request.getEndDate());
        UserDetailsList userDetails = buildUserDetailsList(users);
        responseObserver.onNext(userDetails);
        responseObserver.onCompleted();
    }

    @Override
    public void createUser(NewUserRequest request, StreamObserver<MessageResponse> responseObserver) {
        int id = userService.createNewUser(request.getFName(), request.getLName(), request.getAge(), request.getDob());
        responseObserver.onNext(MessageResponse.newBuilder().setCode(200).setMessage("User has been created with id = " + id).build());
        responseObserver.onCompleted();
    }

    @Override
    public void createMultipleUser(NewUserListRequest request, StreamObserver<MessageResponse> responseObserver) {
        for (NewUserRequest newUser : request.getNewUserRequestList()) {
            int id = userService.createNewUser(newUser.getFName(), newUser.getLName(), newUser.getAge(), newUser.getDob());
            responseObserver.onNext(MessageResponse.newBuilder().setCode(200).setMessage("User has been created with id = " + id).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void deleteUser(DeleteUserRequest request, StreamObserver<MessageResponse> responseObserver) {
        boolean check = userService.deleteUser(request.getUserId());
        if(check){
            responseObserver.onNext(MessageResponse.newBuilder().setCode(200).setMessage("User with id: " + request.getUserId() + " has been deleted").build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void deleteMultipleUser(DeleteUserListRequest request, StreamObserver<MessageListResponse> responseObserver) {
        List<MessageResponseDto> messageResponseDtos = new ArrayList<>();
        for (DeleteUserRequest userDeleted : request.getDeleteUserRequestsList()) {
            boolean check = userService.deleteUser(userDeleted.getUserId());
            messageResponseDtos.add(MessageResponseDto.builder().code(200).message("User with id: " + userDeleted.getUserId() + " has been deleted").build());
        }
        MessageListResponse messageListResponse = buildMessageResponseList(messageResponseDtos);
        responseObserver.onNext(messageListResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void updateUser(UpdateUserRequest request, StreamObserver<MessageResponse> responseObserver) {
        userService.updateUser(request.getUserId(), request.getFName(), request.getLName(), request.getAge(), request.getDob());
        responseObserver.onNext(MessageResponse.newBuilder().setCode(200).setMessage("User with id: " + request.getUserId() + " has been updated").build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateMultipleUser(UpdateUserListRequest request, StreamObserver<MessageListResponse> responseObserver) {
        List<MessageResponseDto> messageResponseDtos = new ArrayList<>();
        for(UpdateUserRequest userUpdated : request.getUpdateUserRequestList()){
            userService.updateUser(userUpdated.getUserId(), userUpdated.getFName(), userUpdated.getLName(), userUpdated.getAge(), userUpdated.getDob());
            messageResponseDtos.add(MessageResponseDto.builder().code(200).message("User with id: " + userUpdated.getUserId() + " has been updated").build());
        }
        MessageListResponse messageListResponse = buildMessageResponseList(messageResponseDtos);
        responseObserver.onNext(messageListResponse);
        responseObserver.onCompleted();
    }

    private UserDetailsList buildUserDetailsList(List<User> userList) {
        UserDetailsList userDetails = UserDetailsList.newBuilder()
                .addAllUserDetail(userList.stream().map(
                                user -> UserDetail.newBuilder()
                                        .setUserId(user.getUserId())
                                        .setFName(user.getFName())
                                        .setLName(user.getLName())
                                        .setAge(user.getAge())
                                        .setDob(user.getDob().toString())
                                        .build())
                        .collect(Collectors.toList()))
                .build();
        return userDetails;
    }

    private MessageListResponse buildMessageResponseList(List<MessageResponseDto> messageResponseDtos) {
        MessageListResponse messageListResponse = MessageListResponse.newBuilder()
                .addAllMessageResponses(messageResponseDtos.stream().map(
                                res -> MessageResponse.newBuilder()
                                        .setCode(res.getCode())
                                        .setMessage(res.getMessage())
                                        .build())
                        .collect(Collectors.toList()))
                .build();
        return messageListResponse;
    }
}
