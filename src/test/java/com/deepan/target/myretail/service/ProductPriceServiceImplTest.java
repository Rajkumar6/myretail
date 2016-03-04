package com.deepan.target.myretail.service;

import com.deepan.target.myretail.entity.ProductPrice;
import com.deepan.target.myretail.entity.ProductPriceConversation;
import com.deepan.target.myretail.exception.ItemNotFoundException;
import com.deepan.target.myretail.persistence.ProductPriceDataAccess;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/3/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductPriceServiceImplTest {

    private ProductPriceServiceImpl target;
    @Mock
    private ProductPriceDataAccess productPriceDataAccess;
    @Captor
    private ArgumentCaptor<ProductPrice> productPriceCaptor;

    @Before
    public void setUp() throws Exception {
        target = new ProductPriceServiceImpl();
        target.setProductPriceDataAccess(productPriceDataAccess);
    }

    @Test
    public void testRetrieveProductPriceInfo() throws Exception {
        Long productId = 16483589L;
        ProductPrice productPrice = generateProductPrice(productId, "100.00");
        when(productPriceDataAccess.selectProductPrice(productId)).thenReturn(productPrice);
        ProductPriceConversation result = target.retrieveProductPriceInfo(productId);
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("AT&T iPhone 6 plus Gold 128GB", result.getName());
        assertEquals("USD", result.getCurrentPrice().getCurrencyCode());
        assertEquals(new BigDecimal("100.00"), result.getCurrentPrice().getValue());
    }

    @Test(expected = ItemNotFoundException.class)
    public void testRetrieveProductPriceInfo_productNameNotFound() throws Exception {
        Long productId = 12345676L;
        ProductPrice productPrice = generateProductPrice(productId, "249.99");
        when(productPriceDataAccess.selectProductPrice(productId)).thenReturn(productPrice);
        target.retrieveProductPriceInfo(productId);
        verifyZeroInteractions(productPriceDataAccess);
    }

    @Test(expected = ItemNotFoundException.class)
    public void testRetrieveProductPriceInfo_productPriceNotFound() throws Exception {
        Long productId = 16483589L;
        when(productPriceDataAccess.selectProductPrice(productId)).thenReturn(null);
        target.retrieveProductPriceInfo(productId);
    }

    @Test
    public void testInsertOrUpdateProductPriceInfo() throws Exception {
        Long productId = 123456L;
        ProductPriceConversation productPriceConv = generateProductPriceConv(productId);
        target.insertOrUpdateProductPriceInfo(productId, productPriceConv);
        verify(productPriceDataAccess, times(1)).updateProductPrice(productPriceCaptor.capture());
        assertResults(productPriceConv, productPriceCaptor.getValue());
    }

    private void assertResults(ProductPriceConversation productPriceConv, ProductPrice productPrice) {
        assertEquals(productPriceConv.getId(), productPrice.getProductId());
        assertEquals(productPriceConv.getCurrentPrice().getCurrencyCode(), productPrice.getCcyCode());
        assertEquals(productPriceConv.getCurrentPrice().getValue(), productPrice.getPrice());
        assertNotNull(productPrice.getLastModifiedTS());
    }

    private ProductPrice generateProductPrice(Long productId, String price) {
        ProductPrice productPrice = new ProductPrice();
        productPrice.setProductId(productId);
        productPrice.setCcyCode("USD");
        productPrice.setPrice(new BigDecimal(price));
        return productPrice;
    }

    private ProductPriceConversation generateProductPriceConv(Long productId) {
        ProductPriceConversation conversation = new ProductPriceConversation();
        conversation.setId(productId);
        conversation.setName("product-name");
        conversation.setCurrentPrice(new BigDecimal("100.00"), "USD");
        return conversation;
    }
}