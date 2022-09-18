package ua.boa.smartlibrary.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.boa.smartlibrary.dataclasses.PublishingHouse;

import java.util.List;

public interface PublishingHouseRepository extends JpaRepository<PublishingHouse, Integer> {
    @Query(value = "SELECT * FROM publishing_house WHERE publishing_house.name LIKE CONCAT('%', CONCAT(:name, '%')) OR :name LIKE CONCAT('%', CONCAT(publishing_house.name, '%'))",
            nativeQuery = true)
    List<PublishingHouse> findPublishingHousesByNameAdvanced(@Param("name") String name);
}
