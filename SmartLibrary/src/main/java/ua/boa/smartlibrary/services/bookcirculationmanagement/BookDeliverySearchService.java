package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.PageGetter;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookDelivery;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.db.repositories.bookcirculationmanagement.BookDeliveryRepository;
import ua.boa.smartlibrary.services.bookmanagement.BookService;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class BookDeliverySearchService extends PageGetter<BookDelivery> {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookDeliveryRepository bookDeliveryRepository;

    public List<BookDelivery> findBookDeliveriesByAll(String bookTitle, Date minDate, Date maxDate) {
        List<Book> books = bookTitle.equals("") ? bookService.getAll() : bookService.findByTitle(bookTitle);
        if (minDate != null && maxDate != null)
            return bookDeliveryRepository
                    .findBookDeliveriesByDelivery_DateOfDeliveryBetweenAndBookIn(minDate, maxDate,
                            books);
        if (minDate != null)
            return bookDeliveryRepository
                    .findBookDeliveriesByDelivery_DateOfDeliveryGreaterThanAndBookIn(minDate,
                            books);
        if (maxDate != null)
            return bookDeliveryRepository
                    .findBookDeliveriesByDelivery_DateOfDeliveryLessThanAndBookIn(maxDate,
                            books);
        return bookDeliveryRepository.findBookDeliveriesByBookIn(books);
    }
    public List<BookDelivery> getPage(int page, int perPage, List<BookDelivery> bookDeliveries){
        List<BookDelivery> list = getPart(bookDeliveries, page, perPage,
                Comparator.comparing((BookDelivery bookDelivery) -> bookDelivery.getDelivery().getDateOfDelivery()));
        Collections.reverse(list);
        return list;
    }
}
