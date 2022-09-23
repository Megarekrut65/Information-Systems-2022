package ua.boa.smartlibrary.controllers.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Genre;
import ua.boa.smartlibrary.services.bookmanagement.GenreService;

import java.util.List;

@RestController
public class GenreController {
    @Autowired
    private GenreService service;

    @RequestMapping(value = "/genre", method = RequestMethod.POST)
    public Genre create(@RequestParam("name") String name) {
        return service.create(name);
    }

    @RequestMapping(value = "/genre", method = RequestMethod.PUT)
    public Genre update(@RequestParam("id") String id, @RequestParam("name") String name) {
        return service.update(Integer.parseInt(id), name);
    }

    @RequestMapping(value = "/genres", method = RequestMethod.GET)
    public List<Genre> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/genre", method = RequestMethod.GET)
    public Genre get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }

    @RequestMapping(value = "/genre", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/genres/by-name", method = RequestMethod.GET)
    public List<Genre> searchByName(@RequestParam("name") String name) {
        return service.findByName(name);
    }
}
