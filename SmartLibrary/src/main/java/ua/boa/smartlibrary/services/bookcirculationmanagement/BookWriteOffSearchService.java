package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.PageGetter;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookLost;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookWriteOff;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.db.repositories.bookcirculationmanagement.BookWriteOffRepository;
import ua.boa.smartlibrary.services.bookmanagement.BookService;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class BookWriteOffSearchService extends PageGetter<BookWriteOff> {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookWriteOffRepository bookWriteOffRepository;

    public List<BookWriteOff> findBookWriteOffsByAll(String bookTitle, Date minDate, Date maxDate) {
        List<Book> books = bookTitle.equals("") ? bookService.getAll() : bookService.findByTitle(bookTitle);
        if (minDate != null && maxDate != null)
            return bookWriteOffRepository
                    .findBookWriteOffsByDateOfWriteOffBetweenAndBookIn(minDate, maxDate,
                            books);
        if (minDate != null)
            return bookWriteOffRepository
                    .findBookWriteOffsByDateOfWriteOffGreaterThanAndBookIn(minDate,
                            books);
        if (maxDate != null)
            return bookWriteOffRepository
                    .findBookWriteOffsByDateOfWriteOffLessThanAndBookIn(maxDate,
                            books);
        return bookWriteOffRepository.findBookWriteOffsByBookIn(books);
    }
    public List<BookWriteOff> getPage(int page, int perPage, List<BookWriteOff> bookWriteOffs){
        List<BookWriteOff> list = getPart(bookWriteOffs, page, perPage,
                Comparator.comparing(BookWriteOff::getDateOfWriteOff));
        Collections.reverse(list);
        return list;
    }
}
