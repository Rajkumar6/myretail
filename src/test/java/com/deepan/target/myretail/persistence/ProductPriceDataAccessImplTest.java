package com.deepan.target.myretail.persistence;

import com.datastax.driver.core.querybuilder.Select;
import com.deepan.target.myretail.entity.ProductPrice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.cassandra.core.CassandraOperations;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/3/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductPriceDataAccessImplTest {

    private ProductPriceDataAccessImpl target;
    @Mock
    private CassandraOperations cassandraOperations;
    @Captor
    private ArgumentCaptor<ProductPrice> productPriceCaptor;
    @Captor
    private ArgumentCaptor<Select> selectCaptor;

    @Before
    public void setUp() throws Exception {
        target = new ProductPriceDataAccessImpl();
        target.setCassandraOperations(cassandraOperations);
    }

    @Test
    public void testInsertProductPrice() throws Exception {
        ProductPrice productPrice = generateProductPrice();
        target.insertProductPrice(productPrice);
        verify(cassandraOperations, times(1)).insert(productPriceCaptor.capture());
        assertResult(productPrice, productPriceCaptor.getValue());
    }

    @Test
    public void testUpdateProductPrice() throws Exception {
        ProductPrice productPrice = generateProductPrice();
        target.updateProductPrice(productPrice);
        verify(cassandraOperations, times(1)).update(productPriceCaptor.capture());
        assertResult(productPrice, productPriceCaptor.getValue());
    }

    @Test
    public void testGetProductPrice() throws Exception {
        ProductPrice productPrice = generateProductPrice();
        when(cassandraOperations.selectOne((Select) anyObject(), any())).thenReturn(productPrice);
        ProductPrice result = target.selectProductPrice(123456L);
        verify(cassandraOperations, times(1)).selectOne(selectCaptor.capture(), eq(ProductPrice.class));
        Select select = selectCaptor.getValue();
        assertEquals("SELECT * FROM product_price WHERE product_id=123456;", selectCaptor.getValue().getQueryString());
    }

    private void assertResult(ProductPrice expected, ProductPrice actual) {
        assertEquals(expected.getProductId(), actual.getProductId());
        assertEquals(expected.getCcyCode(), actual.getCcyCode());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getLastModifiedTS(), actual.getLastModifiedTS());
    }

    private ProductPrice generateProductPrice() {
        ProductPrice productPrice = new ProductPrice();
        productPrice.setProductId(123456L);
        productPrice.setCcyCode("USD");
        productPrice.setPrice(new BigDecimal("100.00"));
        return productPrice;
    }
}