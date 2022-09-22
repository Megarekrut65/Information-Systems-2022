package ua.boa.smartlibrary.db.repositories.bookmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query(value = "SELECT * FROM tag WHERE tag.name " +
            "LIKE CONCAT('%', CONCAT(:name, '%')) OR :name " +
            "LIKE CONCAT('%', CONCAT(tag.name, '%'))",
            nativeQuery = true)
    List<Tag> findTagsByNameAdvanced(@Param("name") String name);
}
