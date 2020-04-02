package com.kevins.product.domain.converters;

import com.opencsv.bean.AbstractBeanField;

public class StringToByteArrayConverter extends AbstractBeanField<String> {
    @Override
    public byte[] convert(String s) {
        int length = s.length();

        char[] chars = s.toCharArray();
        byte[] result = new byte[length];

        for (byte i = 0; i < length; i++) {
            result[i] = (byte) Character.getNumericValue(chars[i]);
        }

        return result;
    }
}
