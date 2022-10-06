package ua.boa.smartlibrary.services.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.dataclasses.bookmanagement.BookGenre;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Genre;
import ua.boa.smartlibrary.db.repositories.bookmanagement.BookGenreRepository;
import ua.boa.smartlibrary.exceptions.bookmanagement.BookGenreNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class BookGenreService {
    @Autowired
    private BookGenreRepository repository;
    @Autowired
    private BookService bookService;
    @Autowired
    private GenreService genreService;

    public BookGenre create(Integer genreId, Integer bookId) {
        Genre genre = genreService.get(genreId);
        Book book = bookService.get(bookId);
        return repository.save(new BookGenre(genre, book));
    }

    public List<BookGenre> getAll() {
        return repository.findAll();
    }

    public BookGenre update(Integer id, Integer genreId, Integer bookId) {
        Genre genre = genreService.get(genreId);
        Book book = bookService.get(bookId);
        BookGenre bookGenre = get(id);
        bookGenre.setGenre(genre);
        bookGenre.setBook(book);
        return repository.save(bookGenre);
    }

    public void remove(Integer id) {
        BookGenre bookGenre = get(id);
        repository.delete(bookGenre);
    }

    public List<BookGenre> findByBook(Integer bookId) {
        Book book = bookService.get(bookId);
        return repository.findBookGenresByBook(book);
    }

    public List<BookGenre> findByGenre(Integer genreId) {
        Genre genre = genreService.get(genreId);
        return repository.findBookGenresByGenre(genre);
    }

    public List<BookGenre> findByGenresAndBooks(List<Genre> genres, List<Book> books) {
        return repository.findBookGenresByGenreInAndBookIn(genres, books);
    }

    public BookGenre get(Integer id) {
        Optional<BookGenre> optional = repository.findById(id);
        if (optional.isEmpty()) throw new BookGenreNotFoundException(id);
        return optional.get();
    }
}
