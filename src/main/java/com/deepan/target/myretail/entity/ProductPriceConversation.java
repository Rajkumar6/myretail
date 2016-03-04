package com.deepan.target.myretail.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/1/16.
 */
public class ProductPriceConversation {
    private Long id;
    private String name;
    private CurrentPrice currentPrice;

    @JsonIgnore
    public ProductPriceConversation() {

    }

    @JsonCreator
    public ProductPriceConversation(@JsonProperty("id") Long id,
                                    @JsonProperty("name") String name,
                                    @JsonProperty("currentPrice") CurrentPrice currentPrice) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrentPrice getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal value, String currencyCode) {
        this.currentPrice = new CurrentPrice(value, currencyCode);
    }

    @Override
    public String toString() {
        return "ProductPriceConversation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
