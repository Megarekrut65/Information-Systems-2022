package ua.boa.smartlibrary.db.repositories.bookcirculationmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookLost;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;

import java.sql.Date;
import java.util.List;

public interface BookLostRepository extends JpaRepository<BookLost, Integer> {
    List<BookLost> findBooksLostByDateOfLossBetween(Date dateOfLossMin, Date dateOfLossMax);

    List<BookLost> findBooksLostByBook(Book book);
}
