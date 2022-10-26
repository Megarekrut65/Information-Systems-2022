package ua.boa.smartlibrary.db.repositories.customermanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.boa.smartlibrary.dataclasses.customermanagement.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT * FROM customer WHERE LOWER(customer.name) " +
            "LIKE CONCAT('%', CONCAT(LOWER(:name), '%')) OR LOWER(:name) " +
            "LIKE CONCAT('%', CONCAT(LOWER(customer.name), '%'))",
            nativeQuery = true)
    List<Customer> findCustomersByNameAdvanced(@Param("name") String name);

    @Query(value = "SELECT * FROM customer WHERE customer.phone_number " +
            "LIKE CONCAT('%', CONCAT(:phone_number, '%')) OR :phone_number " +
            "LIKE CONCAT('%', CONCAT(customer.phone_number, '%'))",
            nativeQuery = true)
    List<Customer> findCustomersByPhoneNumberAdvanced(@Param("phone_number") String phoneNumber);

    @Query(value = "SELECT * FROM customer WHERE LOWER(customer.email) " +
            "LIKE CONCAT('%', CONCAT(LOWER(:email), '%')) OR LOWER(:email) " +
            "LIKE CONCAT('%', CONCAT(LOWER(customer.email), '%'))",
            nativeQuery = true)
    List<Customer> findCustomersByEmailAdvanced(@Param("email") String email);

    @Query(value = "SELECT * FROM customer WHERE (LOWER(customer.email) " +
            "LIKE CONCAT('%', CONCAT(LOWER(:email), '%')) OR LOWER(:email) " +
            "LIKE CONCAT('%', CONCAT(LOWER(customer.email), '%'))) AND" +
            "(LOWER(customer.name) " +
            "LIKE CONCAT('%', CONCAT(LOWER(:name), '%')) OR LOWER(:name) " +
            "LIKE CONCAT('%', CONCAT(LOWER(customer.name), '%'))) AND" +
            "(customer.phone_number " +
            "LIKE CONCAT('%', CONCAT(:phone_number, '%')) OR :phone_number " +
            "LIKE CONCAT('%', CONCAT(customer.phone_number, '%')))",
            nativeQuery = true)
    List<Customer> findCustomersByAllAdvanced(@Param("name") String name, @Param("phone_number") String phoneNumber,
                                              @Param("email") String email);
}
