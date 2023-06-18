package Brute;

import main.BruteUtilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BruteException extends Throwable {

    private int code;
    private String name;
    private String message;
    private String occurrence;

    private String PREFIX = "BruteException:";

    public BruteException(int code, String name, String message){
        this.code = code;
        this.name = name;
        this.message = GetFormattedExceptionMessage(message, false);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat(occurrenceFormat);
        return dateFormat.format(date);
    }

    public final void printStackTrace() {
       System.out.println(this.message);
    }

    private String GetFormattedExceptionMessage(String message, boolean inDetail) {
        StringBuilder sb = new StringBuilder();
        if (inDetail) {
            sb.append(GetOccurrence());
            sb.append(" ");
            sb.append(PREFIX);
            sb.append(" ");
            sb.append(message);
        } else {
            sb.append(PREFIX);
            sb.append(" ");
            sb.append(message);
        }
        return sb.toString();
    }
}
