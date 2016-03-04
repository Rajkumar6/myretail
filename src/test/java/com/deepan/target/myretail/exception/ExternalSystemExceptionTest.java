package com.deepan.target.myretail.exception;

import com.deepan.target.myretail.entity.ErrorCode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/3/16.
 */
public class ExternalSystemExceptionTest {

    private static final String ERROR_MESSAGE = "Error accessing external System";

    private ExternalSystemException target;

    @Test
    public void testGetErrorCode() throws Exception {
        target = new ExternalSystemException(ERROR_MESSAGE);
        assertEquals(ErrorCode.EXT_SYS_ERROR, target.getErrorCode());
        assertEquals(ERROR_MESSAGE, target.getMessage());
    }
}