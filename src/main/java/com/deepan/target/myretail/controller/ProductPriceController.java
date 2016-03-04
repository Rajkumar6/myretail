package com.deepan.target.myretail.controller;

import com.deepan.target.myretail.entity.ProductPriceConversation;
import com.deepan.target.myretail.exception.ExternalSystemException;
import com.deepan.target.myretail.exception.ItemNotFoundException;
import com.deepan.target.myretail.service.ProductPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/1/16.
 */
@RestController
public class ProductPriceController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductPriceController.class);

    @Autowired
    private ProductPriceService productPriceService;

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.GET)
    public ProductPriceConversation getProductPriceInfo(@PathVariable Long productId)
            throws ItemNotFoundException, ExternalSystemException {
        LOG.debug("Retrieving price information for product id " + productId);
        return productPriceService.retrieveProductPriceInfo(productId);
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.POST)
    public void updateProductPriceInfo(@PathVariable Long productId,
                                       @Valid @RequestBody ProductPriceConversation productPriceConversation) {
        LOG.debug("Updating the price of the product with " + productPriceConversation);
        if(!productId.equals(productPriceConversation.getId())) {
            throw new IllegalArgumentException("Product Id in the URL doesn't match with id in the request body");
        }
        productPriceService.insertOrUpdateProductPriceInfo(productId, productPriceConversation);
    }

    //Simply for unit test
    public void setProductPriceService(ProductPriceService productPriceService) {
        this.productPriceService = productPriceService;
    }
}
