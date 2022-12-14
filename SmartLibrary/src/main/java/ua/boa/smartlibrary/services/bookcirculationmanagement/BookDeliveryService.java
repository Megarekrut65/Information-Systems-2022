package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookDelivery;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Delivery;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.db.repositories.bookcirculationmanagement.BookDeliveryRepository;
import ua.boa.smartlibrary.exceptions.BadDatabaseOperationException;
import ua.boa.smartlibrary.exceptions.bookcirculationmanagement.BookDeliveryNotFoundException;
import ua.boa.smartlibrary.services.bookmanagement.BookService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookDeliveryService {
    @Autowired
    private BookDeliveryRepository repository;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private BookService bookService;
    @Autowired
    private StatisticService statisticService;

    @Transactional
    public BookDelivery create(Integer deliveryId, Integer bookId, Integer bookCount, Integer bookPrice) {
        Delivery delivery = deliveryService.get(deliveryId);
        Book book = bookService.get(bookId);
        BookDelivery bookDelivery = new BookDelivery(delivery, book, bookCount, bookPrice);
        try {
            statisticService.addBookDelivery(bookDelivery);
            return repository.save(bookDelivery);
        } catch (BadDatabaseOperationException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BadDatabaseOperationException(e.getMessage());
        }
    }

    public List<BookDelivery> getAll() {
        return repository.findAll();
    }

    @Transactional
    public BookDelivery update(Integer id, Integer deliveryId, Integer bookId, Integer bookCount, Integer bookPrice) {
        Delivery delivery = deliveryService.get(deliveryId);
        Book book = bookService.get(bookId);
        BookDelivery bookDelivery = get(id);
        boolean needAddAgain = false;
        if (!bookDelivery.getDelivery().equals(delivery)
                || !bookDelivery.getBookCount().equals(bookCount)
                || !bookDelivery.getBook().equals(book)) {
            needAddAgain = true;
            bookDelivery.setBookCount(-1 * bookDelivery.getBookCount());
            try {
                statisticService.addBookDelivery(bookDelivery);
            } catch (BadDatabaseOperationException e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new BadDatabaseOperationException(e.getMessage());
            }
        }
        bookDelivery.setDelivery(delivery);
        bookDelivery.setBook(book);
        bookDelivery.setBookCount(bookCount);
        bookDelivery.setBookPrice(bookPrice);
        if (needAddAgain) {
            try {
                statisticService.addBookDelivery(bookDelivery);
            } catch (BadDatabaseOperationException e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new BadDatabaseOperationException(e.getMessage());
            }
        }
        return repository.save(bookDelivery);
    }

    @Transactional
    public void remove(Integer id) {
        BookDelivery bookDelivery = get(id);
        bookDelivery.setBookCount(-1 * bookDelivery.getBookCount());
        try {
            statisticService.addBookDelivery(bookDelivery);
            repository.delete(bookDelivery);
        } catch (BadDatabaseOperationException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BadDatabaseOperationException(e.getMessage());
        }
    }

    public List<BookDelivery> findByDelivery(Integer deliveryId) {
        Delivery delivery = deliveryService.get(deliveryId);
        return repository.findBookDeliveriesByDelivery(delivery);
    }

    public List<BookDelivery> findByBook(Integer bookId) {
        Book book = bookService.get(bookId);
        return repository.findBookDeliveriesByBook(book);
    }

    public BookDelivery get(Integer id) {
        Optional<BookDelivery> optional = repository.findById(id);
        if (optional.isEmpty()) throw new BookDeliveryNotFoundException(id);
        return optional.get();
    }
}
