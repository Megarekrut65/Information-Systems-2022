package ua.boa.smartlibrary.exceptions.bookcirculationmanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.boa.smartlibrary.exceptions.NotFoundByIdException;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such month statistic")
public class MonthStatisticNotFoundException extends NotFoundByIdException {
    public MonthStatisticNotFoundException(Integer id) {
        super(id, "MonthStatistic");
    }
}
