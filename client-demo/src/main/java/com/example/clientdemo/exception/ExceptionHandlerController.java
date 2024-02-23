package com.example.clientdemo.exception;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity gRpcException(StatusRuntimeException ex){
        return ResponseEntity.status(mapStatus(ex)).body(ex.getMessage());
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity interruptException(InterruptedException ex){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Not accepted");
    }

    private HttpStatus mapStatus(StatusRuntimeException ex){
        return ex.getStatus().equals(Status.NOT_FOUND) ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
