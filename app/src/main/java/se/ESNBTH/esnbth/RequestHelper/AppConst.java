package se.ESNBTH.esnbth.RequestHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Julio on 08/01/2015.
 */
public class AppConst {

    public static final String Facebook_PageName = "EsnBth/";



    //KEYS FOR EVENTS REQUEST
    public static final String ID_KEY = "id";
    public static final String NAME_KEY = "name";
    public static final String DESCRIPTION_KEY = "description";
    public static final String LOCATION_KEY = "location";
    public static final String START_TIME_KEY = "start_time";

    /**
     * Will give us the equivalent to srtToTime for using it in the request.
     * @return strToTime equivalent for one year ago.
     */
    public static String getSinceTime(){
        Date date = new Date();
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int year = Calendar.getInstance().get(Calendar.YEAR)-1;
            int day = Calendar.getInstance().get(Calendar.DATE);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            date = sdf.parse(year + "-" + month + "-" + day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        result = String.valueOf(date.getTime()).substring(0,10);
        return result;
    }
}
