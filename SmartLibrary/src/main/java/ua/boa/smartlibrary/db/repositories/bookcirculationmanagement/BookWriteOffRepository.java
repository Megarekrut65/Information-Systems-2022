package ua.boa.smartlibrary.db.repositories.bookcirculationmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookWriteOff;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

public interface BookWriteOffRepository extends JpaRepository<BookWriteOff, Integer> {
    List<BookWriteOff> findBookWriteOffsByDateOfWriteOffBetween(Date dateOfWriteOffMin, Date dateOfWriteOffMax);

    List<BookWriteOff> findBookWriteOffsByBook(Book book);

    List<BookWriteOff> findBookWriteOffsByBookIn(Collection<Book> book);

    List<BookWriteOff> findBookWriteOffsByDateOfWriteOffBetweenAndBookIn(Date dateOfWriteOffMin,
                                                                         Date dateOfWriteOffMax,
                                                                         Collection<Book> book);

    List<BookWriteOff> findBookWriteOffsByDateOfWriteOffGreaterThanAndBookIn(Date dateOfWriteOff,
                                                                             Collection<Book> book);

    List<BookWriteOff> findBookWriteOffsByDateOfWriteOffLessThanAndBookIn(Date dateOfWriteOff,
                                                                          Collection<Book> book);
}
