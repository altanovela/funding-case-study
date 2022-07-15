package id.altanovela.funding.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date bod(Date date) {
        return ddt(date, 00, 00, 01);
    }
    
    public static Date eod(Date date) {
        return ddt(date, 23, 59, 59);
    }
    
    private static Date ddt(Date date, int hour, int minute, int second) {
        if(null == date) {
            return date;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY , hour);
        cal.set(Calendar.MINUTE      , minute);
        cal.set(Calendar.SECOND      , second);
        return cal.getTime();
    }
}
