package ua.boa.smartlibrary.dataclasses;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "book_info")
public class BookInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_info_id_generator")
    @SequenceGenerator(name="book_info_id_generator", sequenceName = "book_info_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "current_count", nullable = false)
    private Integer currentCount;
    @Column(name = "available_count", nullable = false)
    private Integer availableCount;
    @Column(name = "purchasing_count", nullable = false)
    private Integer purchasingCount;
    @Column(name = "write_off_count", nullable = false)
    private Integer writeOffCount;
    @Column(name = "lost_count", nullable = false)
    private Integer lostCount;
    @Column(name = "borrowing_count", nullable = false)
    private Integer borrowingCount;

    public BookInfo() {
        this.currentCount = 0;
        this.availableCount = 0;
        this.purchasingCount = 0;
        this.writeOffCount = 0;
        this.lostCount = 0;
        this.borrowingCount = 0;
    }
}
