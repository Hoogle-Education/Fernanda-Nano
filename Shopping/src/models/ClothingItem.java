package models;

import models.abstracts.Item;
import models.enums.ItemCategory;

import java.util.Objects;

public class ClothingItem extends Item {
    private String brand;

    public ClothingItem(String name, double price, String brand) {
        super(name, price);
        this.category = ItemCategory.CLOTHING;
        this.brand = brand;
    }

    public ClothingItem(ClothingItem source) {
        super(source);
        this.brand = source.brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!super.equals(o)) return false;

        ClothingItem that = (ClothingItem) o;

        return Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        return result;
    }

    @Override
    public String toCsv() {
        return  super.toCsv()
                + "," + brand;
    }
}
