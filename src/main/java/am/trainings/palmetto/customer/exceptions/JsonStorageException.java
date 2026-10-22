package am.trainings.palmetto.customer.exceptions;

import am.trainings.palmetto.common.BaseException;

public class JsonStorageException extends BaseException {

    public JsonStorageException(String message) {
        super(message);
    }

    public JsonStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}