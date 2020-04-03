package com.kevins.domain.enums;

public enum TaxType {

    G("groceries"),
    PF("prepared food"),
    PD("prescription drug"),
    ND("non-prescription drug"),
    C("clothing"),
    O("other");

    private final String description;

    TaxType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
