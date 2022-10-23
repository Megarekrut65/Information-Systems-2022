package ua.boa.smartlibrary.controllers.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Distributor;
import ua.boa.smartlibrary.services.bookcirculationmanagement.DistributorService;

import java.util.List;

@RestController
public class DistributorController {

    @Autowired
    private DistributorService service;

    @RequestMapping(value = "/distributor", method = RequestMethod.POST)
    public Distributor create(@RequestParam("name") String name,
                              @RequestParam("phone-number") String phoneNumber,
                              @RequestParam("address") String address) {
        return service.create(name, phoneNumber, address);
    }

    @RequestMapping(value = "/distributor", method = RequestMethod.PUT)
    public Distributor update(@RequestParam("id") String id, @RequestParam("name") String name,
                              @RequestParam("phone-number") String phoneNumber,
                              @RequestParam("address") String address) {
        return service.update(Integer.parseInt(id), name, phoneNumber, address);
    }

    @RequestMapping(value = "/distributors", method = RequestMethod.GET)
    public List<Distributor> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/distributor", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/distributors/by-name", method = RequestMethod.GET)
    public List<Distributor> searchByName(@RequestParam("name") String name) {
        return service.findByName(name);
    }
    @RequestMapping(value = "/distributors/by-all/page", method = RequestMethod.GET)
    public List<Distributor> searchByAllPage(@RequestParam("page") String page,
                                             @RequestParam("per-page") String perPage,
                                             @RequestParam("name") String name) {
        return service.getPage(Integer.parseInt(page), Integer.parseInt(perPage),service.findByName(name));
    }
    @RequestMapping(value = "/distributor", method = RequestMethod.GET)
    public Distributor get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }
}