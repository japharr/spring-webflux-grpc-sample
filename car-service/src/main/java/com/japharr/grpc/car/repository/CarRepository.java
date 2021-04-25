package com.japharr.grpc.car.repository;

import com.japharr.grpc.car.entity.Car;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CarRepository extends ReactiveMongoRepository<Car, Long> {
}
