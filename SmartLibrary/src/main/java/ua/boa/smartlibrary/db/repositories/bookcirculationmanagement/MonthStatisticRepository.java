package ua.boa.smartlibrary.db.repositories.bookcirculationmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.MonthStatistic;

import java.sql.Date;
import java.util.List;

public interface MonthStatisticRepository extends JpaRepository<MonthStatistic, Integer> {
    List<MonthStatistic> findMonthStatisticsByMonthDateBetween(Date monthDateMin, Date monthDateMax);
}
