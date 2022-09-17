package ua.boa.smartlibrary.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.boa.smartlibrary.dataclasses.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query(value = "SELECT * FROM author WHERE author.name LIKE CONCAT('%', CONCAT(:name, '%')) OR :name LIKE CONCAT('%', CONCAT(author.name, '%'))",
            nativeQuery = true)
    List<Author> findAuthorByNameAdvanced(@Param("name") String name);
}
