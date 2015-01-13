package se.ESNBTH.esnbth.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import se.ESNBTH.esnbth.Activities.MainLayActivity;
import se.ESNBTH.esnbth.R;

public class Fragment_DetailTimetable extends Fragment {

    private static int Image;
    private static String Localisation;

    private TextView detailName;
    private ImageView detailPic;
    private TextView detailLocalisation;
    private TextView monday;
    private TextView tuesday;
    private TextView wednesday;
    private TextView thursday;
    private TextView friday;
    private TextView saturday;
    private TextView sunday;
    private TextView mondayTime;
    private TextView tuesdayTime;
    private TextView wednesdayTime;
    private TextView thursdayTime;
    private TextView fridayTime;
    private TextView saturdayTime;
    private TextView sundayTime;
    private Button map;

    private String mapPos;

    public Fragment_DetailTimetable(){}

    public static Fragment newInstance(int position, String shopLocalisation, int shopPic) {
        Fragment myFragment = new Fragment_DetailTimetable();

        Bundle args = new Bundle();
        args.putInt("pos", position);
        myFragment.setArguments(args);

        Image = shopPic;
        Localisation = shopLocalisation;

        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail_timetable, container, false);

        detailLocalisation = (TextView) rootView.findViewById(R.id.detailLocalisation);
        monday = (TextView) rootView.findViewById(R.id.monday);
        tuesday = (TextView) rootView.findViewById(R.id.tuesday);
        wednesday = (TextView) rootView.findViewById(R.id.wednesday);
        thursday = (TextView) rootView.findViewById(R.id.thursday);
        friday = (TextView) rootView.findViewById(R.id.friday);
        saturday = (TextView) rootView.findViewById(R.id.saturday);
        sunday = (TextView) rootView.findViewById(R.id.sunday);
        mondayTime = (TextView) rootView.findViewById(R.id.mondayTime);
        tuesdayTime = (TextView) rootView.findViewById(R.id.tuesdayTime);
        wednesdayTime = (TextView) rootView.findViewById(R.id.wednesdayTime);
        thursdayTime = (TextView) rootView.findViewById(R.id.thursdayTime);
        fridayTime = (TextView) rootView.findViewById(R.id.fridayTime);
        saturdayTime = (TextView) rootView.findViewById(R.id.saturdayTime);
        sundayTime = (TextView) rootView.findViewById(R.id.sundayTime);
        detailPic = (ImageView) rootView.findViewById(R.id.detailPic);
        map = (Button) rootView.findViewById(R.id.btnMap);
        map.setBackgroundResource(R.drawable.mapslogo1);

        detailLocalisation.setText(Localisation);
        detailPic.setImageResource(Image);

        // COLOR CURRENT DAY
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        if(day == 1){
            sunday.setTextColor(Color.parseColor("#16A5D7"));
            sundayTime.setTextColor(Color.parseColor("#16A5D7"));
        }

        if(day == 2){
            monday.setTextColor(Color.parseColor("#16A5D7"));
            mondayTime.setTextColor(Color.parseColor("#16A5D7"));
        }

        if(day == 3){
            tuesday.setTextColor(Color.parseColor("#16A5D7"));
            tuesdayTime.setTextColor(Color.parseColor("#16A5D7"));
        }

        if(day == 4){
            wednesday.setTextColor(Color.parseColor("#16A5D7"));
            wednesdayTime.setTextColor(Color.parseColor("#16A5D7"));
        }

        if(day == 5){
            thursday.setTextColor(Color.parseColor("#16A5D7"));
            thursdayTime.setTextColor(Color.parseColor("#16A5D7"));
        }

        if(day == 6){
            friday.setTextColor(Color.parseColor("#16A5D7"));
            fridayTime.setTextColor(Color.parseColor("#16A5D7"));
        }

        if(day == 7){
            saturday.setTextColor(Color.parseColor("#16A5D7"));
            saturdayTime.setTextColor(Color.parseColor("#16A5D7"));
        }

        // BIND POSITION

        //CITY GROSS
        if(getArguments().getInt("pos", 0) == 0){
            mondayTime.setText("7:00 AM - 9:00 PM");
            tuesdayTime.setText("7:00 AM - 9:00 PM");
            wednesdayTime.setText("7:00 AM - 9:00 PM");
            thursdayTime.setText("7:00 AM - 9:00 PM");
            fridayTime.setText("7:00 AM - 9:00 PM");
            saturdayTime.setText("7:00 AM - 9:00 PM");
            sundayTime.setText("7:00 AM - 9:00 PM");
            mapPos = "56.196871,15.612925";
        }

        //ELGIGANTEN
        if(getArguments().getInt("pos", 0) == 1){
            mondayTime.setText("10:00 AM - 7:00 PM");
            tuesdayTime.setText("10:00 AM - 7:00 PM");
            wednesdayTime.setText("10:00 AM - 7:00 PM");
            thursdayTime.setText("10:00 AM - 7:00 PM");
            fridayTime.setText("10:00 AM - 7:00 PM");
            saturdayTime.setText("10:00 AM - 4:00 PM");
            sundayTime.setText("11:00 AM - 4:00 PM");
            mapPos = "56.196680,15.647078";
        }

        //ICA
        if(getArguments().getInt("pos", 0) == 2){
            mondayTime.setText("7:00 AM - 11:00 PM");
            tuesdayTime.setText("7:00 AM - 11:00 PM");
            wednesdayTime.setText("7:00 AM - 11:00 PM");
            thursdayTime.setText("7:00 AM - 11:00 PM");
            fridayTime.setText("7:00 AM - 11:00 PM");
            saturdayTime.setText("7:00 AM - 11:00 PM");
            sundayTime.setText("7:00 AM - 11:00 PM");
            mapPos = "56.161019,15.583642";
        }

