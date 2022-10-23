package ua.boa.smartlibrary.controllers.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.Utilities;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookWriteOff;
import ua.boa.smartlibrary.services.bookcirculationmanagement.BookWriteOffSearchService;
import ua.boa.smartlibrary.services.bookcirculationmanagement.BookWriteOffService;

import java.util.List;

@RestController
public class BookWriteOffController {
    @Autowired
    private BookWriteOffService service;
    @Autowired
    private BookWriteOffSearchService bookWriteOffSearchService;

    @RequestMapping(value = "/book-write-off", method = RequestMethod.POST)
    public BookWriteOff create(@RequestParam("date-of-write-off") String dateOfWriteOff,
                               @RequestParam("book-id") String bookId,
                               @RequestParam("book-count") String bookCount) {
        return service.create(Utilities.getDate(dateOfWriteOff), Integer.parseInt(bookId), Integer.parseInt(bookCount));
    }

    @RequestMapping(value = "/book-write-off", method = RequestMethod.PUT)
    public BookWriteOff update(@RequestParam("id") String id, @RequestParam("date-of-write-off") String dateOfWriteOff,
                               @RequestParam("book-id") String bookId,
                               @RequestParam("book-count") String bookCount) {
        return service.update(Integer.parseInt(id), Utilities.getDate(dateOfWriteOff),
                Integer.parseInt(bookId), Integer.parseInt(bookCount));
    }

    @RequestMapping(value = "/book-write-offs", method = RequestMethod.GET)
    public List<BookWriteOff> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/book-write-off", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/book-write-offs/by-date-of-write-off-period", method = RequestMethod.GET)
    public List<BookWriteOff> searchByDatePeriod(@RequestParam("date-of-write-off-min") String dateOfWriteOffMin,
                                                 @RequestParam("date-of-write-off-max") String dateOfWriteOffMax) {
        return service.findByDateOfWriteOffPeriod(Utilities.getDate(dateOfWriteOffMin),
                Utilities.getDate(dateOfWriteOffMax));
    }

    @RequestMapping(value = "/book-write-offs/by-book-id", method = RequestMethod.GET)
    public List<BookWriteOff> searchByBook(@RequestParam("book-id") String bookId) {
        return service.findByBook(Integer.parseInt(bookId));
    }

    @RequestMapping(value = "/book-write-off", method = RequestMethod.GET)
    public BookWriteOff get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }

    @RequestMapping(value = "/book-write-offs/by-all", method = RequestMethod.GET)
    public List<BookWriteOff> searchByAll(@RequestParam("date-of-write-off-min") String dateOfWriteOffMin,
                                          @RequestParam("date-of-write-off-max") String dateOfWriteOffMax,
                                          @RequestParam("book-title") String bookTitle) {
        return bookWriteOffSearchService.findBookWriteOffsByAll(bookTitle, Utilities.getDate(dateOfWriteOffMin),
                Utilities.getDate(dateOfWriteOffMax));
    }
    @RequestMapping(value = "/book-write-offs/by-all/page", method = RequestMethod.GET)
    public List<BookWriteOff> searchByAllPage(@RequestParam("page") String page,
                                              @RequestParam("per-page") String perPage,
                                              @RequestParam("date-of-write-off-min") String dateOfWriteOffMin,
                                          @RequestParam("date-of-write-off-max") String dateOfWriteOffMax,
                                          @RequestParam("book-title") String bookTitle) {
        return bookWriteOffSearchService.getPage(Integer.parseInt(page), Integer.parseInt(perPage),
                bookWriteOffSearchService.findBookWriteOffsByAll(bookTitle, Utilities.getDate(dateOfWriteOffMin),
                Utilities.getDate(dateOfWriteOffMax)));
    }
}
