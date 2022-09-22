package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookDelivery;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookLost;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookWriteOff;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.MonthStatistic;
import ua.boa.smartlibrary.db.repositories.bookcirculationmanagement.MonthStatisticRepository;
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
     * When books are delivered, their current, available and purchase increase
     * @param bookDelivery - information of book delivery
     * @return month statistic of date of delivery
     */
    public MonthStatistic addBookDelivery(BookDelivery bookDelivery){
        List<MonthStatistic> toChange = getMonthStatisticsToChangeByDate(bookDelivery.getDelivery().getDateOfDelivery());
        for(MonthStatistic statistic:toChange){
            statistic.setBooksAvailableCount(statistic.getBooksAvailableCount() + bookDelivery.getBookCount());
            statistic.setBooksCurrentCount(statistic.getBooksCurrentCount() + bookDelivery.getBookCount());
            statistic.setBooksPurchasingCount(statistic.getBooksPurchasingCount() + bookDelivery.getBookCount());
        }
        repository.saveAll(toChange);
        return toChange.get(0);
    }
    public MonthStatistic addBookWriteOff(BookWriteOff bookWriteOff){
        List<MonthStatistic> toChange = getMonthStatisticsToChangeByDate(bookWriteOff.getDateOfWriteOff());
        for(MonthStatistic statistic:toChange){
            statistic.setBooksAvailableCount(statistic.getBooksAvailableCount() - bookWriteOff.getBookCount());
            statistic.setBooksCurrentCount(statistic.getBooksCurrentCount() - bookWriteOff.getBookCount());
            statistic.setBooksWriteOffCount(statistic.getBooksWriteOffCount() + bookWriteOff.getBookCount());
        }
        repository.saveAll(toChange);
        return toChange.get(0);
    }
    public MonthStatistic addBookLost(BookLost bookLost){
        List<MonthStatistic> toChange = getMonthStatisticsToChangeByDate(bookLost.getDateOfLoss());
        for(MonthStatistic statistic:toChange){
            statistic.setBooksAvailableCount(statistic.getBooksAvailableCount() - bookLost.getBookCount());
            statistic.setBooksCurrentCount(statistic.getBooksCurrentCount() - bookLost.getBookCount());
            statistic.setBooksLostCount(statistic.getBooksLostCount() + bookLost.getBookCount());
        }
        repository.saveAll(toChange);
        return toChange.get(0);
    }
    private List<MonthStatistic> getMonthStatisticsToChangeByDate(Date sqlDate){
        LocalDate date = sqlDate.toLocalDate();
        int year = date.getYear();
        int month = date.getMonthValue();
        int lastDay = date.getMonth().maxLength();
        String pattern = year+"-"+month+"-";
        Date min = Date.valueOf(pattern + "01");
        Date max = Date.valueOf(pattern + lastDay);
        List<MonthStatistic> statistics = repository.findMonthStatisticsByMonthDateBetween(min, max);
        if(statistics.isEmpty()){
            List<MonthStatistic> closestStatistics = repository.findClosestMonthStatistics(sqlDate);
            MonthStatistic monthStatistic = closestStatistics.size() > 0
                    ? new MonthStatistic(max, closestStatistics.get(0))
                    : new MonthStatistic(max);
            statistics.add(repository.save(monthStatistic));
            List<MonthStatistic> toChange = repository.findMonthStatisticsByMonthDateGreaterThan(max);
            statistics.addAll(toChange);
        }
        return statistics;
    }
    public List<MonthStatistic> getAll(){
        return repository.findAll();
    }
    public List<MonthStatistic> findByMonthDatePeriod(Date monthDateMin, Date monthDateMax){
        return repository.findMonthStatisticsByMonthDateBetween(monthDateMin, monthDateMax);
    }
    public MonthStatistic get(Integer id){
        Optional<MonthStatistic> optional = repository.findById(id);
        if(optional.isEmpty()) throw new MonthStatisticNotFoundException(id);
        return optional.get();
    }
}