        //HEMKOP
        if(getArguments().getInt("pos", 0) == 3){
            mondayTime.setText("7:00 AM - 10:00 PM");
            tuesdayTime.setText("7:00 AM - 10:00 PM");
            wednesdayTime.setText("7:00 AM - 10:00 PM");
            thursdayTime.setText("7:00 AM - 10:00 PM");
            fridayTime.setText("7:00 AM - 10:00 PM");
            saturdayTime.setText("7:00 AM - 10:00 PM");
            sundayTime.setText("7:00 AM - 10:00 PM");
            mapPos = "56.162210,15.588136";
        }

        //LIDL
        if(getArguments().getInt("pos", 0) == 4){
            mondayTime.setText("9:00 AM - 8:00 PM");
            tuesdayTime.setText("9:00 AM - 8:00 PM");
            wednesdayTime.setText("9:00 AM - 8:00 PM");
            thursdayTime.setText("9:00 AM - 8:00 PM");
            fridayTime.setText("9:00 AM - 8:00 PM");
            saturdayTime.setText("9:00 AM - 6:00 PM");
            sundayTime.setText("10:00 AM - 6:00 PM");
            mapPos = "56.169469,15.585402";
        }

        // MAX BURGER
        if(getArguments().getInt("pos", 0) == 5){
            mondayTime.setText("10:00 AM - 11:00 PM");
            tuesdayTime.setText("10:00 AM - 11:00 PM");
            wednesdayTime.setText("10:00 AM - 11:00 PM");
            thursdayTime.setText("10:00 AM - 11:00 PM");
            fridayTime.setText("10:00 AM - 1:00 AM");
            saturdayTime.setText("10:00 AM - 1:00 AM");
            sundayTime.setText("10:00 AM - 11:00 PM");
            mapPos = "56.195430,15.632279";
        }

        // MC DONALDS
        if(getArguments().getInt("pos", 0) == 6){
            mondayTime.setText("10:00 AM - 9:00 PM");
            tuesdayTime.setText("10:00 AM - 9:00 PM");
            wednesdayTime.setText("10:00 AM - 9:00 PM");
            thursdayTime.setText("10:00 AM - 9:00 PM");
            fridayTime.setText("10:00 AM - 9:00 PM");
            saturdayTime.setText("10:00 AM - 9:00 PM");
            sundayTime.setText("11:00 AM - 6:00 PM");
            mapPos = "56.162660,15.585068";
        }

        // SECOND HAND
        if(getArguments().getInt("pos", 0) == 7){
            mondayTime.setText("12:00 PM - 5:00 PM");
            tuesdayTime.setText("5:00 PM - 6:00 PM");
            wednesdayTime.setText("Closed");
            thursdayTime.setText("Closed");
            fridayTime.setText("Closed");
            saturdayTime.setText("9:30 AM - 1:30PM");
            sundayTime.setText("Closed");
            mapPos = "56.181298, 15.598750";
        }

        // SYSTEMBOLAGET 1
        if(getArguments().getInt("pos", 0) == 8){
            mondayTime.setText("10:00 AM - 6:00 PM");
            tuesdayTime.setText("10:00 AM - 6:00 PM");
            wednesdayTime.setText("10:00 AM - 6:00 PM");
            thursdayTime.setText("10:00 AM - 7:00 PM");
            fridayTime.setText("10:00 AM - 6:00 PM");
            saturdayTime.setText("10:00 AM - 2:00 PM");
            sundayTime.setText("Closed");
            mapPos = "56.161212,15.582255";
        }

        // SYSTEMBOLAGET 2
        if(getArguments().getInt("pos", 0) == 9){
            mondayTime.setText("10:00 AM - 6:00 PM");
            tuesdayTime.setText("10:00 AM - 6:00 PM");
            wednesdayTime.setText("10:00 AM - 6:00 PM");
            thursdayTime.setText("10:00 AM - 7:00 PM");
            fridayTime.setText("10:00 AM - 7:00 PM");
            saturdayTime.setText("10:00 AM - 3:00 PM");
            sundayTime.setText("Closed");
            mapPos = "56.197009,15.641754";
        }

        //WILLYS 1
        if(getArguments().getInt("pos", 0) == 10){
            mondayTime.setText("8:00 AM - 9:00 PM");
            tuesdayTime.setText("8:00 AM - 9:00 PM");
            wednesdayTime.setText("8:00 AM - 9:00 PM");
            thursdayTime.setText("8:00 AM - 9:00 PM");
            fridayTime.setText("8:00 AM - 9:00 PM");
            saturdayTime.setText("8:00 AM - 9:00 PM");
            sundayTime.setText("8:00 AM - 9:00 PM");
            mapPos = "56.173776,15.588205";
        }

        //WILLYS 2
        if(getArguments().getInt("pos", 0) == 11){
            mondayTime.setText("7:00 AM - 9:00 PM");
            tuesdayTime.setText("7:00 AM - 9:00 PM");
            wednesdayTime.setText("7:00 AM - 9:00 PM");
            thursdayTime.setText("7:00 AM - 9:00 PM");
            fridayTime.setText("7:00 AM - 9:00 PM");
            saturdayTime.setText("7:00 AM - 9:00 PM");
            sundayTime.setText("8:00 AM - 9:00 PM");
            mapPos = "56.196961,15.649499";
        }




        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View v, MotionEvent event){

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    try {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?daddr=" + mapPos + "\""));
                        startActivity(intent);
                    } catch(Exception e) {
                        System.out.println(e);
                    }
                    return true;
                }
                return true;
            }
        });

        MainLayActivity.previousFragment = 2;

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

}
