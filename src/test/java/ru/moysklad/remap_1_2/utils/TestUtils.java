package ru.moysklad.remap_1_2.utils;

import org.apache.commons.io.IOUtils;
import ru.moysklad.remap_1_2.schema.SchemaMapper;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Objects;

public interface TestUtils {

    static String getFile(String fileName){
        String result;
        ClassLoader classLoader = SchemaMapper.class.getClassLoader();
        try {
            result = IOUtils.toString(Objects.requireNonNull(classLoader.getResourceAsStream(fileName)), Charset.forName("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException("Unable to get file " + fileName, e);
        }

        return result;
    }

    static DecimalFormat getDoubleFormatWithFractionDigits(int fractionDigits) {
        Locale fmtLocale = Locale.ENGLISH;
        DecimalFormat formatter = (DecimalFormat) DecimalFormat.getInstance(fmtLocale);
        formatter.setMaximumFractionDigits(fractionDigits);
        formatter.setMinimumFractionDigits(1);
        formatter.setDecimalSeparatorAlwaysShown(true);
        return formatter;
    }
}
