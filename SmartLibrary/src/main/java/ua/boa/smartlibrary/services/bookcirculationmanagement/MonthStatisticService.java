package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookDelivery;
import ua.boa.smartlibrary.db.repositories.bookcirculationmanagement.MonthStatisticRepository;

import java.sql.Date;

@Service
public class MonthStatisticService {
    @Autowired
    private MonthStatisticRepository repository;

    public void addBookDelivery(BookDelivery bookDelivery){
        Date date = bookDelivery.getDelivery().getDateOfDelivery();
    }
}
