package com.kevins.domain;

import com.kevins.domain.converters.StringToByteArrayConverter;
import com.kevins.domain.converters.TaxTypeConverter;
import com.kevins.domain.enums.TaxType;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;

import java.math.BigDecimal;

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
        return "name=" + name +
                ", id=" + getIdAsString() +
                ", price=" + price +
                ", taxType=" + taxType;
    }
}
