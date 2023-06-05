package main;

public class BruteUtilities {

    public static String PREFIX = "BruteExpose: ";
    public static void print(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX);
        sb.append(" ");
        sb.append(message);
        System.out.println(sb);
    }
}
