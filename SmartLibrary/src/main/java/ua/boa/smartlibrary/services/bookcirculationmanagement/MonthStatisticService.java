package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookDelivery;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookLost;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookWriteOff;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.MonthStatistic;
import ua.boa.smartlibrary.dataclasses.customermanagement.BookBorrowing;
import ua.boa.smartlibrary.db.repositories.bookcirculationmanagement.MonthStatisticRepository;
import ua.boa.smartlibrary.exceptions.BadDatabaseOperationException;
import ua.boa.smartlibrary.exceptions.bookcirculationmanagement.MonthStatisticNotFoundException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MonthStatisticService {
    @Autowired
    private MonthStatisticRepository repository;

    /**
     * When books are delivered, their total, available and purchase increase
     *
     * @param bookDelivery - information of book delivery
     * @return month statistic of date of delivery
     */
    public MonthStatistic addBookDelivery(BookDelivery bookDelivery) {
        List<MonthStatistic> toChange = getMonthStatisticsToChangeByDate(bookDelivery.getDelivery().getDateOfDelivery());
        int count = bookDelivery.getBookCount();
        for (MonthStatistic statistic : toChange) {
            int newAvailable = statistic.getBooksAvailableCount() + count;
//            if (newAvailable < 0) throw new BadDatabaseOperationException("Can't change delivery date " +
//                    "because available book count will be < 0");
            statistic.setBooksAvailableCount(newAvailable);
            int newTotal = statistic.getBooksTotalCount() + count;
//            if (newTotal < 0) throw new BadDatabaseOperationException("Can't change delivery date " +
//                    "because total book count will be < 0");
            statistic.setBooksTotalCount(newTotal);
            statistic.setBooksPurchasingCount(statistic.getBooksPurchasingCount() + count);
        }
        repository.saveAll(toChange);
        return toChange.get(0);
    }

    public MonthStatistic addBookWriteOff(BookWriteOff bookWriteOff) {
        List<MonthStatistic> toChange = getMonthStatisticsToChangeByDate(bookWriteOff.getDateOfWriteOff());
        int count = bookWriteOff.getBookCount();
        for (MonthStatistic statistic : toChange) {
            int newTotal = statistic.getBooksTotalCount() - count;
            if (newTotal < 0) throw new BadDatabaseOperationException("Can't write-off books " +
                    "because there is not enough total count!");
            statistic.setBooksTotalCount(newTotal);
            int newAvailable = statistic.getBooksAvailableCount() - count;
            if (newAvailable < 0) throw new BadDatabaseOperationException("Can't write-off books because there is " +
                    "not enough available count! Some books are borrowed.");
            statistic.setBooksAvailableCount(newAvailable);
            int newWriteOff = statistic.getBooksWriteOffCount() + count;
            if (newWriteOff < 0)
                throw new BadDatabaseOperationException("Can't undo write-off books because there is " +
                        "not enough write-off count!");
            statistic.setBooksWriteOffCount(newWriteOff);
        }
        repository.saveAll(toChange);
        return toChange.get(0);
    }

    public MonthStatistic addBookLost(BookLost bookLost) {
        List<MonthStatistic> toChange = getMonthStatisticsToChangeByDate(bookLost.getDateOfLoss());
        int count = bookLost.getBookCount();
        for (MonthStatistic statistic : toChange) {
            int newTotal = statistic.getBooksTotalCount() - count;
            if (newTotal < 0) throw new BadDatabaseOperationException("Can't add books as lost " +
                    "because there is not enough total count!");
            statistic.setBooksTotalCount(newTotal);
            if (bookLost.getWasReturned()) {
                int newAvailable = statistic.getBooksAvailableCount() - count;
                if (newAvailable < 0)
                    throw new BadDatabaseOperationException("Can't add books as lost because there is " +
                            "not enough available count! Some books are borrowed.");
                statistic.setBooksAvailableCount(newAvailable);
            } else {
                int newBorrowing = statistic.getBooksBorrowingCount() - count;
                if (newBorrowing < 0)
                    throw new BadDatabaseOperationException("Can't add books as lost because there is " +
                            "not enough available count! Some books are returned.");
                statistic.setBooksBorrowingCount(newBorrowing);
            }
            int newLost = statistic.getBooksLostCount() + count;
            if (newLost < 0) throw new BadDatabaseOperationException("Can't undo loss books because there is " +
                    "not enough loss count!");
            statistic.setBooksLostCount(newLost);
        }
        repository.saveAll(toChange);
        return toChange.get(0);
    }

    public MonthStatistic startBookBorrowing(BookBorrowing bookBorrowing) {
        List<MonthStatistic> toChange = getMonthStatisticsToChangeByDate(bookBorrowing.getDateOfBorrowing());
        for (MonthStatistic statistic : toChange) {
            int available = statistic.getBooksAvailableCount();
            if (available == 0)
                throw new BadDatabaseOperationException("Can't borrow book because there is not any book!");
            statistic.setBooksAvailableCount(available - 1);
            statistic.setBooksBorrowingCount(statistic.getBooksBorrowingCount() + 1);
        }
        repository.saveAll(toChange);
        return toChange.get(0);
    }

    public MonthStatistic finishBookBorrowing(BookBorrowing bookBorrowing) {
        Date date = bookBorrowing.getActualDateOfReturn();
        if (date == null) throw new BadDatabaseOperationException("Can't finish borrowing without date of return!");
        List<MonthStatistic> toChange = getMonthStatisticsToChangeByDate(date);
        for (MonthStatistic statistic : toChange) {
            int borrowing = statistic.getBooksBorrowingCount();
            if (borrowing == 0)
                throw new BadDatabaseOperationException("Can't return book because there is not any borrowed book!");
            statistic.setBooksAvailableCount(statistic.getBooksAvailableCount() + 1);
            statistic.setBooksBorrowingCount(borrowing - 1);
        }
        repository.saveAll(toChange);
        return toChange.get(0);
    }

    private List<MonthStatistic> getMonthStatisticsToChangeByDate(Date sqlDate) {
        LocalDate date = sqlDate.toLocalDate();
        int year = date.getYear();
        int month = date.getMonthValue();
        int lastDay = date.lengthOfMonth();
        String pattern = year + "-" + month + "-";
        Date min = Date.valueOf(pattern + "01");
        Date max = Date.valueOf(pattern + lastDay);
        List<MonthStatistic> statistics = repository.findMonthStatisticsByMonthDateBetween(min, max);
        if (statistics.isEmpty()) {
            List<MonthStatistic> closestStatistics = repository.findClosestMonthStatistics(sqlDate);
            MonthStatistic monthStatistic = closestStatistics.size() > 0
                    ? new MonthStatistic(max, closestStatistics.get(0))
                    : new MonthStatistic(max);
            statistics.add(repository.save(monthStatistic));
        }
        List<MonthStatistic> toChange = repository.findMonthStatisticsByMonthDateGreaterThan(max);
        statistics.addAll(toChange);
        return statistics;
    }

    public List<MonthStatistic> getAll() {
        return repository.getAllOrdered();
    }

    public List<MonthStatistic> findByMonthDatePeriod(Date monthDateMin, Date monthDateMax) {
        if(monthDateMin != null && monthDateMax != null)
            return repository.findMonthStatisticsByMonthDateBetween(monthDateMin, monthDateMax);
        if(monthDateMin != null)
            return repository.findMonthStatisticsByMonthDateGreaterThanEqual(monthDateMin);
        if(monthDateMax != null)
            return repository.findMonthStatisticsByMonthDateLessThanEqual(monthDateMax);
        return repository.getAllOrdered();
    }
    public MonthStatistic findClosestStatisticByDate(Date monthDate) {
        List<MonthStatistic> closestStatistics = repository.findClosestMonthStatistics(monthDate);
        return closestStatistics.size() > 0
                ? closestStatistics.get(0)
                : new MonthStatistic(monthDate);
    }
    public MonthStatistic get(Integer id) {
        Optional<MonthStatistic> optional = repository.findById(id);
        if (optional.isEmpty()) throw new MonthStatisticNotFoundException(id);
        return optional.get();
    }
}
