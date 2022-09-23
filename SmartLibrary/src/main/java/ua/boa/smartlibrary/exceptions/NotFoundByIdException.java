package ua.boa.smartlibrary.exceptions;

public class NotFoundByIdException extends RuntimeException {
    private final Integer id;
    private final String object;

    public NotFoundByIdException(Integer id, String object) {
        this.id = id;
        this.object = object;
    }

    @Override
    public String getMessage() {
        return object + " with id=" + id + " not found!";
    }
}
