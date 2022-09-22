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
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_id_generator")
    @SequenceGenerator(name="delivery_id_generator", sequenceName = "delivery_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "date_of_delivery")
    private Date dateOfDelivery;
    @ManyToOne
    @JoinColumn(name="distributor_id", nullable = false)
    private Distributor distributor;

    public Delivery(Date dateOfDelivery, Distributor distributor) {
        this.dateOfDelivery = dateOfDelivery;
        this.distributor = distributor;
    }
}
