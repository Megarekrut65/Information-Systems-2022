package ua.boa.smartlibrary.services.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookmanagement.*;
import ua.boa.smartlibrary.dataclasses.customermanagement.BookBorrowing;
import ua.boa.smartlibrary.services.customermanagement.BookBorrowingService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookSearchService {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookGenreService bookGenreService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private BookTagService bookTagService;
    @Autowired
    private TagService tagService;
    @Autowired
    private AuthorshipService authorshipService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookBorrowingService bookBorrowingService;

    public List<Book> findBookByAll(String bookTitle, String genreName, String tagName, String authorName) {
        List<Book> books = bookTitle.equals("") ? bookService.getAll() : bookService.findByTitle(bookTitle);
        if (!genreName.equals("")) {
            List<BookGenre> bookGenres = bookGenreService.findByGenresAndBooks(genreService.findByName(genreName), books);
            books.clear();
            bookGenres.forEach(bookGenre -> books.add(bookGenre.getBook()));
        }
        if (!tagName.equals("")) {
            List<BookTag> bookTags = bookTagService.findByTagsAndBooks(tagService.findByName(tagName), books);
            books.clear();
            bookTags.forEach(bookTag -> books.add(bookTag.getBook()));
        }
        if (!authorName.equals("")) {
            List<Authorship> authorships = authorshipService.findByAuthorsAndBooks(authorService.findByName(authorName), books);
            books.clear();
            authorships.forEach(authorship -> books.add(authorship.getBook()));
        }
        Set<Book> set = new HashSet<>(books);
        books.clear();
        books.addAll(set);
        return books;
    }
    public List<Book> getBooksToPurchase(){
        List<Book> books = bookService.getAll();
        for(Book book:books){
            BookInfo bookInfo = book.getBookInfo();
            List<BookBorrowing> bookBorrowings = bookBorrowingService.findByBook(book.getId());

        }
        return books;
    }
}
