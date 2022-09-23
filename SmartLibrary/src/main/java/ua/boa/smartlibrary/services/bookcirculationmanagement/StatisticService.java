package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookDelivery;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookLost;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookWriteOff;
import ua.boa.smartlibrary.dataclasses.customermanagement.BookBorrowing;
import ua.boa.smartlibrary.services.bookmanagement.BookInfoService;

@Service
public class StatisticService {
    @Autowired
    private BookInfoService bookInfoService;
    @Autowired
    private MonthStatisticService monthStatisticService;

    public void addBookDelivery(BookDelivery bookDelivery) {
        bookInfoService.addBookDelivery(bookDelivery);
        monthStatisticService.addBookDelivery(bookDelivery);
    }

    public void addBookWriteOff(BookWriteOff bookWriteOff) {
        bookInfoService.addBookWriteOff(bookWriteOff);
        monthStatisticService.addBookWriteOff(bookWriteOff);
    }

    public void addBookLost(BookLost bookLost) {
        bookInfoService.addBookLost(bookLost);
        monthStatisticService.addBookLost(bookLost);
    }

    public void startBorrowing(BookBorrowing bookBorrowing) {
        bookInfoService.startBookBorrowing(bookBorrowing);
        monthStatisticService.startBookBorrowing(bookBorrowing);
    }

    public void finishBorrowing(BookBorrowing bookBorrowing) {
        bookInfoService.finishBookBorrowing(bookBorrowing);
        monthStatisticService.finishBookBorrowing(bookBorrowing);
    }
    public void finishBorrowingStatisticOnly(BookBorrowing bookBorrowing){
        monthStatisticService.finishBookBorrowing(bookBorrowing);
    }
    public void startBorrowingStatisticOnly(BookBorrowing bookBorrowing) {
        monthStatisticService.startBookBorrowing(bookBorrowing);
    }
}
