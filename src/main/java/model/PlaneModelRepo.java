package model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaneModelRepo extends CrudRepository<PlaneModel, Long> {
}
