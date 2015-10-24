package se.ESNBTH.esnbth.RequestHelper;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import se.ESNBTH.esnbth.R;

/**
 * Created by Julio on 08/01/2015.
 */
public class AppConst {

    public static final String Facebook_PageName = "EsnBth/";


    //KEY FOR SHARED PREFERENCES
    public static final String PREFERENCE_KEY = "prefkey";
    public static final String EVENT_KEY = "event";
    public static final String FEED_KEY = "feeds";

    //KEYS FOR EVENTS REQUEST
    public static final String ID_KEY = "id";
    public static final String NAME_KEY = "name";
    public static final String DESCRIPTION_KEY = "description";
    public static final String LOCATION_KEY = "location";
    public static final String START_TIME_KEY = "start_time";
    public static final String IMAGE_SOURCE_KEY = "source";

    //KEYS FOR FEED REQUEST
    public static final String DATA_KEY = "data";
    public static final String MSG_KEY = "message";
    public static final String CREATEDAT_KEY = "created_time";

    //For NoSql
    public static final String EVENTSQL_KEY = "events";
    public static final String FEVENTSQL_KEY = "fevents";
    public static final String FEEDSQL_KEY = "feed";




    //TODO: Parse ARRAYLIST to String

    //TODO: Parse String to ArrayList


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



    public static ArrayList<Event> mergeAllInfoEvents(List<Event> original, ArrayList<Event> events1){
        ArrayList<Event> result = new ArrayList<>();

        for(int i = 0; i < original.size();i++){
            Event event =  new Event(original.get(i));
           event = event.mergeInfoEvents(events1.get(i));
            result.add(event);
        }

        return result;
    }

    public static ArrayList<Event> mergeAllImgEvents(List<Event> original, ArrayList<Event> events1){
        ArrayList<Event> result = new ArrayList<>();

        for(int i = 0; i < original.size();i++){
            Event event =  new Event(original.get(i));
            event.setImgUrl(events1.get(i).getImgUrl());
            result.add(event);
        }

        return result;
    }

    public static void slide_down(Context context, View v) {
        Animation a = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    public static void slide_up(Context context, View v) {
        Animation a = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    public static Calendar StrtoDate (String dateFormat){
        String template = "yyyy-MM-dd'T'hh:mm:ss";
        DateFormat format = new SimpleDateFormat(template);
        Date date;
        try {
             date = format.parse(dateFormat);
        } catch (ParseException e) {
            throw new RuntimeException("Can't parse " + dateFormat + " as date");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal;
    }

    public static Calendar StrtoDateLite (String dateFormat){
        String template = "yyyy-MM-dd";
        DateFormat format = new SimpleDateFormat(template);
        Date date;
        try {
            date = format.parse(dateFormat);
        } catch (ParseException e) {
            throw new RuntimeException("Can't parse " + dateFormat + " as date");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal;
    }

    public static String GetDate (Calendar cal){
        String result = "";
        DateFormat format = new SimpleDateFormat("EEEE' 'd' 'MMMM");
        result = format.format(cal.getTime());
        return result;
    }

    public static String GetTime (Calendar cal){
        String result = "";
        DateFormat format = new SimpleDateFormat("hh:mm' 'aa");
        result = format.format(cal.getTime());
        return result;
    }
}
