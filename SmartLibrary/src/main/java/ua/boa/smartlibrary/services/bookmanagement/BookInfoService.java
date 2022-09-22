package ua.boa.smartlibrary.services.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookDelivery;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookLost;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookWriteOff;
import ua.boa.smartlibrary.dataclasses.bookmanagement.BookInfo;
import ua.boa.smartlibrary.db.repositories.bookmanagement.BookInfoRepository;
import ua.boa.smartlibrary.exceptions.bookmanagement.BookInfoNotFoundException;

import java.util.Optional;

@Service
public class BookInfoService {
    @Autowired
    private BookInfoRepository repository;

    public BookInfo create(){
        return repository.save(new BookInfo());
    }
    public void remove(Integer id){
        BookInfo bookInfo = get(id);
        repository.delete(bookInfo);
    }
    public BookInfo get(Integer id){
        Optional<BookInfo> optional = repository.findById(id);
        if(optional.isEmpty()) throw new BookInfoNotFoundException(id);
        return optional.get();
    }
    public BookInfo addBookDelivery(Integer id, BookDelivery bookDelivery){
        BookInfo bookInfo = get(id);
        bookInfo.setCurrentCount(bookInfo.getCurrentCount() + bookDelivery.getBookCount());
        bookInfo.setAvailableCount(bookInfo.getAvailableCount() + bookDelivery.getBookCount());
        bookInfo.setPurchasingCount(bookInfo.getPurchasingCount() + bookDelivery.getBookCount());
        return repository.save(bookInfo);
    }
    public BookInfo addBookWriteOff(Integer id, BookWriteOff bookWriteOff){
        BookInfo bookInfo = get(id);
        bookInfo.setCurrentCount(bookInfo.getCurrentCount() - bookWriteOff.getBookCount());
        bookInfo.setAvailableCount(bookInfo.getAvailableCount() - bookWriteOff.getBookCount());
        bookInfo.setWriteOffCount(bookInfo.getWriteOffCount() + bookWriteOff.getBookCount());
        return repository.save(bookInfo);
    }
    public BookInfo addBookLost(Integer id, BookLost bookLost){
        BookInfo bookInfo = get(id);
        bookInfo.setCurrentCount(bookInfo.getCurrentCount() - bookLost.getBookCount());
        bookInfo.setAvailableCount(bookInfo.getAvailableCount() - bookLost.getBookCount());
        bookInfo.setLostCount(bookInfo.getLostCount() + bookLost.getBookCount());
        return repository.save(bookInfo);
    }
}
