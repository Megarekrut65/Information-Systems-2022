package ua.boa.smartlibrary.db.repositories.bookmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.dataclasses.bookmanagement.BookGenre;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Genre;

import java.util.Collection;
import java.util.List;

public interface BookGenreRepository extends JpaRepository<BookGenre, Integer> {
    List<BookGenre> findBookGenresByBook(Book book);

    List<BookGenre> findBookGenresByGenre(Genre genre);
    List<BookGenre> findBookGenresByGenreInAndBookIn(Collection<Genre> genre, Collection<Book> book);
}
