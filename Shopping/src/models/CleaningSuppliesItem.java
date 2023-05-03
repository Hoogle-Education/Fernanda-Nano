package models;

import models.abstracts.Item;
import models.enums.ItemCategory;

import java.util.Objects;

public class CleaningSuppliesItem extends Item {
    private String warning;

    public CleaningSuppliesItem(String name, double price, String warning) {
        super(name, price);
        this.category = ItemCategory.CLEANING_SUPPLIES;
        this.warning = warning;
    }

    public CleaningSuppliesItem(CleaningSuppliesItem source) {
        super(source);
        this.warning = source.warning;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!super.equals(o)) return false;

        CleaningSuppliesItem that = (CleaningSuppliesItem) o;

        return Objects.equals(warning, that.warning);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (warning != null ? warning.hashCode() : 0);
        return result;
    }

    @Override
    public String toCsv() {
        return "CleaningSupplies," + super.toCsv()
                + "," + warning;
    }
}
