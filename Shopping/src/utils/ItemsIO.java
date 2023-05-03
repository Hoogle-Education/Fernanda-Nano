package utils;

import models.*;
import models.abstracts.Item;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ItemsIO { // input and output

    public static List<StoreElement> read(String filename) {
        String filePath = getPath(filename);

        // ajuda a converter datas para textos e vice versa
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        try {
            var lines = Files.readAllLines(Path.of(filePath));
            List<StoreElement> inStoreItems = new ArrayList<>();

            for (var line : lines) { // for each line in lines
                // separa basedo numa regra
                var values = line.split(",");

                Item itemRead = null;

                // convertendo valores do CSV
                // todos os valores do csv vem em formato String
                // no meu programo utilizo variables tipadas
                if (values[0].equals("Food")) {
                    itemRead = new FoodItem(
                            values[2],
                            Double.parseDouble(values[3]),
                            LocalDate.parse(values[4], formatter),
                            LocalDate.parse(values[5], formatter));
                } else if (values[0].equals("Clothing")) {
                    itemRead = new ClothingItem(
                            values[2],
                            Double.parseDouble(values[3]),
                            values[4]);
                } else {
                    itemRead = new CleaningSuppliesItem(
                            values[2],
                            Double.parseDouble(values[3]),
                            values[4]);
                }

                inStoreItems.add(new StoreElement(itemRead, Integer.parseInt(values[1])));
            }

            return inStoreItems;
        } catch (IOException iox) {
            System.err.println("Cannot read this file.");
            return null;
        }
    }

    public static void writeCartElements(List<CartElement> items, String filename) {
        var csvLines = items.stream().map(i -> i.toCsv()).toList();
        var csv = csvLines.stream().reduce("", (acc, line) -> acc + line + "\n");

        String filePath = getPath(filename);

        try {
            Files.writeString(Path.of(filePath), csv);
        } catch (IOException iox) {
            System.err.println("There was and error to writeCartElements in file: " + filename);
        }
    }

    public static void writeStoreElements(List<StoreElement> storeElements, String filename) {
        var csvLines = storeElements.stream().map(e -> e.toCsv()).toList();
        var csv = csvLines.stream().reduce("", (acc, line) -> acc + line + "\n");

        String filePath = getPath(filename);

        try {
            Files.writeString(Path.of(filePath), csv);
        } catch (IOException iox) {
            System.err.println("There was and error to writeCartElements in file: " + filename);
        }
    }

    private static String getPath(String filename) {
        File file = new File("");
        String basePath = file.getAbsolutePath();
        return  basePath + File.separator + filename + ".txt";
    }

}
