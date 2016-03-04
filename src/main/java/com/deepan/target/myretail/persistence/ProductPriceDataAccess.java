package com.deepan.target.myretail.persistence;

import com.deepan.target.myretail.entity.ProductPrice;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/2/16.
 */
public interface ProductPriceDataAccess {
    void insertProductPrice(ProductPrice productPrice);
    void updateProductPrice(ProductPrice productPrice);
    ProductPrice selectProductPrice(Long productId);
}
