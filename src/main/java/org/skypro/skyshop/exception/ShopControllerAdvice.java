package org.skypro.skyshop.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ShopControllerAdvice {

    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<ShopError> handleProductNotFoundError(NoSuchProductException ex) {
        return ResponseEntity.status(404)
                .body(new ShopError("404-01", ex.getMessage()));
    }
}
