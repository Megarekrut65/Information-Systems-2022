package ua.boa.smartlibrary.db.repositories.bookmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.boa.smartlibrary.dataclasses.bookmanagement.PublishingHouse;

import java.util.List;

public interface PublishingHouseRepository extends JpaRepository<PublishingHouse, Integer> {
    @Query(value = "SELECT * FROM publishing_house WHERE LOWER(publishing_house.name) " +
            "LIKE CONCAT('%', CONCAT(LOWER(:name), '%')) OR LOWER(:name) " +
            "LIKE CONCAT('%', CONCAT(LOWER(publishing_house.name), '%'))",
            nativeQuery = true)
    List<PublishingHouse> findPublishingHousesByNameAdvanced(@Param("name") String name);

    @Query(value = "SELECT * FROM publishing_house ORDER BY publishing_house.name;", nativeQuery = true)
    List<PublishingHouse> getAllOrdered();
}
