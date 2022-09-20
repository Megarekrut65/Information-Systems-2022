package ua.boa.smartlibrary.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.boa.smartlibrary.dataclasses.Book;
import ua.boa.smartlibrary.dataclasses.PublishingHouse;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT * FROM book WHERE book.title LIKE CONCAT('%', CONCAT(:title, '%')) OR :title LIKE CONCAT('%', CONCAT(book.title, '%'))",
            nativeQuery = true)
    List<Book> findBooksByTitleAdvanced(@Param("title") String title);
    List<Book> findBooksByPublishingHouse(PublishingHouse publishingHouse);
    List<Book> findBooksByPublishYearBetween(Integer publishYearMin, Integer publishYearMax);
}
