package ua.boa.smartlibrary.exceptions.customermanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.boa.smartlibrary.exceptions.NotFoundByIdException;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such book borrowing")
public class BookBorrowingNotFoundException extends NotFoundByIdException {
    public BookBorrowingNotFoundException(Integer id) {
        super(id, "BookBorrowing");
    }
}
