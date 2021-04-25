package com.japharr.grpc.calculator.service;

import com.japharr.calculator.CalculatorServiceGrpc;
import com.japharr.calculator.Input;
import com.japharr.calculator.Output;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.stream.IntStream;

@GrpcService
public class CalculatorService extends CalculatorServiceGrpc.CalculatorServiceImplBase {
    @Override
    public void getAllDoubles(Input request, StreamObserver<Output> responseObserver) {
        int index = request.getNumber();

        IntStream.rangeClosed(1, index)
            .map(i -> i * 2)
            .mapToObj(i -> Output.newBuilder().setResult(i).build())
            .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }
}
