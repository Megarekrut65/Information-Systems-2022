package ua.boa.smartlibrary.db.repositories.bookcirculationmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookWriteOff;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;

import java.sql.Date;
import java.util.List;

public interface BookWriteOffRepository extends JpaRepository<BookWriteOff, Integer> {
    List<BookWriteOff> findBookWriteOffsByDateOfWriteOffBetween(Date dateOfWriteOffMin, Date dateOfWriteOffMax);

    List<BookWriteOff> findBookWriteOffsByBook(Book book);
}
