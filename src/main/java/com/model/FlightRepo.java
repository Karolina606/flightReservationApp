package com.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepo extends CrudRepository<Flight, Long> {
}
