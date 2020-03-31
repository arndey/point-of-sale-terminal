package com.kevins.product.domain;

import com.opencsv.bean.AbstractBeanField;

public class TaxTypeConverter extends AbstractBeanField<String> {
    @Override
    protected Object convert(String s) {
        return TaxType.valueOf(s.toUpperCase());
    }
}
