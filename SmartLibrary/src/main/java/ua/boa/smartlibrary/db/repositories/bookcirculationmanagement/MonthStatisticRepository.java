package ua.boa.smartlibrary.db.repositories.bookcirculationmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.MonthStatistic;

import java.sql.Date;
import java.util.List;

public interface MonthStatisticRepository extends JpaRepository<MonthStatistic, Integer> {
    List<MonthStatistic> findMonthStatisticsByMonthDateBetween(Date monthDateMin, Date monthDateMax);

    @Query(value = "SELECT * FROM month_statistic " +
            "WHERE CAST(month_statistic.month_date AS DATE) <= CAST(:date AS DATE) " +
            "ORDER BY ABS(CAST(month_statistic.month_date AS DATE) - " +
            "CAST(:date AS DATE)) LIMIT 1;", nativeQuery = true)
    List<MonthStatistic> findClosestMonthStatistics(@Param("date") Date date);

    List<MonthStatistic> findMonthStatisticsByMonthDateGreaterThan(Date monthDate);
    List<MonthStatistic> findMonthStatisticsByMonthDateLessThanEqual(Date monthDate);
    List<MonthStatistic> findMonthStatisticsByMonthDateGreaterThanEqual(Date monthDate);

    @Query(value = "SELECT * FROM month_statistic ORDER BY month_statistic.month_date DESC;", nativeQuery = true)
    List<MonthStatistic> getAllOrdered();
}
