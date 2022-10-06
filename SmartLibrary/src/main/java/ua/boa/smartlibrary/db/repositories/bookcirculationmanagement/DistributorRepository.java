package ua.boa.smartlibrary.db.repositories.bookcirculationmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Distributor;

import java.util.List;

public interface DistributorRepository extends JpaRepository<Distributor, Integer> {
    @Query(value = "SELECT * FROM distributor WHERE LOWER(distributor.name) " +
            "LIKE CONCAT('%', CONCAT(LOWER(:name), '%')) OR LOWER(:name) " +
            "LIKE CONCAT('%', CONCAT(LOWER(distributor.name), '%'))",
            nativeQuery = true)
    List<Distributor> findDistributorsByNameAdvanced(@Param("name") String name);
}
