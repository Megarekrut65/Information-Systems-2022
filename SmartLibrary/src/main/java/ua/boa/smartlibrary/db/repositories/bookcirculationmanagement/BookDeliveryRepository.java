package ua.boa.smartlibrary.db.repositories.bookcirculationmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.BookDelivery;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Delivery;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;

import java.util.List;

public interface BookDeliveryRepository extends JpaRepository<BookDelivery, Integer> {
    List<BookDelivery> findBookDeliveriesByDelivery(Delivery delivery);
    List<BookDelivery> findBookDeliveriesByBook(Book book);
}
