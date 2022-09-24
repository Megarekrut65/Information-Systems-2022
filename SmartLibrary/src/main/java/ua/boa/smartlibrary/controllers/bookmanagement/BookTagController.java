package ua.boa.smartlibrary.controllers.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.dataclasses.bookmanagement.BookGenre;
import ua.boa.smartlibrary.dataclasses.bookmanagement.BookTag;
import ua.boa.smartlibrary.services.bookmanagement.BookTagService;

import java.util.List;

@RestController
public class BookTagController {
    @Autowired
    private BookTagService service;

    @RequestMapping(value = "/book-tag", method = RequestMethod.POST)
    public BookTag create(@RequestParam("book-id") String bookId,
                          @RequestParam("tag-id") String tagId) {
        return service.create(Integer.parseInt(bookId), Integer.parseInt(tagId));
    }

    @RequestMapping(value = "/book-tag", method = RequestMethod.PUT)
    public BookTag update(@RequestParam("id") String id, @RequestParam("book-id") String bookId,
                          @RequestParam("tag-id") String tagId) {
        return service.update(Integer.parseInt(id), Integer.parseInt(bookId), Integer.parseInt(tagId));
    }

    @RequestMapping(value = "/book-tags", method = RequestMethod.GET)
    public List<BookTag> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/book-tag", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/book-tags/by-book-id", method = RequestMethod.GET)
    public List<BookTag> searchByBook(@RequestParam("book-id") String bookId) {
        return service.findByBook(Integer.parseInt(bookId));
    }

    @RequestMapping(value = "/book-tags/by-tag-id", method = RequestMethod.GET)
    public List<BookTag> searchByTag(@RequestParam("tag-id") String tagId) {
        return service.findByTag(Integer.parseInt(tagId));
    }

    @RequestMapping(value = "/book-tag", method = RequestMethod.GET)
    public BookTag get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }
}
