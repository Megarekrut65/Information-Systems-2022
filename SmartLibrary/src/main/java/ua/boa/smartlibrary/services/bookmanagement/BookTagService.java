package ua.boa.smartlibrary.services.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.dataclasses.bookmanagement.BookTag;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Tag;
import ua.boa.smartlibrary.db.repositories.bookmanagement.BookTagRepository;
import ua.boa.smartlibrary.exceptions.bookmanagement.BookTagNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class BookTagService {
    @Autowired
    private BookTagRepository repository;
    @Autowired
    private BookService bookService;
    @Autowired
    private TagService tagService;

    public BookTag create(Integer bookId, Integer tagId) {
        Book book = bookService.get(bookId);
        Tag tag = tagService.get(tagId);
        return repository.save(new BookTag(book, tag));
    }

    public List<BookTag> getAll() {
        return repository.findAll();
    }

    public BookTag update(Integer id, Integer bookId, Integer tagId) {
        Book book = bookService.get(bookId);
        Tag tag = tagService.get(tagId);
        BookTag bookTag = get(id);
        bookTag.setBook(book);
        bookTag.setTag(tag);
        return repository.save(bookTag);
    }

    public void remove(Integer id) {
        BookTag bookTag = get(id);
        repository.delete(bookTag);
    }

    public List<BookTag> findByBook(Integer bookId) {
        Book book = bookService.get(bookId);
        return repository.findBookTagsByBook(book);
    }

    public List<BookTag> findByTag(Integer tagId) {
        Tag tag = tagService.get(tagId);
        return repository.findBookTagsByTag(tag);
    }

    public List<BookTag> findByTagsAndBooks(List<Tag> tags, List<Book> books) {
        return repository.findBookTagsByTagInAndBookIn(tags, books);
    }

    public BookTag get(Integer id) {
        Optional<BookTag> optional = repository.findById(id);
        if (optional.isEmpty()) throw new BookTagNotFoundException(id);
        return optional.get();
    }
}
