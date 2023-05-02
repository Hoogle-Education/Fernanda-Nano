package utils;

import models.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ItemsIO {

    public static List<Item> read(String filename) throws IOException {
        File file = new File("");
        String basePath = file.getAbsolutePath();
        String filePath = basePath + "\\" + filename + ".txt";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        var lines = Files.readAllLines(Path.of(filePath));
        List<Item> itemsRead = new ArrayList<>();

        for (var line : lines) {
            var values = line.split(",");
            Item itemRead = null;

            if(values[0].equals("Food")) {
                itemRead = new FoodItem(
                        values[1],
                        Double.parseDouble(values[2]),
                        LocalDate.parse(values[3], formatter),
                        LocalDate.parse(values[4], formatter)
                );
            } else if(values[0].equals("Clothing")) {
                itemRead = new ClothingItem(
                        values[1],
                        Double.parseDouble(values[2]),
                        values[3]
                );
            } else {
                itemRead = new CleaningSuppliesItem(
                        values[1],
                        Double.parseDouble(values[2]),
                        values[3]
                );
            }

            itemsRead.add(itemRead);
        }

        return itemsRead;
    }

    public static void write(List<CartElement> items, String filename) throws IOException {
        var csvLines = items.stream().map(i -> i.toCsv()).toList();
        var csv = csvLines.stream().reduce("", (acc, line) -> acc + line + "\n");
        File file = new File("");
        String basePath = file.getAbsolutePath();
        String filePath = basePath + "\\" + filename + ".txt";

        Files.writeString(Path.of(filePath), csv);
    }

}
