package org.skypro.skyshop.exception;

import lombok.Data;

@Data
public class ShopError {

    private final String code;
    private final String message;
}
