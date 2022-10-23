package ua.boa.smartlibrary.controllers.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Tag;
import ua.boa.smartlibrary.services.bookmanagement.TagService;

import java.util.List;

@RestController
public class TagController {
    @Autowired
    private TagService service;

    @RequestMapping(value = "/tag", method = RequestMethod.POST)
    public Tag create(@RequestParam("name") String name) {
        return service.create(name);
    }

    @RequestMapping(value = "/tag", method = RequestMethod.PUT)
    public Tag update(@RequestParam("id") String id, @RequestParam("name") String name) {
        return service.update(Integer.parseInt(id), name);
    }

    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public List<Tag> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public Tag get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }

    @RequestMapping(value = "/tag", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/tags/by-name", method = RequestMethod.GET)
    public List<Tag> searchByName(@RequestParam("name") String name) {
        return service.findByName(name);
    }
    @RequestMapping(value = "/tags/by-all/page", method = RequestMethod.GET)
    public List<Tag> searchByAllPage(@RequestParam("page") String page,
                                     @RequestParam("per-page") String perPage,
                                     @RequestParam("name") String name) {
        return service.getPage(Integer.parseInt(page), Integer.parseInt(perPage),service.findByName(name));
    }
}
