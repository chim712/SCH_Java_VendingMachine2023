package AdminSys;

import java.util.Calendar;

public class Time {
    public static Calendar getToday(){
        Calendar tmpToday = Calendar.getInstance();
        tmpToday.add(Calendar.MONTH, 1);
        return tmpToday;
    }
    public static Calendar getYesterday(){
        Calendar yesterday = getToday();
        yesterday.add(Calendar.DAY_OF_MONTH,-1);
        return yesterday;
    }

    public static Calendar getLastMonth() {
        Calendar lastMonth = getToday();
        lastMonth.add(Calendar.MONTH,-1);
        lastMonth.set(Calendar.DAY_OF_MONTH,30);
        return lastMonth;
    }

    public static int getTodayYear(){
        return getToday().get(Calendar.YEAR);
    }
    public static int getTodayMonth(){
        return getToday().get(Calendar.MONTH);
    }
    public static int getTodayDate(){
        return getToday().get(Calendar.DAY_OF_MONTH);
    }

    public static String getTimeTitle(){
        String str = "  " + getToday().get(Calendar.YEAR) + " 년  " + (getToday().get(Calendar.MONTH)) + " 월  "
                + getToday().get(Calendar.DAY_OF_MONTH) + " 일";
        return str;
    }
}
