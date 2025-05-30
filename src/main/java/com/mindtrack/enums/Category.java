package com.mindtrack.enums;

public enum Category {
    CONTENT("Satisfação"), AGREEMENT("Concordância"), IMPORTANCE("Importância"), PROBABILITY("Probabilidade"), FREQUENCY("Frequência");

    private String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
