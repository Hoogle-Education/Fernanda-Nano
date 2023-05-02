package utils;

import java.util.Comparator;
import java.util.List;

public class BubbleSort {
    public static <T extends Comparable<T>> void sort(List<T> list) {
        boolean swapped = false;
        do {
            swapped = false;

            for(int i = 0; i < list.size() - 1; i++) {
                if(list.get(i).compareTo(list.get(i+1)) > 0) {
                    T aux = list.get(i);
                    list.set(i, list.get(i+1));
                    list.set(i+1, aux);
                    swapped = true;
                }
            }

        } while(swapped);
    }
}
