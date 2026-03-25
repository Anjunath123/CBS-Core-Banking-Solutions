package com.canfin.corebanking.customerservice.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class OmniUtils {



    public static int diffInMonths(final Date fromDate, final Date toDate) {
        //
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(fromDate);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(toDate);

        int diffYear = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);
        return diffMonth;
    }
}
