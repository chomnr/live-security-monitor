package Brute;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BruteException extends Throwable {

    public static final String EXCEPTION_PREFIX = "[BRUTE]";

    private int code;
    private String name;
    private String message;
    private String occurrence;

    public BruteException(int code, String name, String message){
        this.code = code;
        this.name = name;
        this.message = GetFormattedExceptionMessage(message, true);
    }

    public final int GetCode(){
        return this.code;
    }

    public final String GetName(){
        return this.name;
    }

    public final String GetMessage() {
        return this.message;
    }

    public final String GetOccurrence(){
        Date date = new Date();
        String occurrenceFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(occurrenceFormat );
        return dateFormat.format(date);
    }

    private String GetFormattedExceptionMessage(String message, boolean inDetail) {
        StringBuilder sb = new StringBuilder();
        if (inDetail) {
            sb.append(EXCEPTION_PREFIX);
            sb.append(" ");
            sb.append(GetOccurrence());
            sb.append(" ");
            sb.append(message);
        } else {
            sb.append(EXCEPTION_PREFIX);
            sb.append(" ");
            sb.append(message);
        }
        return sb.toString();
    }
}
