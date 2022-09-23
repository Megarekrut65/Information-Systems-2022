package ua.boa.smartlibrary.exceptions.bookcirculationmanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.boa.smartlibrary.exceptions.NotFoundByIdException;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such book lost")
public class BookLostNotFoundException extends NotFoundByIdException {
    public BookLostNotFoundException(Integer id) {
        super(id, "BookLost");
    }
}
