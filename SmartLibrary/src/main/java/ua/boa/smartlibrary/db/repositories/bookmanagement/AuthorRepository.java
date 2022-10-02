package ua.boa.smartlibrary.db.repositories.bookmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query(value = "SELECT * FROM author WHERE LOWER(author.name) " +
            "LIKE CONCAT('%', CONCAT(LOWER(:name), '%')) OR LOWER(:name) LIKE CONCAT('%', CONCAT(LOWER(author.name), '%'))",
            nativeQuery = true)
    List<Author> findAuthorsByNameAdvanced(@Param("name") String name);
}
