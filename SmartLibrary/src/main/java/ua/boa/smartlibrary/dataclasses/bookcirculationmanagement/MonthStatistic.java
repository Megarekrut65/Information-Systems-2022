package ua.boa.smartlibrary.dataclasses.bookcirculationmanagement;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="month_statistic")
public class MonthStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "month_statistic_id_generator")
    @SequenceGenerator(name="month_statistic_id_generator", sequenceName = "month_statistic_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name="month_date", nullable = false)
    private Date monthDate;
    @Column(name="books_total_count", nullable = false)
    private Integer booksTotalCount;
    @Column(name="books_available_count", nullable = false)
    private Integer booksAvailableCount;
    @Column(name="books_purchasing_count", nullable = false)
    private Integer booksPurchasingCount;
    @Column(name="books_write_off_count", nullable = false)
    private Integer booksWriteOffCount;
    @Column(name="books_lost_count", nullable = false)
    private Integer booksLostCount;
    @Column(name="books_borrowing_count", nullable = false)
    private Integer booksBorrowingCount;

    public MonthStatistic(Date monthDate, Integer booksTotalCount,
                          Integer booksAvailableCount, Integer booksPurchasingCount,
                          Integer booksWriteOffCount, Integer booksLostCount, Integer booksBorrowingCount) {
        this.monthDate = monthDate;
        this.booksTotalCount = booksTotalCount;
        this.booksAvailableCount = booksAvailableCount;
        this.booksPurchasingCount = booksPurchasingCount;
        this.booksWriteOffCount = booksWriteOffCount;
        this.booksLostCount = booksLostCount;
        this.booksBorrowingCount = booksBorrowingCount;
    }

    public MonthStatistic(Date monthDate) {
        this.monthDate = monthDate;
        this.booksTotalCount = 0;
        this.booksAvailableCount = 0;
        this.booksPurchasingCount = 0;
        this.booksWriteOffCount = 0;
        this.booksLostCount = 0;
        this.booksBorrowingCount = 0;
    }
    public MonthStatistic(Date monthDate, MonthStatistic monthStatistic){
        this.monthDate = monthDate;
        this.booksTotalCount = monthStatistic.getBooksTotalCount();
        this.booksAvailableCount = monthStatistic.getBooksAvailableCount();
        this.booksPurchasingCount = monthStatistic.getBooksPurchasingCount();
        this.booksWriteOffCount = monthStatistic.getBooksWriteOffCount();
        this.booksLostCount = monthStatistic.getBooksLostCount();
        this.booksBorrowingCount = monthStatistic.getBooksBorrowingCount();
    }
}