package com.deepan.target.myretail.exception;

import com.deepan.target.myretail.entity.ErrorCode;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/2/16.
 */
public class ExternalSystemException extends Exception {

    private ErrorCode errorCode = ErrorCode.EXT_SYS_ERROR;

    public ExternalSystemException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
