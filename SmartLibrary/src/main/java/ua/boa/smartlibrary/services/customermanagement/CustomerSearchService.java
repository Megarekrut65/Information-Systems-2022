package ua.boa.smartlibrary.services.customermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.customermanagement.Customer;
import ua.boa.smartlibrary.db.repositories.customermanagement.CustomerRepository;

import java.util.List;

@Service
public class CustomerSearchService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findCustomersByAll(String name, String phoneNumber, String email){
        return customerRepository.findCustomersByAllAdvanced(name, phoneNumber, email);
    }
}
