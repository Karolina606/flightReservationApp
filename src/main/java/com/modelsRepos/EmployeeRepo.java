package com.modelsRepos;

import com.model.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface EmployeeRepo extends CrudRepository<Employee, Long> {

	@Query(value = "SELECT SUM(TIMESTAMPDIFF(HOUR, f.departure_date, f.arrival_date)) " +
			"FROM employee e " +
			"INNER JOIN flight_crew fc ON fc.crew_id = e.id " +
			"INNER JOIN flight f on fc.flights_id = f.id " +
			"WHERE e.id = :employeeId " +
			"AND YEAR(f.departure_date) = YEAR(:date) " +
			"AND MONTH(f.departure_date) = YEAR(:date)",
			nativeQuery = true)
	Integer getWorkedHoursInMonth(@Param("employeeId") Long employeeId,
								  @Param("date") LocalDateTime date);

	// Ilu pilotów już zapisanych do lotu
	@Query(value = "SELECT COUNT(*) FROM employee e " +
			"INNER JOIN flight_crew fc ON fc.crew_id = e.id " +
			"WHERE e.employee_role = 0 AND fc.flights_id = :flightId",
			nativeQuery = true)
	Integer howManyPilotsAlreadyInFlight(@Param("flightId") Long flightId);


	// Ile stewardess już zapisanych do lotu
	@Query(value = "SELECT COUNT(*) FROM employee e " +
			"INNER JOIN flight_crew fc ON fc.crew_id = e.id " +
			"WHERE e.employee_role = 1 AND fc.flights_id = :flightId",
			nativeQuery = true)
	Integer howManyStewardessAlreadyInFlight(@Param("flightId") Long flightId);

	// Zwraca wszystkie daty wylotów danego pracownika
	@Query(value = "SELECT f.departure_date FROM flight_crew fc " +
			"INNER JOIN flight f ON f.id = fc.flights_id " +
			"WHERE fc.crew_id = :employeeId",
			nativeQuery = true)
	List<LocalDateTime> getAllDepartureDatesOfEmployee(@Param("employeeId") Long employeeId);

	// Zwraca wszystkie daty przylotow danego pracownika
	@Query(value = "SELECT f.arrival_date FROM flight_crew fc " +
			"INNER JOIN flight f ON f.id = fc.flights_id " +
			"WHERE fc.crew_id = :employeeId",
			nativeQuery = true)
	List<LocalDateTime> getAllArrivalDatesOfEmployee(@Param("employeeId") Long employeeId);

	// Zwraca wszystkich pracownikow z dana rola
	@Query(value = "SELECT * FROM employee e " +
			"WHERE e.employee_role = :employeeRole",
			nativeQuery = true)
	List<Employee> getEmployeesWithRole(@Param("employeeRole") int employeeRole);


	// ZUsuwa pracownika
	@Modifying
	@Query(value = "DELETE FROM employee WHERE employee.id = :employeeId",
			nativeQuery = true)
	void deleteByEmployeeId(@Param("employeeId") Long employeeId);
}
