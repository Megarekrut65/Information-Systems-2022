package ua.boa.smartlibrary.services.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.PageGetter;
import ua.boa.smartlibrary.dataclasses.bookmanagement.*;
import ua.boa.smartlibrary.dataclasses.customermanagement.BookBorrowing;
import ua.boa.smartlibrary.services.customermanagement.BookBorrowingService;

import java.util.*;

@Service
public class BookSearchService extends PageGetter<Book> {
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
    private int getPurchasingStatus(Book book){
        int status = 0;
        BookInfo bookInfo = book.getBookInfo();
        if(bookInfo.getTotalCount() == 0) status+=1;
        else if(bookInfo.getAvailableCount() == 0) status+=1;
        if(bookInfo.getBorrowingCount() - bookInfo.getReturnedCount() > bookInfo.getAvailableCount())
            status += 20/(bookInfo.getAvailableCount()+1);
        return status;
    }
    public List<Book> getBooksToPurchase(int count){
        List<Book> books = bookService.getBookToPurchase();
        books.sort(Comparator.comparing(this::getPurchasingStatus));
        Collections.reverse(books);
        books = books.stream().filter((book) -> getPurchasingStatus(book) > 0).toList();
        return books.subList(0, Math.min(count, books.size()));
    }

    public List<Book> getPage(int page, int perPage, List<Book> books) {
        return getPart(books, page, perPage, Comparator.comparing(Book::getTitle));
    }
}
