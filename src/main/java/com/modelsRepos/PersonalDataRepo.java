package com.modelsRepos;

import com.model.PersonalData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PersonalDataRepo extends CrudRepository<PersonalData, Long> {


        @Query("SELECT d FROM PersonalData d " +
                "WHERE LOWER(d.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
                "OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
        List<PersonalData> search(@Param("searchTerm") String searchTerm);

}
