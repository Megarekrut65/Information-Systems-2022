package ua.boa.smartlibrary.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.boa.smartlibrary.dataclasses.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT * FROM customer WHERE customer.name " +
            "LIKE CONCAT('%', CONCAT(:name, '%')) OR :name " +
            "LIKE CONCAT('%', CONCAT(customer.name, '%'))",
            nativeQuery = true)
    List<Customer> findCustomersByNameAdvanced(@Param("name") String name);
    @Query(value = "SELECT * FROM customer WHERE customer.phone_number " +
            "LIKE CONCAT('%', CONCAT(:phone_number, '%')) OR :phone_number " +
            "LIKE CONCAT('%', CONCAT(customer.phone_number, '%'))",
            nativeQuery = true)
    List<Customer> findCustomersByPhoneNumberAdvanced(@Param("phone_number") String phoneNumber);
    @Query(value = "SELECT * FROM customer WHERE customer.email " +
            "LIKE CONCAT('%', CONCAT(:email, '%')) OR :email " +
            "LIKE CONCAT('%', CONCAT(customer.email, '%'))",
            nativeQuery = true)
    List<Customer> findCustomersByEmailAdvanced(@Param("email") String email);
}
