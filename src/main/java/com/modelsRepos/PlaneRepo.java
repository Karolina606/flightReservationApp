package com.modelsRepos;

import com.model.Plane;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaneRepo extends CrudRepository<Plane, Long> {

}
