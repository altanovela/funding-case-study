package id.altanovela.funding.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtils {

    public static Date toDate(String pattern, String date) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            log.error("ERR", e);
        }
        return null;
    }
    
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
