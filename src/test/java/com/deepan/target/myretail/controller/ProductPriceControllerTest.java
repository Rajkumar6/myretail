package com.deepan.target.myretail.controller;

import com.deepan.target.myretail.entity.ProductPriceConversation;
import com.deepan.target.myretail.service.ProductPriceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/3/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductPriceControllerTest {

    private ProductPriceController target;
    @Mock
    private ProductPriceService productPriceService;

    @Before
    public void setUp() throws Exception {
        target = new ProductPriceController();
        target.setProductPriceService(productPriceService);
    }

    @Test
    public void testGetProductPriceInfo() throws Exception {
        ProductPriceConversation productPriceConv = getProductPriceConv(123456L);
        when(productPriceService.retrieveProductPriceInfo(123456L)).thenReturn(productPriceConv);
        ProductPriceConversation result = target.getProductPriceInfo(123456L);
        verify(productPriceService, times(1)).retrieveProductPriceInfo(anyLong());
        assertResult(productPriceConv, result);
    }

    @Test
    public void testUpdateProductPriceInfo() throws Exception {
        Long productId = 123456L;
        ProductPriceConversation productPriceConv = getProductPriceConv(productId);
        target.updateProductPriceInfo(productId, productPriceConv);
        verify(productPriceService, times(1)).insertOrUpdateProductPriceInfo(productId, productPriceConv);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateProductPriceInfo_idMisMatch() throws Exception {
        Long productId = 123456L;
        ProductPriceConversation productPriceConv = getProductPriceConv(654321L);
        target.updateProductPriceInfo(productId, productPriceConv);
        verifyZeroInteractions(productPriceService);
    }

    private void assertResult(ProductPriceConversation expected, ProductPriceConversation actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCurrentPrice().getValue(), actual.getCurrentPrice().getValue());
        assertEquals(expected.getCurrentPrice().getCurrencyCode(), actual.getCurrentPrice().getCurrencyCode());
    }

    private ProductPriceConversation getProductPriceConv(Long productId) {
        ProductPriceConversation conversation = new ProductPriceConversation();
        conversation.setId(productId);
        conversation.setName("product-name");
        conversation.setCurrentPrice(new BigDecimal("100.00"), "USD");
        return conversation;
    }
}