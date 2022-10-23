package ua.boa.smartlibrary.controllers.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.Utilities;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookLost;
import ua.boa.smartlibrary.services.bookcirculationmanagement.BookLostSearchService;
import ua.boa.smartlibrary.services.bookcirculationmanagement.BookLostService;

import java.util.List;

@RestController
public class BookLostController {
    @Autowired
    private BookLostService service;
    @Autowired
    private BookLostSearchService bookLostSearchService;

    @RequestMapping(value = "/book-lost", method = RequestMethod.POST)
    public BookLost create(@RequestParam("book-count") String bookCount, @RequestParam("book-id") String bookId,
                           @RequestParam("cause-of-loss") String causeOfLoss,
                           @RequestParam("date-of-loss") String dateOfLoss,
                           @RequestParam("was-returned") String wasReturned) {
        return service.create(Integer.parseInt(bookCount), Integer.parseInt(bookId),
                causeOfLoss, Utilities.getDate(dateOfLoss), Boolean.parseBoolean(wasReturned));
    }

    @RequestMapping(value = "/book-lost", method = RequestMethod.PUT)
    public BookLost update(@RequestParam("id") String id, @RequestParam("book-count") String bookCount,
                           @RequestParam("book-id") String bookId,
                           @RequestParam("cause-of-loss") String causeOfLoss,
                           @RequestParam("date-of-loss") String dateOfLoss,
                           @RequestParam("was-returned") String wasReturned) {
        return service.update(Integer.parseInt(id), Integer.parseInt(bookCount), Integer.parseInt(bookId),
                causeOfLoss, Utilities.getDate(dateOfLoss), Boolean.parseBoolean(wasReturned));
    }

    @RequestMapping(value = "/book-losts", method = RequestMethod.GET)
    public List<BookLost> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/book-lost", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/book-losts/by-date-of-loss-period", method = RequestMethod.GET)
    public List<BookLost> searchByDatePeriod(@RequestParam("date-of-loss-min") String dateOfLossMin,
                                             @RequestParam("date-of-loss-max") String dateOfLossMax) {
        return service.findByDateOfLossPeriod(Utilities.getDate(dateOfLossMin),
                Utilities.getDate(dateOfLossMax));
    }

    @RequestMapping(value = "/book-losts/by-book-id", method = RequestMethod.GET)
    public List<BookLost> searchByBook(@RequestParam("book-id") String bookId) {
        return service.findByBook(Integer.parseInt(bookId));
    }

    @RequestMapping(value = "/book-lost", method = RequestMethod.GET)
    public BookLost get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }

    @RequestMapping(value = "/book-losts/by-all", method = RequestMethod.GET)
    public List<BookLost> searchByAll(@RequestParam("book-title") String bookTitle,
                                      @RequestParam("date-of-loss-min") String dateOfLossMin,
                                      @RequestParam("date-of-loss-max") String dateOfLossMax) {
        return bookLostSearchService.findBookLostsByAll(bookTitle, Utilities.getDate(dateOfLossMin),
                Utilities.getDate(dateOfLossMax));
    }
    @RequestMapping(value = "/book-losts/by-all/page", method = RequestMethod.GET)
    public List<BookLost> searchByAllPage(@RequestParam("page") String page,
                                          @RequestParam("per-page") String perPage,
                                          @RequestParam("book-title") String bookTitle,
                                      @RequestParam("date-of-loss-min") String dateOfLossMin,
                                      @RequestParam("date-of-loss-max") String dateOfLossMax) {
        return bookLostSearchService.getPage(Integer.parseInt(page), Integer.parseInt(perPage),
                bookLostSearchService.findBookLostsByAll(bookTitle, Utilities.getDate(dateOfLossMin),
                Utilities.getDate(dateOfLossMax)));
    }
}
