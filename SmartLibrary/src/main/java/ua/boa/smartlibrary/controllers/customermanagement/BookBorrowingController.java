package ua.boa.smartlibrary.controllers.customermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.Utilities;
import ua.boa.smartlibrary.dataclasses.customermanagement.BookBorrowing;
import ua.boa.smartlibrary.services.customermanagement.BookBorrowingSearchService;
import ua.boa.smartlibrary.services.customermanagement.BookBorrowingService;

import java.util.List;

@RestController
public class BookBorrowingController {
    @Autowired
    private BookBorrowingService service;
    @Autowired
    private BookBorrowingSearchService bookBorrowingSearchService;

    @RequestMapping(value = "/book-borrowing", method = RequestMethod.POST)
    public BookBorrowing create(@RequestParam("date-of-borrowing") String dateOfBorrowing,
                                @RequestParam("customer-id") String customerId,
                                @RequestParam("book-id") String bookId,
                                @RequestParam("estimated-date-of-return") String estimatedDateOfReturn,
                                @RequestParam("actual-date-of-return") String actualDateOfReturn,
                                @RequestParam("comment") String comment) {
        return service.create(Utilities.getDate(dateOfBorrowing), Integer.parseInt(customerId),
                Integer.parseInt(bookId), Utilities.getDate(estimatedDateOfReturn),
                Utilities.getDate(actualDateOfReturn), comment);
    }

    @RequestMapping(value = "/book-borrowing", method = RequestMethod.PUT)
    public BookBorrowing update(@RequestParam("id") String id,
                                @RequestParam("date-of-borrowing") String dateOfBorrowing,
                                @RequestParam("customer-id") String customerId,
                                @RequestParam("book-id") String bookId,
                                @RequestParam("estimated-date-of-return") String estimatedDateOfReturn,
                                @RequestParam("actual-date-of-return") String actualDateOfReturn,
                                @RequestParam("comment") String comment) {
        return service.update(Integer.parseInt(id), Utilities.getDate(dateOfBorrowing), Integer.parseInt(customerId),
                Integer.parseInt(bookId), Utilities.getDate(estimatedDateOfReturn),
                Utilities.getDate(actualDateOfReturn), comment);
    }

    @RequestMapping(value = "/book-borrowings", method = RequestMethod.GET)
    public List<BookBorrowing> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/book-borrowing", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/book-borrowings/by-date-of-borrowing-period", method = RequestMethod.GET)
    public List<BookBorrowing> searchByDatePeriod(@RequestParam("date-of-borrowing-min") String dateOfBorrowingMin,
                                                  @RequestParam("date-of-borrowing-max") String dateOfBorrowingMax) {
        return service.findByDateOfBorrowingPeriod(Utilities.getDate(dateOfBorrowingMin),
                Utilities.getDate(dateOfBorrowingMax));
    }

    @RequestMapping(value = "/book-borrowings/by-book-id", method = RequestMethod.GET)
    public List<BookBorrowing> searchByBook(@RequestParam("book-id") String bookId) {
        return service.findByBook(Integer.parseInt(bookId));
    }

    @RequestMapping(value = "/book-borrowings/by-customer-id", method = RequestMethod.GET)
    public List<BookBorrowing> searchByCustomer(@RequestParam("customer-id") String customerId) {
        return service.findByCustomer(Integer.parseInt(customerId));
    }

    @RequestMapping(value = "/book-borrowing", method = RequestMethod.GET)
    public BookBorrowing get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }
    @RequestMapping(value = "/book-borrowings/by-all", method = RequestMethod.GET)
    public List<BookBorrowing> searchByAll(@RequestParam("book-title") String bookTitle,
                                           @RequestParam("customer-name") String customerName,
                                           @RequestParam("date-of-borrowing-min") String dateOfBorrowingMin,
                                                  @RequestParam("date-of-borrowing-max") String dateOfBorrowingMax) {
        return bookBorrowingSearchService.findBookBorrowingsByAll(bookTitle, customerName, Utilities.getDate(dateOfBorrowingMin),
                Utilities.getDate(dateOfBorrowingMax));
    }
}
