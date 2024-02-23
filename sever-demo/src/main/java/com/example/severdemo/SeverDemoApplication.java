package com.example.severdemo;

import com.example.severdemo.configuration.ServerConfig;
import com.example.severdemo.endpoint.UserEndpoint;
import com.example.severdemo.exception.NotFoundException;
import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.grpc.GrpcService;
import io.grpc.Status;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeverDemoApplication {
    public static void main(String[] args) {

        var applicationContext = SpringApplication.run(SeverDemoApplication.class, args);

        final var server = newGrpcServer(applicationContext
                .getBean(ServerConfig.class), applicationContext.getBean(UserEndpoint.class));
        server.closeOnJvmShutdown();
        server.start().join();

    }

    private static Server newGrpcServer(ServerConfig grpcServerConfig, UserEndpoint endpoint) {
        final var serverBuilder = Server.builder();
        serverBuilder.http(grpcServerConfig.getHttp()).https(grpcServerConfig.getHttps()).tlsSelfSigned(true);
        final var gRpcService = GrpcService.builder()
                .addService(endpoint)
                .addService(ProtoReflectionService.newInstance())
                .supportedSerializationFormats(GrpcSerializationFormats.values())
                .addExceptionMapping(NotFoundException.class, Status.NOT_FOUND)
                .enableUnframedRequests(true)
                .build();

        serverBuilder.service(gRpcService);

        return serverBuilder.build();
    }
}