package ua.boa.smartlibrary.services.customermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.customermanagement.Customer;
import ua.boa.smartlibrary.db.repositories.customermanagement.CustomerRepository;
import ua.boa.smartlibrary.exceptions.customermanagement.CustomerNotFoundException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public Customer create(String name, Date dateOfBirth, String phoneNumber, String address, String email){
        return repository.save(new Customer(name, dateOfBirth, phoneNumber, address, email));
    }
    public List<Customer> getAll(){
        return repository.findAll();
    }
    public Customer update(Integer id, String name, Date dateOfBirth, String phoneNumber, String address, String email){
        Customer customer = get(id);
        customer.setName(name);
        customer.setDateOfBirth(dateOfBirth);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);
        customer.setEmail(email);
        return repository.save(customer);
    }
    public void remove(Integer id){
        Customer customer = get(id);
        repository.delete(customer);
    }
    public List<Customer> findByName(String name){
        return repository.findCustomersByNameAdvanced(name);
    }
    public List<Customer> findByPhoneNumber(String phoneNumber){
        return repository.findCustomersByPhoneNumberAdvanced(phoneNumber);
    }
    public List<Customer> findByEmail(String email){
        return repository.findCustomersByEmailAdvanced(email);
    }
    public Customer get(Integer id){
        Optional<Customer> optional = repository.findById(id);
        if(optional.isEmpty()) throw new CustomerNotFoundException(id);
        return optional.get();
    }
}
