package Brute;

import java.util.*;

public class BruteUtilities {

    public static String PREFIX = "BruteExpose: ";
    public static void print(String message) {
        String sb = PREFIX +
                message;
        System.out.println(sb);
    }

    /*
        (Slightly Modified)

        Credits to:
        https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueAndLimit(Map<K, V> map, int limit) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        int limitTracker = 0;

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
            limitTracker++;
            if (list.size() == limitTracker || limitTracker == limit ) {
                break;
            }
        }
        return result;
    }
}
