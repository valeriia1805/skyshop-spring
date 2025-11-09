package org.skypro.skyshop.exception;

import java.util.UUID;

public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException(UUID id) {
        super("Товар с id = %s отсутствует.".formatted(id));
    }
}
