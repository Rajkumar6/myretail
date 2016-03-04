package com.deepan.target.myretail.entity;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/1/16.
 */
@Table(value = "product_price")
public class ProductPrice {

    @PrimaryKey(value = "product_id")
    private Long productId;
    @Column(value = "price")
    private BigDecimal price;
    @Column(value = "ccy_code")
    private String ccyCode;
    @Column(value = "last_modified_ts")
    private Date lastModifiedTS;

    public ProductPrice() {
        this.lastModifiedTS = new Date();
    }

    public ProductPrice(Long productId, BigDecimal price, String ccyCode) {
        this.productId = productId;
        this.price = price;
        this.ccyCode = ccyCode;
        this.lastModifiedTS = new Date();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCcyCode() {
        return ccyCode;
    }

    public void setCcyCode(String ccyCode) {
        this.ccyCode = ccyCode;
    }

    public Date getLastModifiedTS() {
        return lastModifiedTS;
    }

    public void setLastModifiedTS(Date lastModifiedTS) {
        this.lastModifiedTS = lastModifiedTS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductPrice that = (ProductPrice) o;

        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (ccyCode != null ? !ccyCode.equals(that.ccyCode) : that.ccyCode != null) return false;
        return !(lastModifiedTS != null ? !lastModifiedTS.equals(that.lastModifiedTS) : that.lastModifiedTS != null);

    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (ccyCode != null ? ccyCode.hashCode() : 0);
        result = 31 * result + (lastModifiedTS != null ? lastModifiedTS.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "productId=" + productId +
                ", price=" + price +
                ", ccyCode='" + ccyCode + '\'' +
                ", lastModifiedTS=" + lastModifiedTS +
                '}';
    }
}
