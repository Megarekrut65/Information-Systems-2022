package ua.boa.smartlibrary;

import java.sql.Date;

public class Utilities {
    public static Date getDate(String date) {
        Date res = null;
        try {
            res = Date.valueOf(date);
        } catch (IllegalArgumentException ignored) {
        }
        return res;
    }

}
