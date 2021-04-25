package com.japharr.grpc.driver.service;

import com.japharr.utils.Input;
import com.japharr.car.ReactorCarServiceGrpc;
import com.japharr.grpc.driver.dto.CarDto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DriverService {
    @GrpcClient("car-service")
    private ReactorCarServiceGrpc.ReactorCarServiceStub carServiceStub;

    public Flux<CarDto> getDriversCarById(Long id) {
        return Mono.just(id)
            .flatMapMany(r ->
                carServiceStub
                    .getCarsByDriverId(Mono.just(Input.newBuilder().setValue(r).build())))
            .map(r -> new CarDto(r.getId(), r.getName()));
    }

    public Mono<CarDto> getCars(Long id) {
        return Mono.just(id)
            .flatMap(r ->
                carServiceStub
                    .getCarById(Mono.just(Input.newBuilder().setValue(r).build())))
            .map(r -> new CarDto(r.getId(), r.getName()));
    }
}
