package ua.boa.smartlibrary.services.customermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.PageGetter;
import ua.boa.smartlibrary.dataclasses.customermanagement.Customer;
import ua.boa.smartlibrary.db.repositories.customermanagement.CustomerRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class CustomerSearchService extends PageGetter<Customer> {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findCustomersByAll(String name, String phoneNumber, String email) {
        return customerRepository.findCustomersByAllAdvanced(name, phoneNumber, email);
    }
    public List<Customer> getPage(int page, int perPage, List<Customer> customers) {
        customers.sort(Comparator.comparing(Customer::getName));
        return getPart(customers, page, perPage);
    }
}
