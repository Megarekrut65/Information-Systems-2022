package ua.boa.smartlibrary.controllers.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.Utilities;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.MonthStatistic;
import ua.boa.smartlibrary.services.bookcirculationmanagement.MonthStatisticService;

import java.util.List;

@RestController
public class MonthStatisticController {
    @Autowired
    private MonthStatisticService service;

    @RequestMapping(value = "/month-statistics", method = RequestMethod.GET)
    public List<MonthStatistic> getAll(){
        return service.getAll();
    }
    @RequestMapping(value = "/month-statistics/by-month-date-period", method = RequestMethod.GET)
    public List<MonthStatistic> searchByPublishYearPeriod(@RequestParam("month-date-min")String monthDateMin,
                                                @RequestParam("month-date-max")String monthDateMax){
        return service.findByMonthDatePeriod(Utilities.getDate(monthDateMin),Utilities.getDate(monthDateMax));
    }
    @RequestMapping(value = "/month-statistic", method = RequestMethod.GET)
    public MonthStatistic get(@RequestParam("id")String id){
        return service.get(Integer.parseInt(id));
    }
}
