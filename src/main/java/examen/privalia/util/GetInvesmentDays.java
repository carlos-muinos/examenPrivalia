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

                    Calendar invesmentDate = checkLastMonthDay(startDate);
                    dates.add(formatter.format(invesmentDate.getTime()));
                }
                startDate.add(Calendar.DATE, 1);
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return (dates);
    }

    public static Calendar checkLastMonthDay(Calendar startDate){
        int currentDay = startDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        int finalMonthDay = startDate.get(Calendar.DAY_OF_MONTH);
        if(currentDay == finalMonthDay){
            startDate.add(Calendar.DATE, 1);
        }
        return (startDate);
    }
}
