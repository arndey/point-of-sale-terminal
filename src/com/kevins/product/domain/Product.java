package com.kevins.product.domain;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;

import java.math.BigDecimal;

public class Product {

    @CsvBindByPosition(position=0)
    private byte[] id;

    @CsvBindByPosition(position=1)
    private String name;

    @CsvBindByPosition(position = 2)
    private BigDecimal price;

    @CsvCustomBindByPosition(converter = TaxTypeConverter.class, position = 3)
    private TaxType taxType;

    public byte[] getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TaxType getTaxType() {
        return taxType;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", taxType=" + taxType.getDescription() +
                '}';
    }
}
