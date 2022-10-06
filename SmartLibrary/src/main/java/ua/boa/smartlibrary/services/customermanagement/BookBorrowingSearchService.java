package ua.boa.smartlibrary.services.customermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.dataclasses.customermanagement.BookBorrowing;
import ua.boa.smartlibrary.dataclasses.customermanagement.Customer;
import ua.boa.smartlibrary.db.repositories.customermanagement.BookBorrowingRepository;
import ua.boa.smartlibrary.services.bookmanagement.BookService;

import java.sql.Date;
import java.util.List;

@Service
public class BookBorrowingSearchService {
    @Autowired
    private BookBorrowingRepository bookBorrowingRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private CustomerService customerService;

    public List<BookBorrowing> findBookBorrowingsByAll(String bookTitle, String customerName, Date minDate, Date maxDate) {
        List<Book> books = bookTitle.equals("") ? bookService.getAll() : bookService.findByTitle(bookTitle);
        List<Customer> customers = customerName.equals("") ? customerService.getAll()
                : customerService.findByName(customerName);
        if (minDate != null && maxDate != null)
            return bookBorrowingRepository
                    .findBookBorrowingByDateOfBorrowingBetweenAndBookInAndCustomerIn(minDate, maxDate,
                            books, customers);
        if (minDate != null)
            return bookBorrowingRepository
                    .findBookBorrowingByDateOfBorrowingGreaterThanAndBookInAndCustomerIn(minDate,
                            books, customers);
        if (maxDate != null)
            return bookBorrowingRepository
                    .findBookBorrowingByDateOfBorrowingLessThanAndBookInAndCustomerIn(maxDate,
                            books, customers);
        return bookBorrowingRepository.findBookBorrowingByBookInAndCustomerIn(books, customers);
    }
}
