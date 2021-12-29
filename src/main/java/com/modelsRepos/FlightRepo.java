package com.modelsRepos;

import com.model.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepo extends CrudRepository<Flight, Long> {
}
