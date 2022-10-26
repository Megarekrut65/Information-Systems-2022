package ua.boa.smartlibrary.services.customermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.dataclasses.customermanagement.BookBorrowing;
import ua.boa.smartlibrary.dataclasses.customermanagement.Customer;
import ua.boa.smartlibrary.db.repositories.customermanagement.BookBorrowingRepository;
import ua.boa.smartlibrary.exceptions.BadDatabaseOperationException;
import ua.boa.smartlibrary.exceptions.customermanagement.BookBorrowingNotFoundException;
import ua.boa.smartlibrary.services.bookcirculationmanagement.StatisticService;
import ua.boa.smartlibrary.services.bookmanagement.BookService;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class BookBorrowingService {
    @Autowired
    private BookBorrowingRepository repository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BookService bookService;
    @Autowired
    private StatisticService statisticService;

    @Transactional
    public BookBorrowing create(Date dateOfBorrowing, Integer customerId, Integer bookId,
                                Date estimatedDateOfReturn, Date actualDateOfReturn, String comment) {
        Customer customer = customerService.get(customerId);
        Book book = bookService.get(bookId);
        BookBorrowing bookBorrowing = new BookBorrowing(dateOfBorrowing, customer, book,
                estimatedDateOfReturn, actualDateOfReturn, comment);
        try {
            statisticService.addBookBorrowing(bookBorrowing, 1);
        } catch (BadDatabaseOperationException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BadDatabaseOperationException(e.getMessage());
        }
        if (actualDateOfReturn != null) {
            try {
                statisticService.addBookReturned(bookBorrowing, 1);
            } catch (BadDatabaseOperationException e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new BadDatabaseOperationException(e.getMessage());
            }
        }
        return repository.save(bookBorrowing);
    }

    public List<BookBorrowing> getAll() {
        return repository.findAll();
    }

    @Transactional
    public BookBorrowing update(Integer id, Date dateOfBorrowing, Integer customerId, Integer bookId,
                                Date estimatedDateOfReturn, Date actualDateOfReturn, String comment) {
        Customer customer = customerService.get(customerId);
        Book book = bookService.get(bookId);
        BookBorrowing bookBorrowing = get(id);
        boolean otherBook = !bookBorrowing.getBook().equals(book);
        if(!Objects.equals(bookBorrowing.getDateOfBorrowing(), dateOfBorrowing) || otherBook){
            statisticService.addBookBorrowing(bookBorrowing, -1);
            Book save = bookBorrowing.getBook();
            bookBorrowing.setBook(book);
            bookBorrowing.setDateOfBorrowing(dateOfBorrowing);
            statisticService.addBookBorrowing(bookBorrowing, 1);
            bookBorrowing.setBook(save);
        }
        if(!Objects.equals(bookBorrowing.getActualDateOfReturn(),actualDateOfReturn) || otherBook){
            if(bookBorrowing.getActualDateOfReturn() != null){
                statisticService.addBookReturned(bookBorrowing, -1);
            }
            Book save = bookBorrowing.getBook();
            bookBorrowing.setBook(book);
            bookBorrowing.setActualDateOfReturn(actualDateOfReturn);
            statisticService.addBookReturned(bookBorrowing, 1);
            bookBorrowing.setBook(save);
        }
        bookBorrowing.setBook(book);
        bookBorrowing.setCustomer(customer);
        bookBorrowing.setComment(comment);
        bookBorrowing.setEstimatedDateOfReturn(estimatedDateOfReturn);
        return repository.save(bookBorrowing);
    }

    @Transactional
    public void remove(Integer id) {
        BookBorrowing bookBorrowing = get(id);
        statisticService.addBookBorrowing(bookBorrowing, -1);
        statisticService.addBookReturned(bookBorrowing, -1);
        repository.delete(bookBorrowing);
    }

    public List<BookBorrowing> findByDateOfBorrowingPeriod(Date dateOfBorrowingMin, Date dateOfBorrowingMax) {
        return repository.findBookBorrowingByDateOfBorrowingBetween(dateOfBorrowingMin, dateOfBorrowingMax);
    }

    public List<BookBorrowing> findByCustomer(Integer customerId) {
        Customer customer = customerService.get(customerId);
        return repository.findBookBorrowingByCustomer(customer);
    }
    public List<BookBorrowing> findReturnedByCustomer(Integer customerId) {
        Customer customer = customerService.get(customerId);
        return repository.findBookBorrowingByCustomerAndActualDateOfReturnNotNull(customer);
    }
    public List<BookBorrowing> findNotReturnedByCustomer(Integer customerId) {
        Customer customer = customerService.get(customerId);
        return repository.findBookBorrowingByCustomerAndActualDateOfReturnNull(customer);
    }

    public List<BookBorrowing> findByBook(Integer bookId) {
        Book book = bookService.get(bookId);
        return repository.findBookBorrowingByBook(book);
    }
    public List<BookBorrowing> findByBookReturned(Book book) {
        return repository.findBookBorrowingByBookAndActualDateOfReturnNotNull(book);
    }
    public List<BookBorrowing> findByBookNotReturned(Book book) {
        return repository.findBookBorrowingByBookAndActualDateOfReturnNull(book);
    }
    public BookBorrowing get(Integer id) {
        Optional<BookBorrowing> optional = repository.findById(id);
        if (optional.isEmpty()) throw new BookBorrowingNotFoundException(id);
        return optional.get();
    }
}
