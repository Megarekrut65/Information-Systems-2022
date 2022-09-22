package ua.boa.smartlibrary.exceptions.bookmanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.boa.smartlibrary.exceptions.NotFoundByIdException;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such book")
public class BookNotFoundException extends NotFoundByIdException {
    public BookNotFoundException(Integer id) {
        super(id, "Book");
    }
}
