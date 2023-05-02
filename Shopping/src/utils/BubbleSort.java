package utils;

import java.util.Comparator;
import java.util.List;

public class BubbleSort {
    public static <T extends Comparable<T>> void sort(List<T> list) {
        boolean swapped = false;
        do {
            swapped = false;

            // looping elements
            for (int i = 0; i < list.size() - 1; i++) {
                // * vou até o tamanho - 1 pois não preciso comparar o ultimo.

                // comparing elements two by two
                if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                    // algoritmo de troca de dois elementos
                    T aux = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, aux);

                    // caso haja troca, swapped = true
                    swapped = true;
                }
            }

        } while (swapped);
    }
}

// 3 4 2 1 5
// 3 2 1 4 5 , swapped = true
// 2 1 3 4 5 , swapped = true
// 1 2 3 4 5 , swapped = true
// 1 2 3 4 5 , swapped = false
// acabou, está ordenado

// CompareTo
// greater than 0 if descending
// less than 0 if ascending
// equal to 0 if equal