package com.japharr.grpc.driver.service;

import com.japharr.driver.Driver;
import com.japharr.driver.ReactorDriverServiceGrpc;
import com.japharr.grpc.driver.repository.DriverRepository;
import com.japharr.utils.Input;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Mono;

@GrpcService
public class DriverGrpcService extends ReactorDriverServiceGrpc.DriverServiceImplBase {
    private final DriverRepository driverRepository;

    public DriverGrpcService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Mono<Driver> getDriverById(Mono<Input> request) {
        System.out.println("getDriverById");
        return request.flatMap(r-> driverRepository.findById(r.getValue()))
            .map(driver ->
                Driver
                .newBuilder()
                    .setId(driver.getId())
                    .setName(driver.getName())
                    .build());
    }
}
