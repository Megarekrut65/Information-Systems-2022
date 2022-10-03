package ua.boa.smartlibrary.controllers.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.Utilities;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Author;
import ua.boa.smartlibrary.services.bookmanagement.AuthorSearchService;
import ua.boa.smartlibrary.services.bookmanagement.AuthorService;

import java.util.List;

@RestController
public class AuthorController {
    @Autowired
    private AuthorService service;
    @Autowired
    protected AuthorSearchService authorSearchService;

    @RequestMapping(value = "/author", method = RequestMethod.POST)
    public Author create(@RequestParam("name") String name, @RequestParam("date-of-birth") String dateOfBirth,
                         @RequestParam("date-of-death") String dateOfDeath) {
        return service.create(name, Utilities.getDate(dateOfBirth), Utilities.getDate(dateOfDeath));
    }

    @RequestMapping(value = "/author", method = RequestMethod.PUT)
    public Author update(@RequestParam("id") String id, @RequestParam("name") String name,
                         @RequestParam("date-of-birth") String dateOfBirth,
                         @RequestParam("date-of-death") String dateOfDeath) {
        return service.update(Integer.parseInt(id), name, Utilities.getDate(dateOfBirth), Utilities.getDate(dateOfDeath));
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public List<Author> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/author", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/authors/by-name", method = RequestMethod.GET)
    public List<Author> searchByName(@RequestParam("name") String name) {
        return service.findByName(name);
    }

    @RequestMapping(value = "/author", method = RequestMethod.GET)
    public Author get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }
    @RequestMapping(value = "/authors/by-all", method = RequestMethod.GET)
    public List<Author> searchByAll(@RequestParam("author-name") String authorName,
                                    @RequestParam("book-title")String bookTitle) {
        return authorSearchService.findAuthorByAll(authorName, bookTitle);
    }
}
