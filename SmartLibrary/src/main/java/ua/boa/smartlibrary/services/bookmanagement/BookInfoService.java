package ua.boa.smartlibrary.services.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookDelivery;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookLost;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookWriteOff;
import ua.boa.smartlibrary.dataclasses.bookmanagement.BookInfo;
import ua.boa.smartlibrary.dataclasses.customermanagement.BookBorrowing;
import ua.boa.smartlibrary.db.repositories.bookmanagement.BookInfoRepository;
import ua.boa.smartlibrary.exceptions.BadDatabaseOperationException;
import ua.boa.smartlibrary.exceptions.bookmanagement.BookInfoNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class BookInfoService {
    @Autowired
    private BookInfoRepository repository;

    public BookInfo create() {
        return repository.save(new BookInfo());
    }

    public void remove(Integer id) {
        BookInfo bookInfo = get(id);
        repository.delete(bookInfo);
    }
    public BookInfo get(Integer id) {
        Optional<BookInfo> optional = repository.findById(id);
        if (optional.isEmpty()) throw new BookInfoNotFoundException(id);
        return optional.get();
    }
    public BookInfo addBookDelivery(BookDelivery bookDelivery) {
        BookInfo bookInfo = get(bookDelivery.getBook().getBookInfo().getId());
        int count = bookDelivery.getBookCount();
        int newTotal = bookInfo.getTotalCount() + count;
        bookInfo.setTotalCount(newTotal);
        int newAvailable = bookInfo.getAvailableCount() + count;
        bookInfo.setAvailableCount(newAvailable);
        bookInfo.setPurchasingCount(bookInfo.getPurchasingCount() + count);
        return repository.save(bookInfo);
    }

    public BookInfo addBookWriteOff(BookWriteOff bookWriteOff) {
        BookInfo bookInfo = get(bookWriteOff.getBook().getBookInfo().getId());
        int count = bookWriteOff.getBookCount();
        int newTotal = bookInfo.getTotalCount() - count;
        if (newTotal < 0) throw new BadDatabaseOperationException("Can't write-off books " +
                "because there is not enough total count!");
        bookInfo.setTotalCount(newTotal);
        int newAvailable = bookInfo.getAvailableCount() - count;
        if (newAvailable < 0) throw new BadDatabaseOperationException("Can't write-off books because there is " +
                "not enough available count! Some books are borrowed.");
        bookInfo.setAvailableCount(newAvailable);
        int newWriteOff = bookInfo.getWriteOffCount() + count;
        if (newWriteOff < 0) throw new BadDatabaseOperationException("Can't undo write-off books because there is " +
                "not enough write-off count!");
        bookInfo.setWriteOffCount(newWriteOff);
        return repository.save(bookInfo);
    }

    public BookInfo addBookLost(BookLost bookLost) {
        BookInfo bookInfo = get(bookLost.getBook().getBookInfo().getId());
        int count = bookLost.getBookCount();
        int newTotal = bookInfo.getTotalCount() - count;
        if (newTotal < 0) throw new BadDatabaseOperationException("Can't add books as lost" +
                "because there is not enough total count!");
        bookInfo.setTotalCount(newTotal);
        if (bookLost.getWasReturned()) {
            int newAvailable = bookInfo.getAvailableCount() - count;
            if (newAvailable < 0) throw new BadDatabaseOperationException("Can't add books as lost because there is " +
                    "not enough available count! Some books are borrowed.");
            bookInfo.setAvailableCount(newAvailable);
        } else {
            int newBorrowing = bookInfo.getBorrowingCount() - count;
            if (newBorrowing < 0) throw new BadDatabaseOperationException("Can't add books as lost because there is " +
                    "not enough available count! Some books are returned.");
            bookInfo.setBorrowingCount(newBorrowing);
        }
        int newLost = bookInfo.getLostCount() + count;
        if (newLost < 0) throw new BadDatabaseOperationException("Can't undo loss books because there is " +
                "not enough loss count!");
        bookInfo.setLostCount(newLost);
        return repository.save(bookInfo);
    }

    public BookInfo addBookBorrowing(BookBorrowing bookBorrowing, int value) {
        BookInfo bookInfo = get(bookBorrowing.getBook().getBookInfo().getId());
        int available = bookInfo.getAvailableCount();
        if (available - value < 0) throw new BadDatabaseOperationException("Can't borrow book because there is not any book!");
        bookInfo.setAvailableCount(available - value);
        bookInfo.setBorrowingCount(bookInfo.getBorrowingCount() + value);
        return repository.save(bookInfo);
    }

    public BookInfo addBookReturned(BookBorrowing bookBorrowing, int value) {
        BookInfo bookInfo = get(bookBorrowing.getBook().getBookInfo().getId());
        bookInfo.setAvailableCount(bookInfo.getAvailableCount() + value);
        bookInfo.setReturnedCount(bookInfo.getReturnedCount() + value);
        return repository.save(bookInfo);
    }
}
