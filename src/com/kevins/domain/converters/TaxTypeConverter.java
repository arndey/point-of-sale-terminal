package com.kevins.domain.converters;

import com.kevins.domain.enums.TaxType;
import com.opencsv.bean.AbstractBeanField;

public class TaxTypeConverter extends AbstractBeanField<String> {
    @Override
    protected TaxType convert(String s) {
        return TaxType.valueOf(s.toUpperCase());
    }
}
