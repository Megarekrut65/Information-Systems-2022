package ua.boa.smartlibrary.exceptions.bookcirculationmanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.boa.smartlibrary.exceptions.NotFoundByIdException;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such book delivery")
public class BookDeliveryNotFoundException extends NotFoundByIdException {
    public BookDeliveryNotFoundException(Integer id) {
        super(id, "BookDelivery");
    }
}
