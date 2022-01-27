package com.modelsRepos;

import com.model.Address;
import com.model.Flight;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FlightRepo extends CrudRepository<Flight, Long> {

	@Query(value = "SELECT COUNT(*) FROM Reservation r " +
			"WHERE r.flight_id = :flightId", nativeQuery = true)
	Integer getNumberOfOccupiedSeats(@Param("flightId") Long flightId);

	@Modifying
	@Query(value = "DELETE FROM flight " +
			"WHERE flight.arrival_date < SYSDATE()", nativeQuery = true)
	void deleteOldFlights();

	@Modifying
	@Query(value = "DELETE FROM flight WHERE flight.id = :flightId",
			nativeQuery = true)
	void deleteByFlightId(@Param("flightId") Long flightId);
}
