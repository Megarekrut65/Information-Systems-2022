package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookWriteOff;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.db.repositories.bookcirculationmanagement.BookWriteOffRepository;
import ua.boa.smartlibrary.exceptions.BadDatabaseOperationException;
import ua.boa.smartlibrary.exceptions.bookcirculationmanagement.BookWriteOffNotFoundException;
import ua.boa.smartlibrary.services.bookmanagement.BookService;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookWriteOffService {
    @Autowired
    private BookWriteOffRepository repository;
    @Autowired
    private BookService bookService;
    @Autowired
    private StatisticService statisticService;

    @Transactional
    public BookWriteOff create(Date dateOfWriteOff, Integer bookId, Integer bookCount) {
        Book book = bookService.get(bookId);
        BookWriteOff bookWriteOff = new BookWriteOff(dateOfWriteOff, book, bookCount);
        try {
            statisticService.addBookWriteOff(bookWriteOff);
            return repository.save(bookWriteOff);
        } catch (BadDatabaseOperationException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return null;
    }

    public List<BookWriteOff> getAll() {
        return repository.findAll();
    }
    @Transactional
    public BookWriteOff update(Integer id, Date dateOfWriteOff, Integer bookId, Integer bookCount) {
        Book book = bookService.get(bookId);
        BookWriteOff bookWriteOff = get(id);
        bookWriteOff.setBookCount(-1 * bookWriteOff.getBookCount());
        try {
            statisticService.addBookWriteOff(bookWriteOff);
        } catch (BadDatabaseOperationException e) {
            e.printStackTrace();
            return null;
        }
        bookWriteOff.setDateOfWriteOff(dateOfWriteOff);
        bookWriteOff.setBook(book);
        bookWriteOff.setBookCount(bookCount);
        try {
            statisticService.addBookWriteOff(bookWriteOff);
        } catch (BadDatabaseOperationException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return null;
        }
        return repository.save(bookWriteOff);
    }
    @Transactional
    public void remove(Integer id) {
        BookWriteOff bookWriteOff = get(id);
        bookWriteOff.setBookCount(-1 * bookWriteOff.getBookCount());
        try {
            statisticService.addBookWriteOff(bookWriteOff);
            repository.delete(bookWriteOff);
        } catch (BadDatabaseOperationException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    public List<BookWriteOff> findByDateOfWriteOffPeriod(Date dateOfWriteOffMin, Date dateOfWriteOffMax) {
        return repository.findBookWriteOffsByDateOfWriteOffBetween(dateOfWriteOffMin, dateOfWriteOffMax);
    }

    public List<BookWriteOff> findByBook(Integer bookId) {
        Book book = bookService.get(bookId);
        return repository.findBookWriteOffsByBook(book);
    }

    public BookWriteOff get(Integer id) {
        Optional<BookWriteOff> optional = repository.findById(id);
        if (optional.isEmpty()) throw new BookWriteOffNotFoundException(id);
        return optional.get();
    }
}
