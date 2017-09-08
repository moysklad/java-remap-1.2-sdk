package com.lognex.api.util;

import com.lognex.api.exception.ConverterException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    /*TODO поддержать обработку форматов с миллисекундами и без*/
    public static Date parseDate(String dateString) throws ConverterException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            throw new ConverterException(e);
        }
    }
}
