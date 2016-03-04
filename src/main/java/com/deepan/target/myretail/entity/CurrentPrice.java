package com.deepan.target.myretail.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/3/16.
 */


public class CurrentPrice {

    @NotBlank
    @JsonProperty("value")
    private BigDecimal value;
    @NotBlank
    @Size(min = 3, max = 3)
    @JsonProperty("currencyCode")
    private String currencyCode;

    public CurrentPrice() {

    }

    @JsonIgnore
    public CurrentPrice(BigDecimal value, String currencyCode) {
        this.value = value;
        this.currencyCode = currencyCode;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return "CurrentPrice{" +
                "value='" + value.toPlainString() + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
