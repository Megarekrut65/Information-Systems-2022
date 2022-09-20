package ua.boa.smartlibrary.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.boa.smartlibrary.dataclasses.Customer;
import ua.boa.smartlibrary.dataclasses.Distributor;

import java.util.List;

public interface DistributorRepository extends JpaRepository<Distributor, Integer> {
    @Query(value = "SELECT * FROM distributor WHERE distributor.name " +
            "LIKE CONCAT('%', CONCAT(:name, '%')) OR :name " +
            "LIKE CONCAT('%', CONCAT(distributor.name, '%'))",
            nativeQuery = true)
    List<Distributor> findDistributorsByNameAdvanced(@Param("name") String name);
}
