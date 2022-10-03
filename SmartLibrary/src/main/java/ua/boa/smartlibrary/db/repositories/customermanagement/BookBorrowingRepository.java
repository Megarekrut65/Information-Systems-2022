package ua.boa.smartlibrary.db.repositories.customermanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.dataclasses.customermanagement.BookBorrowing;
import ua.boa.smartlibrary.dataclasses.customermanagement.Customer;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

public interface BookBorrowingRepository extends JpaRepository<BookBorrowing, Integer> {
    List<BookBorrowing> findBookBorrowingByBook(Book book);

    List<BookBorrowing> findBookBorrowingByCustomer(Customer customer);

    List<BookBorrowing> findBookBorrowingByCustomerAndActualDateOfReturnEquals(Customer customer,
                                                                               Date actualDateOfReturn);

    List<BookBorrowing> findBookBorrowingByDateOfBorrowingBetween(Date dateOfBorrowingMin, Date dateOfBorrowingMax);
    List<BookBorrowing> findBookBorrowingByDateOfBorrowingBetweenAndBookInAndCustomerIn(Date dateOfBorrowing,
                                                                                        Date dateOfBorrowing2,
                                                                                        Collection<Book> book,
                                                                                        Collection<Customer> customer);
    List<BookBorrowing> findBookBorrowingByDateOfBorrowingGreaterThanAndBookInAndCustomerIn(Date dateOfBorrowing,
                                                                                            Collection<Book> book,
                                                                                            Collection<Customer> customer);
    List<BookBorrowing> findBookBorrowingByDateOfBorrowingLessThanAndBookInAndCustomerIn(Date dateOfBorrowing,
                                                                                         Collection<Book> book,
                                                                                         Collection<Customer> customer);
    List<BookBorrowing> findBookBorrowingByBookInAndCustomerIn(Collection<Book> book, Collection<Customer> customer);
}
