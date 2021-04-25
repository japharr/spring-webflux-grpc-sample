package com.japharr.grpc.car.service;

import com.japharr.car.ReactorCarServiceGrpc;
import com.japharr.driver.ReactorDriverServiceGrpc;
import com.japharr.grpc.car.dto.CarDto;
import com.japharr.grpc.car.dto.DriverDto;
import com.japharr.grpc.car.entity.Car;
import com.japharr.grpc.car.repository.CarRepository;
import com.japharr.utils.Input;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarService {
    @GrpcClient("driver-service")
    private ReactorDriverServiceGrpc.ReactorDriverServiceStub driverServiceStub;

//    @Autowired
//    private CarRepository carRepository;

    public Mono<DriverDto> findDriverById(Long id) {
        return driverServiceStub
            .getDriverById(Mono.just(Input.newBuilder().build()))
            .map(driver -> new DriverDto(driver.getId(), driver.getName()));
    }

//    public Mono<CarDto> findCarById(Long id) {
//        return carRepository.findById(id)
//            .flatMap(r -> {
//                Mono<DriverDto> driverDto = findDriverById(r.getDriverId());
//                return Mono.zip(Mono.just(r), driverDto)
//                    .map(m -> new CarDto(m.getT1().getId(), m.getT1().getName(), m.getT2()));
//            });
//    }
//
//    public Flux<CarDto> findAllCars() {
//        return carRepository.findAll()
//            .flatMap(r -> {
//                Mono<DriverDto> driverDto = findDriverById(r.getDriverId());
//                return Mono.zip(Mono.just(r), driverDto)
//                    .map(m -> new CarDto(m.getT1().getId(), m.getT1().getName(), m.getT2()));
//            });
//    }
//
//    public Mono<CarDto> addCar(Car car) {
//        return carRepository.save(car)
//            .flatMap(r -> {
//                Mono<DriverDto> driverDto = findDriverById(r.getDriverId());
//                return Mono.zip(Mono.just(r), driverDto).map(m -> new CarDto(m.getT1().getId(), m.getT1().getName(), m.getT2()));
//            });
//    }
//
//    public Mono<Void> deleteCarById(Long id) {
//        return carRepository.findById(id).flatMap(r -> carRepository.delete(r));
//    }
}
