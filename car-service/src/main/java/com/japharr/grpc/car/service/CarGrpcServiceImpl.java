package com.japharr.grpc.car.service;

import com.japharr.car.*;
import com.japharr.grpc.car.repository.CarRepository;
import com.japharr.utils.Input;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@GrpcService
public class CarGrpcServiceImpl extends ReactorCarServiceGrpc.CarServiceImplBase {
    private final CarRepository carRepository;

    public CarGrpcServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Mono<Car> getCarById(Mono<Input> request) {
        return request
            .flatMap(r -> carRepository.findById(r.getValue()))
                .map(car -> Car.newBuilder()
                    .setId(car.getId())
                    .setName(car.getName())
                    .setReleaseDate(car.getReleaseDate().toEpochDay())
                    .build());
    }

    @Override
    public Flux<Car> getCarsByDriverId(Mono<Input> request) {
        return request
            .flatMapMany(r -> carRepository.findAllByDriverId(r.getValue()))
            .map(car -> Car.newBuilder()
                .setId(car.getId())
                .setName(car.getName())
                .setReleaseDate(car.getReleaseDate().toEpochDay())
                .build());
    }
}
