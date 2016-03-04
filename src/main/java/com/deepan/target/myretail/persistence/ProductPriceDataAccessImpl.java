package com.deepan.target.myretail.persistence;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.deepan.target.myretail.entity.ProductPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Component;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/2/16.
 */
@Component
public class ProductPriceDataAccessImpl implements ProductPriceDataAccess {

    @Autowired
    private CassandraOperations cassandraOperations;

    @Override
    public void insertProductPrice(ProductPrice productPrice) {
        cassandraOperations.insert(productPrice);
    }

    @Override
    public void updateProductPrice(ProductPrice productPrice) {
        cassandraOperations.update(productPrice);
    }

    @Override
    public ProductPrice selectProductPrice(Long productId) {
        Select query = QueryBuilder.select().from("product_price");
        query.where(QueryBuilder.eq("product_id", productId));
        return cassandraOperations.selectOne(query, ProductPrice.class);
    }

    //Simply for unit test
    public void setCassandraOperations(CassandraOperations cassandraOperations) {
        this.cassandraOperations = cassandraOperations;
    }
}
