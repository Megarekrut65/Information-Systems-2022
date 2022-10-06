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
            statisticService.startBorrowing(bookBorrowing);
        } catch (BadDatabaseOperationException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BadDatabaseOperationException(e.getMessage());
        }
        if (actualDateOfReturn != null) {
            try {
                statisticService.finishBorrowing(bookBorrowing);
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
        boolean needToStart = false, needToFinish = false;
        if (!Objects.equals(bookBorrowing.getActualDateOfReturn(), actualDateOfReturn)
                || !bookBorrowing.getBook().equals(book)) {
            needToFinish = true;
            undoFinishBorrowing(bookBorrowing);
        }
        if (!bookBorrowing.getDateOfBorrowing().equals(dateOfBorrowing)
                || !bookBorrowing.getBook().equals(book)) {
            needToStart = true;
            undoStartBorrowing(bookBorrowing);
        }
        bookBorrowing.setDateOfBorrowing(dateOfBorrowing);
        bookBorrowing.setCustomer(customer);
        bookBorrowing.setBook(book);
        bookBorrowing.setEstimatedDateOfReturn(estimatedDateOfReturn);
        bookBorrowing.setActualDateOfReturn(actualDateOfReturn);
        bookBorrowing.setComment(comment);
        if (needToStart) {
            try {
                statisticService.startBorrowing(bookBorrowing);
            } catch (BadDatabaseOperationException e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new BadDatabaseOperationException(e.getMessage());
            }
        }
        if (needToFinish && bookBorrowing.getActualDateOfReturn() != null) {
            try {
                statisticService.finishBorrowing(bookBorrowing);
            } catch (BadDatabaseOperationException e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new BadDatabaseOperationException(e.getMessage());
            }
        }
        return repository.save(bookBorrowing);
    }

    private void undoStartBorrowing(BookBorrowing bookBorrowing) {
        BookBorrowing toFinish = new BookBorrowing();
        toFinish.setActualDateOfReturn(bookBorrowing.getDateOfBorrowing());
        toFinish.setBook(bookBorrowing.getBook());
        try {
            if (bookBorrowing.getActualDateOfReturn() == null) statisticService.finishBorrowing(toFinish);
            else statisticService.finishBorrowingStatisticOnly(toFinish);
        } catch (BadDatabaseOperationException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BadDatabaseOperationException(e.getMessage());
        }
    }

    private void undoFinishBorrowing(BookBorrowing bookBorrowing) {
        if (bookBorrowing.getActualDateOfReturn() != null) {
            BookBorrowing toStart = new BookBorrowing();
            toStart.setDateOfBorrowing(bookBorrowing.getActualDateOfReturn());
            toStart.setBook(bookBorrowing.getBook());
            try {
                statisticService.startBorrowing(toStart);
            } catch (BadDatabaseOperationException e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new BadDatabaseOperationException(e.getMessage());
            }
        }
    }

    @Transactional
    public void remove(Integer id) {
        BookBorrowing bookBorrowing = get(id);
        undoFinishBorrowing(bookBorrowing);
        undoStartBorrowing(bookBorrowing);
        repository.delete(bookBorrowing);
    }

    public List<BookBorrowing> findByDateOfBorrowingPeriod(Date dateOfBorrowingMin, Date dateOfBorrowingMax) {
        return repository.findBookBorrowingByDateOfBorrowingBetween(dateOfBorrowingMin, dateOfBorrowingMax);
    }

    public List<BookBorrowing> findByCustomer(Integer customerId) {
        Customer customer = customerService.get(customerId);
        return repository.findBookBorrowingByCustomer(customer);
    }

    public List<BookBorrowing> findNotReturnedByCustomer(Integer customerId) {
        Customer customer = customerService.get(customerId);
        return repository.findBookBorrowingByCustomerAndActualDateOfReturnEquals(customer, null);
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
