package gkfire.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
    private static Map<String,SimpleDateFormat> formats = new HashMap<>();

    public static Date parse(String format, String stringDate) throws ParseException {
        SimpleDateFormat sdf = formats.get(format);
        if(sdf == null){
            sdf = new SimpleDateFormat(format);
            formats.put(format,sdf);
        }
        return sdf.parse(stringDate);
    }
}
