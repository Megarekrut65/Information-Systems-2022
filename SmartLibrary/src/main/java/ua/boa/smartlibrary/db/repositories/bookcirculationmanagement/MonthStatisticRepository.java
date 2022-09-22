package ua.boa.smartlibrary.db.repositories.bookcirculationmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.MonthStatistic;

import java.sql.Date;
import java.util.List;

public interface MonthStatisticRepository extends JpaRepository<MonthStatistic, Integer> {
    List<MonthStatistic> findMonthStatisticsByMonthDateBetween(Date monthDateMin, Date monthDateMax);
    @Query(value = "SELECT TOP 1 * FROM month_statistic " +
            "WHERE EXTRACT(YEAR FROM month_statistic.month_date) <= EXTRACT(YEAR FROM date) " +
            "ORDER BY ABS(DATEDIFF(day, month_statistic.month_date, date));", nativeQuery = true)
    List<MonthStatistic> findClosestMonthStatistics(@Param("date")Date date);
    List<MonthStatistic> findMonthStatisticsByMonthDateGreaterThan(Date date);
}
