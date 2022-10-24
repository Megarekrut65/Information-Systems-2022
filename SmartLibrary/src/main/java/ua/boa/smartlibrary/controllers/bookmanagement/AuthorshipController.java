package ua.boa.smartlibrary.controllers.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Authorship;
import ua.boa.smartlibrary.services.bookmanagement.AuthorshipService;

import java.util.List;

@RestController
public class AuthorshipController {
    @Autowired
    private AuthorshipService service;

    @RequestMapping(value = "/authorship", method = RequestMethod.POST)
    public Authorship create(@RequestParam("author-role") String authorRole,
                             @RequestParam("book-id") String bookId,
                             @RequestParam("author-id") String authorId) {
        return service.create(authorRole, Integer.parseInt(bookId), Integer.parseInt(authorId));
    }

    @RequestMapping(value = "/authorship", method = RequestMethod.PUT)
    public Authorship update(@RequestParam("id") String id, @RequestParam("author-role") String authorRole,
                             @RequestParam("book-id") String bookId,
                             @RequestParam("author-id") String authorId) {
        return service.update(Integer.parseInt(id), authorRole, Integer.parseInt(bookId), Integer.parseInt(authorId));
    }

    @RequestMapping(value = "/authorships", method = RequestMethod.GET)
    public List<Authorship> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/authorship", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/authorships/by-book-id", method = RequestMethod.GET)
    public List<Authorship> searchByBook(@RequestParam("book-id") String bookId) {
        return service.findByBook(Integer.parseInt(bookId));
    }

    @RequestMapping(value = "/authorships/by-author-id", method = RequestMethod.GET)
    public List<Authorship> searchByAuthor(@RequestParam("author-id") String authorId) {
        return service.findByAuthor(Integer.parseInt(authorId));
    }
    @RequestMapping(value = "/authorships/by-author-id/page", method = RequestMethod.GET)
    public List<Authorship> searchByAuthorPage(@RequestParam("page") String page,
                                               @RequestParam("per-page") String perPage,
                                               @RequestParam("author-id") String authorId) {
        return service.getPage(Integer.parseInt(page), Integer.parseInt(perPage),
                service.findByAuthor(Integer.parseInt(authorId)));
    }
    @RequestMapping(value = "/authorship", method = RequestMethod.GET)
    public Authorship get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }
}
