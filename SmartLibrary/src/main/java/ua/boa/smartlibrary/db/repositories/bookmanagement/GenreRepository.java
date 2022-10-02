package ua.boa.smartlibrary.db.repositories.bookmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    @Query(value = "SELECT * FROM genre WHERE LOWER(genre.name) " +
            "LIKE CONCAT('%', CONCAT(LOWER(:name), '%')) OR LOWER(:name) " +
            "LIKE CONCAT('%', CONCAT(LOWER(genre.name), '%'))",
            nativeQuery = true)
    List<Genre> findGenresByNameAdvanced(@Param("name") String name);
}
