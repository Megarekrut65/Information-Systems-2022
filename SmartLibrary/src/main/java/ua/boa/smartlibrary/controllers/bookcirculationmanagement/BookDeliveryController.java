package ua.boa.smartlibrary.controllers.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.Utilities;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookDelivery;
import ua.boa.smartlibrary.services.bookcirculationmanagement.BookDeliverySearchService;
import ua.boa.smartlibrary.services.bookcirculationmanagement.BookDeliveryService;

import java.util.List;

@RestController
public class BookDeliveryController {
    @Autowired
    private BookDeliveryService service;
    @Autowired
    private BookDeliverySearchService bookDeliverySearchService;

    @RequestMapping(value = "/book-delivery", method = RequestMethod.POST)
    public BookDelivery create(@RequestParam("delivery-id") String deliveryId,
                               @RequestParam("book-id") String bookId,
                               @RequestParam("book-count") String bookCount,
                               @RequestParam("book-price") String bookPrice) {
        return service.create(Integer.parseInt(deliveryId), Integer.parseInt(bookId),
                Integer.parseInt(bookCount), Integer.parseInt(bookPrice));
    }

    @RequestMapping(value = "/book-delivery", method = RequestMethod.PUT)
    public BookDelivery update(@RequestParam("id") String id, @RequestParam("delivery-id") String deliveryId,
                               @RequestParam("book-id") String bookId,
                               @RequestParam("book-count") String bookCount,
                               @RequestParam("book-price") String bookPrice) {
        return service.update(Integer.parseInt(id), Integer.parseInt(deliveryId), Integer.parseInt(bookId),
                Integer.parseInt(bookCount), Integer.parseInt(bookPrice));
    }

    @RequestMapping(value = "/book-deliveries", method = RequestMethod.GET)
    public List<BookDelivery> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/book-delivery", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/book-deliveries/by-delivery-id", method = RequestMethod.GET)
    public List<BookDelivery> searchByDelivery(@RequestParam("delivery-id") String deliveryId) {
        return service.findByDelivery(Integer.parseInt(deliveryId));
    }
    @RequestMapping(value = "/book-deliveries/by-delivery-id/page", method = RequestMethod.GET)
    public List<BookDelivery> searchByDeliveryPage(@RequestParam("page") String page,
                                                   @RequestParam("per-page") String perPage,
                                                   @RequestParam("delivery-id") String deliveryId) {
        return bookDeliverySearchService.getPage(Integer.parseInt(page), Integer.parseInt(perPage),
                service.findByDelivery(Integer.parseInt(deliveryId)));
    }
    @RequestMapping(value = "/book-deliveries/by-book-id", method = RequestMethod.GET)
    public List<BookDelivery> searchByPublishYearPeriod(@RequestParam("book-id") String bookId) {
        return service.findByBook(Integer.parseInt(bookId));
    }

    @RequestMapping(value = "/book-delivery", method = RequestMethod.GET)
    public BookDelivery get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }

    @RequestMapping(value = "/book-deliveries/by-all", method = RequestMethod.GET)
    public List<BookDelivery> searchByAll(@RequestParam("date-of-delivery-min") String deliveryDateMin,
                                                        @RequestParam("date-of-delivery-max") String deliveryDateMax,
                                                        @RequestParam("book-title") String bookTitle) {
        return bookDeliverySearchService.findBookDeliveriesByAll(bookTitle,
                Utilities.getDate(deliveryDateMin), Utilities.getDate(deliveryDateMax));
    }
    @RequestMapping(value = "/book-deliveries/by-all/page", method = RequestMethod.GET)
    public List<BookDelivery> searchByAllPage(@RequestParam("page") String page,
                                              @RequestParam("per-page") String perPage,
                                              @RequestParam("date-of-delivery-min") String deliveryDateMin,
                                          @RequestParam("date-of-delivery-max") String deliveryDateMax,
                                          @RequestParam("book-title") String bookTitle) {
        return bookDeliverySearchService.getPage(Integer.parseInt(page), Integer.parseInt(perPage),
                bookDeliverySearchService.findBookDeliveriesByAll(bookTitle,
                Utilities.getDate(deliveryDateMin), Utilities.getDate(deliveryDateMax)));
    }
}
