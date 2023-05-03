package models;

import exceptions.InvalidItemIndexException;
import exceptions.OutOfStockException;
import models.abstracts.Item;
import utils.ItemsIO;

import java.util.List;

public class Store {

    private static List<StoreElement> items = ItemsIO.read("items");

    public static List<StoreElement> getItems() {
        return items;
    }
    public static List<StoreElement> getAvailableItems() {
        return items
                .stream()
                .filter(stElement -> stElement.getQuantity() > 0)
                .toList();
    }

    public static Item getItemByQuantity(int itemId, int quantity)
            throws InvalidItemIndexException, OutOfStockException {

        if(itemId >= items.size())
            throw new InvalidItemIndexException("The index must be in the items available items");

        StoreElement inStoreElement = items.get(itemId);

        if(inStoreElement.getQuantity() < quantity)
            throw new OutOfStockException("This item has insufficient stock or is out of Stock");

        inStoreElement.decrementQuantity(quantity);
        return inStoreElement.getItem();
    }

}
