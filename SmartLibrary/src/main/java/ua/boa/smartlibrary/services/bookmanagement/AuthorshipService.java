package ua.boa.smartlibrary.services.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Author;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Authorship;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.db.repositories.bookmanagement.AuthorshipRepository;
import ua.boa.smartlibrary.exceptions.bookmanagement.AuthorshipNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorshipService {
    @Autowired
    private AuthorshipRepository repository;
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    public Authorship create(String authorRole, Integer bookId, Integer authorId) {
        Book book = bookService.get(bookId);
        Author author = authorService.get(authorId);
        return repository.save(new Authorship(authorRole, book, author));
    }

    public List<Authorship> getAll() {
        return repository.findAll();
    }

    public Authorship update(Integer id, String authorRole, Integer bookId, Integer authorId) {
        Book book = bookService.get(bookId);
        Author author = authorService.get(authorId);
        Authorship authorship = get(id);
        authorship.setAuthorRole(authorRole);
        authorship.setBook(book);
        authorship.setAuthor(author);
        return repository.save(authorship);
    }

    public void remove(Integer id) {
        Authorship authorship = get(id);
        repository.delete(authorship);
    }

    public List<Authorship> findByBook(Integer bookId) {
        Book book = bookService.get(bookId);
        return repository.findAuthorshipsByBook(book);
    }

    public List<Authorship> findByAuthor(Integer authorId) {
        Author author = authorService.get(authorId);
        return repository.findAuthorshipsByAuthor(author);
    }
    public List<Authorship> findByAuthorsAndBooks(List<Author> authors, List<Book> books) {
        return repository.findAuthorshipsByAuthorInAndBookIn(authors, books);
    }
    public Authorship get(Integer id) {
        Optional<Authorship> optional = repository.findById(id);
        if (optional.isEmpty()) throw new AuthorshipNotFoundException(id);
        return optional.get();
    }
}
