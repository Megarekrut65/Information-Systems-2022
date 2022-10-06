package ua.boa.smartlibrary.db.repositories.bookcirculationmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookDelivery;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Delivery;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

public interface BookDeliveryRepository extends JpaRepository<BookDelivery, Integer> {
    List<BookDelivery> findBookDeliveriesByDelivery(Delivery delivery);

    List<BookDelivery> findBookDeliveriesByBook(Book book);

    List<BookDelivery> findBookDeliveriesByBookIn(Collection<Book> book);

    List<BookDelivery> findBookDeliveriesByDelivery_DateOfDeliveryBetweenAndBookIn(Date deliveryDateOfDeliveryMin,
                                                                                   Date deliveryDateOfDeliveryMax,
                                                                                   Collection<Book> book);

    List<BookDelivery> findBookDeliveriesByDelivery_DateOfDeliveryGreaterThanAndBookIn(Date deliveryDateOfDelivery,
                                                                                       Collection<Book> book);

    List<BookDelivery> findBookDeliveriesByDelivery_DateOfDeliveryLessThanAndBookIn(Date deliveryDateOfDelivery,
                                                                                    Collection<Book> book);
}
