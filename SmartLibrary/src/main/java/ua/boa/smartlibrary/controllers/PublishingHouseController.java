package ua.boa.smartlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.dataclasses.PublishingHouse;
import ua.boa.smartlibrary.services.PublishingHouseService;

import java.util.List;

@RestController
public class PublishingHouseController {
    @Autowired
    private PublishingHouseService service;

    @RequestMapping(value = "/publishing-house", method = RequestMethod.POST)
    public PublishingHouse create(@RequestParam("name") String name, @RequestParam("address") String address) {
        return service.create(name, address);
    }

    @RequestMapping(value = "/publishing-house", method = RequestMethod.PUT)
    public PublishingHouse update(@RequestParam("id") String id, @RequestParam("name") String name,
                                  @RequestParam("address") String address) {
        return service.update(Integer.parseInt(id), name, address);
    }

    @RequestMapping(value = "/publishing-houses", method = RequestMethod.GET)
    public List<PublishingHouse> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/publishing-house", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/publishing-houses/by-name", method = RequestMethod.GET)
    public List<PublishingHouse> search(@RequestParam("name") String name) {
        return service.findByName(name);
    }

    @RequestMapping(value = "/publishing-house", method = RequestMethod.GET)
    public PublishingHouse get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }
}
