package ua.boa.smartlibrary.exceptions.bookmanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.boa.smartlibrary.exceptions.NotFoundByIdException;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such book tag")
public class BookTagNotFoundException extends NotFoundByIdException {
    public BookTagNotFoundException(Integer id) {
        super(id, "BookTag");
    }
}
