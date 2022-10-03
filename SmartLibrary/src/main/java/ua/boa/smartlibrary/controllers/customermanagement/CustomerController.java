package ua.boa.smartlibrary.controllers.customermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.Utilities;
import ua.boa.smartlibrary.dataclasses.customermanagement.Customer;
import ua.boa.smartlibrary.services.customermanagement.CustomerSearchService;
import ua.boa.smartlibrary.services.customermanagement.CustomerService;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService service;
    @Autowired
    private CustomerSearchService customerSearchService;


    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public Customer create(@RequestParam("name") String name, @RequestParam("date-of-birth") String dateOfBirth,
                           @RequestParam("phone-number") String phoneNumber,
                           @RequestParam("address") String address,
                           @RequestParam("email") String email) {
        return service.create(name, Utilities.getDate(dateOfBirth), phoneNumber, address, email);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.PUT)
    public Customer update(@RequestParam("id") String id, @RequestParam("name") String name,
                           @RequestParam("date-of-birth") String dateOfBirth,
                           @RequestParam("phone-number") String phoneNumber,
                           @RequestParam("address") String address,
                           @RequestParam("email") String email) {
        return service.update(Integer.parseInt(id), name, Utilities.getDate(dateOfBirth), phoneNumber, address, email);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public List<Customer> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/customer", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/customers/by-name", method = RequestMethod.GET)
    public List<Customer> searchByName(@RequestParam("name") String name) {
        return service.findByName(name);
    }

    @RequestMapping(value = "/customers/by-phone-number", method = RequestMethod.GET)
    public List<Customer> searchByPhoneNumber(@RequestParam("phone-number") String phoneNumber) {
        return service.findByPhoneNumber(phoneNumber);
    }

    @RequestMapping(value = "/customers/by-email", method = RequestMethod.GET)
    public List<Customer> searchByEmail(@RequestParam("email") String email) {
        return service.findByEmail(email);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public Customer get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }
    @RequestMapping(value = "/customers/by-all", method = RequestMethod.GET)
    public List<Customer> searchByAll(@RequestParam("name") String name,
                                      @RequestParam("phone-number") String phoneNumber,
                                      @RequestParam("email") String email) {
        return customerSearchService.findCustomersByAll(name, phoneNumber, email);
    }
}
