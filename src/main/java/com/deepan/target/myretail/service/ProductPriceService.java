package com.deepan.target.myretail.service;

import com.deepan.target.myretail.entity.ProductPriceConversation;
import com.deepan.target.myretail.exception.ExternalSystemException;
import com.deepan.target.myretail.exception.ItemNotFoundException;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/1/16.
 */
public interface ProductPriceService {

    ProductPriceConversation retrieveProductPriceInfo(Long productId) throws ItemNotFoundException, ExternalSystemException;

    void insertOrUpdateProductPriceInfo(Long productId, ProductPriceConversation productPriceConversation);
}
