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
    @Column(name="books_current_count", nullable = false)
    private Integer booksCurrentCount;
    @Column(name="books_available_count", nullable = false)
    private Integer booksAvailableCount;
    @Column(name="books_purchasing_count", nullable = false)
    private Integer booksPurchasingCount;
    @Column(name="books_write-off_count", nullable = false)
    private Integer booksWriteOffCount;
    @Column(name="books_lost_count", nullable = false)
    private Integer booksLostCount;
    @Column(name="books_borrowing_count", nullable = false)
    private Integer booksBorrowingCount;
}