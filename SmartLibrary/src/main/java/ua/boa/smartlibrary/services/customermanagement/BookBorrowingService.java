package ua.boa.smartlibrary.services.customermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.dataclasses.customermanagement.BookBorrowing;
import ua.boa.smartlibrary.dataclasses.customermanagement.Customer;
import ua.boa.smartlibrary.db.repositories.customermanagement.BookBorrowingRepository;
import ua.boa.smartlibrary.exceptions.customermanagement.BookBorrowingNotFoundException;
import ua.boa.smartlibrary.services.bookmanagement.BookService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookBorrowingService {
    @Autowired
    private BookBorrowingRepository repository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BookService bookService;

    public BookBorrowing create(Date dateOfBorrowing, Integer customerId, Integer bookId,
                                Date estimatedDateOfReturn, Date actualDateOfReturn, String comment) {
        Customer customer = customerService.get(customerId);
        Book book = bookService.get(bookId);
        return repository.save(
                new BookBorrowing(dateOfBorrowing, customer, book, estimatedDateOfReturn, actualDateOfReturn, comment));
    }

    public List<BookBorrowing> getAll() {
        return repository.findAll();
    }

    public BookBorrowing update(Integer id, Date dateOfBorrowing, Integer customerId, Integer bookId,
                                Date estimatedDateOfReturn, Date actualDateOfReturn, String comment) {
        Customer customer = customerService.get(customerId);
        Book book = bookService.get(bookId);
        BookBorrowing bookBorrowing = get(id);
        bookBorrowing.setDateOfBorrowing(dateOfBorrowing);
        bookBorrowing.setCustomer(customer);
        bookBorrowing.setBook(book);
        bookBorrowing.setEstimatedDateOfReturn(estimatedDateOfReturn);
        bookBorrowing.setActualDateOfReturn(actualDateOfReturn);
        bookBorrowing.setComment(comment);
        return repository.save(bookBorrowing);
    }

    public void remove(Integer id) {
        BookBorrowing bookBorrowing = get(id);
        repository.delete(bookBorrowing);
    }

    public List<BookBorrowing> findByDateOfBorrowingPeriod(Date dateOfBorrowingMin, Date dateOfBorrowingMax) {
        return repository.findBookBorrowingByDateOfBorrowingBetween(dateOfBorrowingMin, dateOfBorrowingMax);
    }

    public List<BookBorrowing> findByCustomer(Integer customerId) {
        Customer customer = customerService.get(customerId);
        return repository.findBookBorrowingByCustomer(customer);
    }

    public List<BookBorrowing> findByBook(Integer bookId) {
        Book book = bookService.get(bookId);
        return repository.findBookBorrowingByBook(book);
    }

    public BookBorrowing get(Integer id) {
        Optional<BookBorrowing> optional = repository.findById(id);
        if (optional.isEmpty()) throw new BookBorrowingNotFoundException(id);
        return optional.get();
    }
}
