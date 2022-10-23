package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.PageGetter;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookDelivery;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookLost;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.db.repositories.bookcirculationmanagement.BookLostRepository;
import ua.boa.smartlibrary.services.bookmanagement.BookService;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class BookLostSearchService extends PageGetter<BookLost> {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookLostRepository bookLostRepository;

    public List<BookLost> findBookLostsByAll(String bookTitle, Date minDate, Date maxDate) {
        List<Book> books = bookTitle.equals("") ? bookService.getAll() : bookService.findByTitle(bookTitle);
        if (minDate != null && maxDate != null)
            return bookLostRepository
                    .findBooksLostByDateOfLossBetweenAndBookIn(minDate, maxDate,
                            books);
        if (minDate != null)
            return bookLostRepository
                    .findBooksLostByDateOfLossGreaterThanAndBookIn(minDate,
                            books);
        if (maxDate != null)
            return bookLostRepository
                    .findBooksLostByDateOfLossLessThanAndBookIn(maxDate,
                            books);
        return bookLostRepository.findBooksLostByBookIn(books);
    }
    public List<BookLost> getPage(int page, int perPage, List<BookLost> bookLosts){
        List<BookLost> list = getPart(bookLosts, page, perPage,
                Comparator.comparing(BookLost::getDateOfLoss));
        Collections.reverse(list);
        return list;
    }
}
