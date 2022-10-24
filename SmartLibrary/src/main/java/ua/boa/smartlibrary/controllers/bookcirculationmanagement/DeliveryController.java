package ua.boa.smartlibrary.controllers.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.Utilities;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Delivery;
import ua.boa.smartlibrary.services.bookcirculationmanagement.DeliverySearchService;
import ua.boa.smartlibrary.services.bookcirculationmanagement.DeliveryService;

import java.util.List;

@RestController
public class DeliveryController {
    @Autowired
    private DeliveryService service;
    @Autowired
    private DeliverySearchService deliverySearchService;

    @RequestMapping(value = "/delivery", method = RequestMethod.POST)
    public Delivery create(@RequestParam("date-of-delivery") String dateOfDelivery,
                           @RequestParam("distributor-id") String distributorId) {
        return service.create(Utilities.getDate(dateOfDelivery), Integer.parseInt(distributorId));
    }

    @RequestMapping(value = "/delivery", method = RequestMethod.PUT)
    public Delivery update(@RequestParam("id") String id, @RequestParam("date-of-delivery") String dateOfDelivery,
                           @RequestParam("distributor-id") String distributorId) {
        return service.update(Integer.parseInt(id), Utilities.getDate(dateOfDelivery), Integer.parseInt(distributorId));
    }

    @RequestMapping(value = "/deliveries", method = RequestMethod.GET)
    public List<Delivery> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/delivery", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/deliveries/by-distributor-id", method = RequestMethod.GET)
    public List<Delivery> searchByDistributor(@RequestParam("distributor-id") String distributorId) {
        return service.findByDistributor(Integer.parseInt(distributorId));
    }
    @RequestMapping(value = "/deliveries/by-distributor-id/page", method = RequestMethod.GET)
    public List<Delivery> searchByDistributorPage(@RequestParam("page") String page,
                                                  @RequestParam("per-page") String perPage,
                                                  @RequestParam("distributor-id") String distributorId) {
        return deliverySearchService.getPage(Integer.parseInt(page), Integer.parseInt(perPage),
                service.findByDistributor(Integer.parseInt(distributorId)));
    }
    @RequestMapping(value = "/deliveries/by-date-of-delivery-period", method = RequestMethod.GET)
    public List<Delivery> searchByDatePeriod(@RequestParam("date-of-delivery-min") String dateOfDeliveryMin,
                                             @RequestParam("date-of-delivery-max") String dateOfDeliveryMax) {
        return service.findByDatePeriod(Utilities.getDate(dateOfDeliveryMin), Utilities.getDate(dateOfDeliveryMax));
    }

    @RequestMapping(value = "/deliveries/by-all", method = RequestMethod.GET)
    public List<Delivery> searchByAll(@RequestParam("distributor-name") String distributorName,
                                      @RequestParam("date-of-delivery-min") String dateOfDeliveryMin,
                                      @RequestParam("date-of-delivery-max") String dateOfDeliveryMax) {
        return deliverySearchService.findDeliveriesByAll(distributorName,
                Utilities.getDate(dateOfDeliveryMin), Utilities.getDate(dateOfDeliveryMax));
    }
    @RequestMapping(value = "/deliveries/by-all/page", method = RequestMethod.GET)
    public List<Delivery> searchByAllPage(@RequestParam("page") String page,
                                          @RequestParam("per-page") String perPage,
                                          @RequestParam("distributor-name") String distributorName,
                                      @RequestParam("date-of-delivery-min") String dateOfDeliveryMin,
                                      @RequestParam("date-of-delivery-max") String dateOfDeliveryMax) {
        return deliverySearchService.getPage(Integer.parseInt(page), Integer.parseInt(perPage),
                deliverySearchService.findDeliveriesByAll(distributorName,
                Utilities.getDate(dateOfDeliveryMin), Utilities.getDate(dateOfDeliveryMax)));
    }
    @RequestMapping(value = "/delivery", method = RequestMethod.GET)
    public Delivery get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }
}
