package com.zlrx.java.advanced.classes.three;

public enum City {
    BUDAPEST(1000), ZUGLO(BUDAPEST), TAKSONY(2000), NOSZLOP(3000), PECS(4000);

    private int zipCode;

    City(int zipCode) {
        this.zipCode = zipCode;
    }

    City(City city) {
        zipCode = city.zipCode;
    }

    public int getZipCode() {
        return zipCode;
    }

    @Override
    public String toString() {
        return name() + "(" + zipCode + ")";
    }
}
