package com.deepan.target.myretail.exception;

import com.deepan.target.myretail.entity.ErrorCode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/3/16.
 */
public class ItemNotFoundExceptionTest {

    private static final String ERROR_MESSAGE = "Product not found in the data store";
    private ItemNotFoundException target;

    @Test
    public void testGetErrorCode() throws Exception {
        target = new ItemNotFoundException(ERROR_MESSAGE);
        assertEquals(ErrorCode.ITEM_NOT_FOUND, target.getErrorCode());
        assertEquals(ERROR_MESSAGE, target.getMessage());
    }
}