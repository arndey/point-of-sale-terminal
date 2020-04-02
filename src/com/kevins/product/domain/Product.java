package com.kevins.product.domain;

import com.kevins.product.domain.converters.StringToByteArrayConverter;
import com.kevins.product.domain.converters.TaxTypeConverter;
import com.kevins.product.domain.enums.TaxType;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;

import java.math.BigDecimal;
import java.util.Arrays;

public class Product {

    @CsvCustomBindByPosition(converter = StringToByteArrayConverter.class, position = 0)
    private byte[] id;

    @CsvBindByPosition(position = 1)
    private String name;

    @CsvBindByPosition(position = 2)
    private BigDecimal price;

    @CsvCustomBindByPosition(converter = TaxTypeConverter.class, position = 3)
    private TaxType taxType;

    public String getIdAsString() {
        StringBuilder sb = new StringBuilder();
        for (byte b : id) {
            sb.append(b);
        }

        return sb.toString();
    }

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
                "id=" + Arrays.toString(id) +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", taxType=" + taxType.getDescription() +
                '}';
    }
}
