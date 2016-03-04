package com.deepan.target.myretail.exception;

import com.deepan.target.myretail.entity.ErrorCode;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/1/16.
 */
public class ItemNotFoundException extends Exception {

    private ErrorCode errorCode = ErrorCode.ITEM_NOT_FOUND;

    public ItemNotFoundException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
