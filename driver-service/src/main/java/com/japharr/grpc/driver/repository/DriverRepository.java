package com.japharr.grpc.driver.repository;

import com.japharr.grpc.driver.entity.Driver;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DriverRepository extends ReactiveMongoRepository<Driver, Long> {
}
