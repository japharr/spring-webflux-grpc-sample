package com.japharr.grpc.driver.controller;

import com.japharr.grpc.driver.entity.Driver;
import com.japharr.grpc.driver.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DriverController {
    private final DriverRepository driverRepository;

    public DriverController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @PostMapping("/drivers")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Driver> addDriver(@RequestBody Driver car) {
        return driverRepository.save(car);
    }

    @GetMapping("/drivers")
    public Flux<Driver> getDrivers() {
        return driverRepository.findAll();
    }

    @DeleteMapping("/drivers/{id}")
    public Mono<ResponseEntity<Void>> deleteDriver(@PathVariable("id") Long id) {
        return driverRepository.findById(id)
            .flatMap(car -> driverRepository.delete(car).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
