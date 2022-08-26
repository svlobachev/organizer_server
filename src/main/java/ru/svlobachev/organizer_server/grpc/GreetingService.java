package ru.svlobachev.organizer_server.grpc;

import organizer.GreeterGrpc;
import organizer.RequestMessage;
import organizer.ResponseMessage;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.svlobachev.organizer_server.registration.controller.reaction.RegistrationGrpcRebound;

import java.util.Map;

@GRpcService
public class GreetingService extends GreeterGrpc.GreeterImplBase {
    @Autowired
    RegistrationGrpcRebound registrationGrpcRebound;
    Map<String, String> newRegistrationBox;

    @Override
    public void sayHello(RequestMessage req, StreamObserver<ResponseMessage> responseObserver) {
        newRegistrationBox = registrationGrpcRebound.registrationResponse(req.getRegistrationBoxRequestMap());
        ResponseMessage reply = ResponseMessage.newBuilder().putAllRegistrationBoxResponse(newRegistrationBox).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
