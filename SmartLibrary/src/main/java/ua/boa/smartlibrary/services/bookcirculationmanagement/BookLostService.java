package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookLost;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.db.repositories.bookcirculationmanagement.BookLostRepository;
import ua.boa.smartlibrary.exceptions.BadDatabaseOperationException;
import ua.boa.smartlibrary.exceptions.bookcirculationmanagement.BookLostNotFoundException;
import ua.boa.smartlibrary.services.bookmanagement.BookService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class BookLostService {
    @Autowired
    private BookLostRepository repository;
    @Autowired
    private BookService bookService;
    @Autowired
    private StatisticService statisticService;

    @Transactional
    public BookLost create(Integer bookCount, Integer bookId, String causeOfLoss, Date dateOfLoss, Boolean wasReturned) {
        Book book = bookService.get(bookId);
        BookLost bookLost = new BookLost(bookCount, book, causeOfLoss, dateOfLoss, wasReturned);
        try {
            statisticService.addBookLost(bookLost);
            return repository.save(bookLost);
        } catch (BadDatabaseOperationException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BadDatabaseOperationException(e.getMessage());
        }
    }

    public List<BookLost> getAll() {
        return repository.findAll();
    }

    @Transactional
    public BookLost update(Integer id, Integer bookCount, Integer bookId, String causeOfLoss,
                           Date dateOfLoss, Boolean wasReturned) {
        Book book = bookService.get(bookId);
        BookLost bookLost = get(id);
        boolean needAddAgain = false;
        if (!bookLost.getBookCount().equals(bookCount)
                || !bookLost.getBook().equals(book)
                || !bookLost.getDateOfLoss().equals(dateOfLoss)
                || !bookLost.getWasReturned().equals(wasReturned)) {
            needAddAgain = true;
            bookLost.setBookCount(-1 * bookLost.getBookCount());
            try {
                statisticService.addBookLost(bookLost);
            } catch (BadDatabaseOperationException e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new BadDatabaseOperationException(e.getMessage());
            }
        }
        bookLost.setBookCount(bookCount);
        bookLost.setBook(book);
        bookLost.setCauseOfLoss(causeOfLoss);
        bookLost.setDateOfLoss(dateOfLoss);
        bookLost.setWasReturned(wasReturned);
        if (needAddAgain) {
            try {
                statisticService.addBookLost(bookLost);
            } catch (BadDatabaseOperationException e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new BadDatabaseOperationException(e.getMessage());
            }
        }
        return repository.save(bookLost);
    }

    @Transactional
    public void remove(Integer id) {
        BookLost bookLost = get(id);
        bookLost.setBookCount(-1 * bookLost.getBookCount());
        try {
            statisticService.addBookLost(bookLost);
            repository.delete(bookLost);
        } catch (BadDatabaseOperationException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BadDatabaseOperationException(e.getMessage());
        }
    }

    public List<BookLost> findByDateOfLossPeriod(Date dateOfLossMin, Date dateOfLossMax) {
        return repository.findBooksLostByDateOfLossBetween(dateOfLossMin, dateOfLossMax);
    }

    public List<BookLost> findByBook(Integer bookId) {
        Book book = bookService.get(bookId);
        return repository.findBooksLostByBook(book);
    }

    public BookLost get(Integer id) {
        Optional<BookLost> optional = repository.findById(id);
        if (optional.isEmpty()) throw new BookLostNotFoundException(id);
        return optional.get();
    }
}
