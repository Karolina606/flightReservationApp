package com.modelsRepos;

import com.model.Airport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepo extends CrudRepository<Airport, Long> {
}
