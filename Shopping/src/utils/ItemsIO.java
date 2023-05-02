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

public class ItemsIO { // input and output

    public static List<Item> read(String filename) throws IOException {
        File file = new File("");

        // caminho absoluto na máquina que está executando
        String basePath = file.getAbsolutePath();
        String filePath = basePath + "\\" + filename + ".txt";

        // ajuda a converter datas para textos e vice versa
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        var lines = Files.readAllLines(Path.of(filePath));
        List<Item> itemsRead = new ArrayList<>();

        for (var line : lines) { // for each line in lines
            // separa basedo numa regra
            var values = line.split(",");

            Item itemRead = null;

            // convertendo valores do CSV
            // todos os valores do csv vem em formato String
            // no meu programo utilizo variables tipadas
            if (values[0].equals("Food")) {
                itemRead = new FoodItem(
                        values[1],
                        Double.parseDouble(values[2]),
                        LocalDate.parse(values[3], formatter),
                        LocalDate.parse(values[4], formatter));
            } else if (values[0].equals("Clothing")) {
                itemRead = new ClothingItem(
                        values[1],
                        Double.parseDouble(values[2]),
                        values[3]);
            } else {
                itemRead = new CleaningSuppliesItem(
                        values[1],
                        Double.parseDouble(values[2]),
                        values[3]);
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

// "linha1", "linha2", "linha3"

// acc = ""
// acc = acc + "linha1" + "\n" = "linha1\n"
// acc = acc + "linha2" + "\n" = "linha1\nlinha2\n"
// acc = acc + "linha3" + "\n" = "linha1\nlinha2\nlinha3\n"
