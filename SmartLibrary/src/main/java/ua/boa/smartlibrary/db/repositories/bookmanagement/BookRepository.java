package ua.boa.smartlibrary.db.repositories.bookmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.dataclasses.bookmanagement.PublishingHouse;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT * FROM book WHERE  LOWER(book.title) LIKE CONCAT('%', CONCAT( LOWER(:title), '%')) " +
            "OR  LOWER(:title) LIKE CONCAT('%', CONCAT( LOWER(book.title), '%')) ORDER BY book.title;",
            nativeQuery = true)
    List<Book> findBooksByTitleAdvanced(@Param("title") String title);

    List<Book> findBooksByPublishingHouse(PublishingHouse publishingHouse);

    List<Book> findBooksByPublishYearBetween(Integer publishYearMin, Integer publishYearMax);

    @Query(value = "SELECT * FROM book ORDER BY book.title;", nativeQuery = true)
    List<Book> getAllOrdered();
}
