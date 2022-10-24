package ua.boa.smartlibrary.controllers.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.PageGetter;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.services.bookmanagement.BookSearchService;
import ua.boa.smartlibrary.services.bookmanagement.BookService;

import java.util.List;

@RestController
public class BookController{
    @Autowired
    private BookService service;

    @Autowired
    private BookSearchService bookSearchService;

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public Book create(@RequestParam("title") String title,
                       @RequestParam("publishing-house-id") String publishingHouseId,
                       @RequestParam("publish-year") String publishYear,
                       @RequestParam("comment") String comment) {
        return service.create(title, Integer.parseInt(publishingHouseId), Integer.parseInt(publishYear), comment);
    }

    @RequestMapping(value = "/book", method = RequestMethod.PUT)
    public Book update(@RequestParam("id") String id, @RequestParam("title") String title,
                       @RequestParam("publishing-house-id") String publishingHouseId,
                       @RequestParam("publish-year") String publishYear,
                       @RequestParam("comment") String comment) {
        return service.update(Integer.parseInt(id), title,
                Integer.parseInt(publishingHouseId), Integer.parseInt(publishYear), comment);
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<Book> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/book", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/books/by-title", method = RequestMethod.GET)
    public List<Book> searchByTitle(@RequestParam("title") String title) {
        return service.findByTitle(title);
    }

    @RequestMapping(value = "/books/by-publishing-house-id", method = RequestMethod.GET)
    public List<Book> searchByPublishingHouse(@RequestParam("publishing-house-id") String publishingHouseId) {
        return service.findByPublishingHouse(Integer.parseInt(publishingHouseId));
    }
    @RequestMapping(value = "/books/by-publishing-house-id/page", method = RequestMethod.GET)
    public List<Book> searchByPublishingHousePage(@RequestParam("page") String page,
                                                  @RequestParam("per-page") String perPage,
                                                  @RequestParam("publishing-house-id") String publishingHouseId) {
        return bookSearchService.getPage(Integer.parseInt(page), Integer.parseInt(perPage),
                service.findByPublishingHouse(Integer.parseInt(publishingHouseId)));
    }
    @RequestMapping(value = "/books/by-publish-year-period", method = RequestMethod.GET)
    public List<Book> searchByPublishYearPeriod(@RequestParam("publish-year-min") String publishYearMin,
                                                @RequestParam("publish-year-max") String publishYearMax) {
        return service.findByPublishYearPeriod(Integer.parseInt(publishYearMin), Integer.parseInt(publishYearMax));
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public Book get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }

    @RequestMapping(value = "/books/by-all", method = RequestMethod.GET)
    public List<Book> searchByAll(@RequestParam("title") String title,
                                  @RequestParam("genre-name") String genreName,
                                  @RequestParam("tag-name") String tagName,
                                  @RequestParam("author-name") String authorName) {
        return bookSearchService.findBookByAll(title, genreName, tagName, authorName);
    }
    @RequestMapping(value = "/books/by-all/page", method = RequestMethod.GET)
    public List<Book> searchByAllPage(@RequestParam("page") String page,
                                      @RequestParam("per-page") String perPage,
                                      @RequestParam("title") String title,
                                  @RequestParam("genre-name") String genreName,
                                  @RequestParam("tag-name") String tagName,
                                  @RequestParam("author-name") String authorName) {
        return bookSearchService.getPage(Integer.parseInt(page), Integer.parseInt(perPage),
                bookSearchService.findBookByAll(title, genreName, tagName, authorName));
    }
}
