package com.lognex.api.util;

import com.lognex.api.exception.ConverterException;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class DateUtils {
    /*TODO поддержать обработку форматов с миллисекундами и без*/
    public static Date parseDate(String dateString) throws ConverterException {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            throw new ConverterException(e);
        }
    }
}
