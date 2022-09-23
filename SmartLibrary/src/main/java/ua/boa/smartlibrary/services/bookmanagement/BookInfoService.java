package ua.boa.smartlibrary.services.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookDelivery;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookLost;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookWriteOff;
import ua.boa.smartlibrary.dataclasses.bookmanagement.BookInfo;
import ua.boa.smartlibrary.dataclasses.customermanagement.BookBorrowing;
import ua.boa.smartlibrary.db.repositories.bookmanagement.BookInfoRepository;
import ua.boa.smartlibrary.exceptions.bookmanagement.BookInfoNotFoundException;

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
        if (newTotal < 0) throw new IllegalStateException("Can't change delivery date " +
                "because total book count will be < 0");
        bookInfo.setTotalCount(newTotal);
        int newAvailable = bookInfo.getAvailableCount() + count;
        if (newAvailable < 0) throw new IllegalStateException("Can't change delivery date " +
                "because available book count will be < 0");
        bookInfo.setAvailableCount(newAvailable);
        bookInfo.setPurchasingCount(bookInfo.getPurchasingCount() + count);
        return repository.save(bookInfo);
    }

    public BookInfo addBookWriteOff(BookWriteOff bookWriteOff) {
        BookInfo bookInfo = get(bookWriteOff.getBook().getBookInfo().getId());
        int count = bookWriteOff.getBookCount();
        int newTotal = bookInfo.getTotalCount() - count;
        if (newTotal < 0) throw new IllegalStateException("Can't write-off books " +
                "because there is not enough total count!");
        bookInfo.setTotalCount(newTotal);
        int newAvailable = bookInfo.getAvailableCount() - count;
        if (newAvailable < 0) throw new IllegalStateException("Can't write-off books because there is " +
                "not enough available count! Some books are borrowed.");
        bookInfo.setAvailableCount(newAvailable);
        bookInfo.setWriteOffCount(bookInfo.getWriteOffCount() + count);
        return repository.save(bookInfo);
    }

    public BookInfo addBookLost(BookLost bookLost, boolean wasReturned) {
        BookInfo bookInfo = get(bookLost.getBook().getBookInfo().getId());
        int count = bookLost.getBookCount();
        int newTotal = bookInfo.getTotalCount() - count;
        if (newTotal < 0) throw new IllegalStateException("Can't add books as lost" +
                "because there is not enough total count!");
        bookInfo.setTotalCount(newTotal);
        if (wasReturned) {
            int newAvailable = bookInfo.getAvailableCount() - count;
            if (newAvailable < 0) throw new IllegalStateException("Can't add books as lost because there is " +
                    "not enough available count! Some books are borrowed.");
            bookInfo.setAvailableCount(newAvailable);
        } else {
            int newBorrowing = bookInfo.getBorrowingCount() - count;
            if (newBorrowing < 0) throw new IllegalStateException("Can't add books as lost because there is " +
                    "not enough available count! Some books are returned.");
            bookInfo.setBorrowingCount(newBorrowing);
        }
        bookInfo.setLostCount(bookInfo.getLostCount() + count);
        return repository.save(bookInfo);
    }

    public BookInfo startBookBorrowing(BookBorrowing bookBorrowing) {
        BookInfo bookInfo = get(bookBorrowing.getBook().getBookInfo().getId());
        int available = bookInfo.getAvailableCount();
        if (available == 0) throw new IllegalStateException("Can't borrow book because there is not any book!");
        bookInfo.setAvailableCount(available - 1);
        bookInfo.setBorrowingCount(bookInfo.getBorrowingCount() + 1);
        return repository.save(bookInfo);
    }

    public BookInfo finishBookBorrowing(BookBorrowing bookBorrowing) {
        BookInfo bookInfo = get(bookBorrowing.getBook().getBookInfo().getId());
        int borrowing = bookInfo.getBorrowingCount();
        if (borrowing == 0)
            throw new IllegalStateException("Can't return book because there is not any borrowed book!");
        bookInfo.setAvailableCount(bookInfo.getAvailableCount() + 1);
        bookInfo.setBorrowingCount(borrowing - 1);
        return repository.save(bookInfo);
    }
}
