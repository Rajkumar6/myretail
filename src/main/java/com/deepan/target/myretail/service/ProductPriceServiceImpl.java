package com.deepan.target.myretail.service;

import com.deepan.target.myretail.entity.ProductPrice;
import com.deepan.target.myretail.entity.ProductPriceConversation;
import com.deepan.target.myretail.exception.ExternalSystemException;
import com.deepan.target.myretail.exception.ItemNotFoundException;
import com.deepan.target.myretail.persistence.ProductPriceDataAccess;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/1/16.
 */
@Component
public class ProductPriceServiceImpl implements ProductPriceService {

    private static final String REQUEST_URL = "https://api.target.com/products/v3/%s?fields=descriptions&id_type=TCIN&key=43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz";
    private static final String APPLiCATION_JSON = "application/json";

    @Autowired
    private ProductPriceDataAccess productPriceDataAccess;

    @Override
    public ProductPriceConversation retrieveProductPriceInfo(Long productId) throws ItemNotFoundException, ExternalSystemException {
        String productName = getProductName(productId);
        ProductPrice productPrice = getProductPriceFromDB(productId);
        ProductPriceConversation productPriceConversation = new ProductPriceConversation();
        productPriceConversation.setId(productId);
        productPriceConversation.setName(productName);
        productPriceConversation.setCurrentPrice(productPrice.getPrice(), productPrice.getCcyCode());
        return productPriceConversation;
    }

    @Override
    public void insertOrUpdateProductPriceInfo(Long productId, ProductPriceConversation productPriceConversation) {
        ProductPrice productPrice = new ProductPrice();
        productPrice.setProductId(productId);
        productPrice.setPrice(productPriceConversation.getCurrentPrice().getValue());
        productPrice.setCcyCode(productPriceConversation.getCurrentPrice().getCurrencyCode());
        productPriceDataAccess.updateProductPrice(productPrice);
    }

    private ProductPrice getProductPriceFromDB(Long productId) throws ItemNotFoundException {
        ProductPrice productPrice = productPriceDataAccess.selectProductPrice(productId);
        if(productPrice == null) {
            throw new ItemNotFoundException("Price Information not available for this product");
        }
        return productPrice;
    }

    private String getProductName(Long productId) throws ItemNotFoundException, ExternalSystemException {
        Client client = Client.create();
        WebResource webResource = client.resource(String.format(REQUEST_URL, productId));
        ClientResponse response = webResource.accept(APPLiCATION_JSON).get(ClientResponse.class);
        if(response.getStatus() != 200) {
            throw new ExternalSystemException("Exception while accessing product information");
        } else {
            return retrieveProductName(response);
        }
    }

    private String retrieveProductName(ClientResponse response) throws ItemNotFoundException {
        JSONObject jsonResponseItem = new JSONObject(response.getEntity(String.class))
                .getJSONObject("product_composite_response")
                .getJSONArray("items").getJSONObject(0);
        if(jsonResponseItem.has("general_description")) {
            return jsonResponseItem.getString("general_description");
        } else {
            String errorResponse = jsonResponseItem.getJSONArray("errors").getJSONObject(0).getString("message");
            throw new ItemNotFoundException(errorResponse);
        }
    }

    public void setProductPriceDataAccess(ProductPriceDataAccess productPriceDataAccess) {
        this.productPriceDataAccess = productPriceDataAccess;
    }
}
