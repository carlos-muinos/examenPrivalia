package examen.privalia.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class GetInvesmentDays {
    static final String stringStartDate = "23-05-2001";
    static final String stringEndDate = "28-12-2017";

    public static List getInvesmentDays() throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        List<String> dates = new ArrayList<>();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        try {
            startDate.setTime(formatter.parse(stringStartDate));
            endDate.setTime(formatter.parse(stringEndDate));
            while (endDate.after(startDate) || endDate.equals(startDate)) {
                if (startDate.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                    int month = startDate.get(Calendar.MONTH);
                    int year = startDate.get(Calendar.YEAR);
                    int day = startDate.get(Calendar.DAY_OF_MONTH);
                    int monthLastThursday = getMonthLastThursday(startDate, month, year);

                    if(day == monthLastThursday){
                        startDate.add(Calendar.DATE, 1);
                        dates.add(formatter.format(startDate.getTime()));
                    }
                }

                startDate.add(Calendar.DATE, 1);
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return (dates);
    }


    public static int getMonthLastThursday(Calendar startDate, int month, int year){
        Calendar cal = Calendar.getInstance();
        cal.set( year, month + 1, 1 );
        cal.add(Calendar.DAY_OF_MONTH, -( cal.get( Calendar.DAY_OF_WEEK ) % 7 + 2 ) );
        int finalMonthDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int monthLastThursday = cal.get(Calendar.DAY_OF_MONTH);
        //no calcula bien en algunos casos, así que hacemos este check
        if(finalMonthDay - monthLastThursday >= 7){
            monthLastThursday = finalMonthDay;
        }
        return (monthLastThursday);
    }
}
