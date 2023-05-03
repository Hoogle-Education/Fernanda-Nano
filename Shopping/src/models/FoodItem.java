package models;

import models.abstracts.Item;
import models.enums.ItemCategory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class FoodItem extends Item {

    // atributos
    private LocalDate producedIn;
    private LocalDate expiresIn;

    // constructor
    public FoodItem(String name, double price, LocalDate producedIn, LocalDate expiresIn) {
        super(name, price);
        this.category = ItemCategory.FOOD;
        this.producedIn = producedIn;
        this.expiresIn = expiresIn;
    }

    public FoodItem(FoodItem source) {
        super(source);
        this.producedIn = source.producedIn;
        this.expiresIn = source.expiresIn;
    }

    public LocalDate getProducedIn() {
        return producedIn;
    }

    public void setProducedIn(LocalDate producedIn) {
        this.producedIn = producedIn;
    }

    public LocalDate getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(LocalDate expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!super.equals(o)) return false;

        FoodItem foodItem = (FoodItem) o;

        if (!Objects.equals(producedIn, foodItem.producedIn)) return false;
        return Objects.equals(expiresIn, foodItem.expiresIn);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (producedIn != null ? producedIn.hashCode() : 0);
        result = 31 * result + (expiresIn != null ? expiresIn.hashCode() : 0);
        return result;
    }

    @Override
    public String toCsv() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        return  super.toCsv()
                + "," + formatter.format(producedIn)
                + "," + formatter.format(expiresIn);
    }
}
