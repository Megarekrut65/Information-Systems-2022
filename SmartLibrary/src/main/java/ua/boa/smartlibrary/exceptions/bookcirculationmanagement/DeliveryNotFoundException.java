package ua.boa.smartlibrary.exceptions.bookcirculationmanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.boa.smartlibrary.exceptions.NotFoundByIdException;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such delivery")
public class DeliveryNotFoundException extends NotFoundByIdException {
    public DeliveryNotFoundException(Integer id) {
        super(id, "Delivery");
    }
}
