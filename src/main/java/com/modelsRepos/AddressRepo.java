package com.modelsRepos;

import com.model.Address;
import com.model.PersonalData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends CrudRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE " +
            "LOWER(a.country) LIKE LOWER(:addressCountry)" +
            "AND LOWER(a.city) LIKE LOWER(:addressCity)" +
            "AND LOWER(a.postcode) LIKE LOWER(:addressPostcode)" +
            "AND LOWER(a.street) LIKE LOWER(:addressStreet)" +
            "AND (a.buildingNr = :addressBuildingNr)" +
            "AND (a.apartmentNr = :addressApartmentNr)")
    List<Address> searchForAddress(@Param("addressCountry") String addressCountry,
                                   @Param("addressCity") String addressCity,
                                   @Param("addressPostcode") String addressPostcode,
                                   @Param("addressStreet") String addressStreet,
                                   @Param("addressBuildingNr") int addressBuildingNr,
                                   @Param("addressApartmentNr") int addressApartmentNr);
}
