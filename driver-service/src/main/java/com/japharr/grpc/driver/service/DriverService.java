package com.japharr.grpc.driver.service;

import com.japharr.grpc.driver.dto.DriverDto;
import com.japharr.grpc.driver.entity.Driver;
import com.japharr.grpc.driver.repository.DriverRepository;
import com.japharr.utils.Input;
import com.japharr.car.ReactorCarServiceGrpc;
import com.japharr.grpc.driver.dto.CarDto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DriverService {
    @GrpcClient("car-service")
    private ReactorCarServiceGrpc.ReactorCarServiceStub carServiceStub;

    @Autowired
    private DriverRepository driverRepository;

    public Flux<DriverDto> getDrivers() {
        return driverRepository.findAll()
            .map(r -> new DriverDto(r.getId(), r.getName()));
    }

    public Mono<DriverDto> addDriver(Driver car) {
        return driverRepository.save(car)
            .map(r-> new DriverDto(r.getId(), r.getName()));
    }

    public Mono<Void> deleteDriver(@PathVariable("id") Long id) {
        return driverRepository.findById(id)
            .flatMap(car -> driverRepository.delete(car));
    }

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
