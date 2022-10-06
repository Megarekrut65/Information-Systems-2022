package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookDelivery;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.db.repositories.bookcirculationmanagement.BookDeliveryRepository;
import ua.boa.smartlibrary.services.bookmanagement.BookService;

import java.sql.Date;
import java.util.List;

@Service
public class BookDeliverySearchService {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookDeliveryRepository bookDeliveryRepository;

    public List<BookDelivery> findBookDeliveriesByAll(String bookTitle, Date minDate, Date maxDate){
        List<Book> books = bookTitle.equals("")?bookService.getAll():bookService.findByTitle(bookTitle);
        if(minDate != null && maxDate != null)
            return bookDeliveryRepository
                    .findBookDeliveriesByDelivery_DateOfDeliveryBetweenAndBookIn(minDate, maxDate,
                            books);
        if(minDate != null)
            return bookDeliveryRepository
                    .findBookDeliveriesByDelivery_DateOfDeliveryGreaterThanAndBookIn(minDate,
                            books);
        if(maxDate != null)
            return bookDeliveryRepository
                    .findBookDeliveriesByDelivery_DateOfDeliveryLessThanAndBookIn(maxDate,
                            books);
        return bookDeliveryRepository.findBookDeliveriesByBookIn(books);
    }
}
