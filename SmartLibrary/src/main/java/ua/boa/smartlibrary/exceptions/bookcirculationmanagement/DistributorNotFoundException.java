package ua.boa.smartlibrary.exceptions.bookcirculationmanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.boa.smartlibrary.exceptions.NotFoundByIdException;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such distributor")
public class DistributorNotFoundException extends NotFoundByIdException {

    public DistributorNotFoundException(Integer id) {
        super(id, "Distributor");
    }
}
