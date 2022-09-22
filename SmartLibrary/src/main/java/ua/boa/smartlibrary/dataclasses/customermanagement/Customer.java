package ua.boa.smartlibrary.dataclasses.customermanagement;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_generator")
    @SequenceGenerator(name="customer_id_generator", sequenceName = "customer_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name="phone_number", nullable = false)
    private String phoneNumber;
    @Column(name="address", nullable = false)
    private String address;
    @Column(name="email")
    private String email;

    public Customer(String name, Date dateOfBirth, String phoneNumber, String address, String email) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }
}
