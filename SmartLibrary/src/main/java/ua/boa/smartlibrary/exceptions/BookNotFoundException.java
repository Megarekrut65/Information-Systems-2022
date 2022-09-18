package ua.boa.smartlibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such book")
public class BookNotFoundException extends NotFoundByIdException{
    public BookNotFoundException(Integer id) {
        super(id, "Book");
    }
}
