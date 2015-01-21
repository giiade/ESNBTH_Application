package se.ESNBTH.esnbth.Fragments;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Random;
import java.util.concurrent.TimeoutException;

import se.ESNBTH.esnbth.R;
import se.ESNBTH.esnbth.RequestHelper.AppConst;
import se.ESNBTH.esnbth.RequestHelper.Event;

/**
 * Created by JulioLopez on 21/1/15.
 */
public class Fragment_SingleEvent extends Fragment {

    public Fragment_SingleEvent(){};

    private TextView titleTxt, descriptionTxt, descriptionTitle, timeTxt, placeTxt,timeTitle,placeTitle;
    private ImageView eventImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.single_event_layout, container, false);

        Event event = getArguments().getParcelable(AppConst.EVENT_KEY);

        Resources color = getResources();

        int[] colorIds = {R.color.ESNDarkBlue,R.color.blue, R.color.ESNGreen,R.color.ESNOrange,R.color.ESNPink};
        int colorPos = (int) (Math.random()*colorIds.length);


        //Retrieve all layout elements
        titleTxt = (TextView) rootView.findViewById(R.id.singleEName);
        descriptionTitle = (TextView) rootView.findViewById(R.id.eventDescriptionTitle);
        descriptionTxt = (TextView) rootView.findViewById(R.id.singleEventDescription);
        eventImage = (ImageView) rootView.findViewById(R.id.eventImg);
        timeTitle = (TextView) rootView.findViewById(R.id.eventTimeTitle);
        timeTxt = (TextView) rootView.findViewById(R.id.singleEventTime);
        placeTitle = (TextView) rootView.findViewById(R.id.eventPlaceTitle);
        placeTxt = (TextView) rootView.findViewById(R.id.singleEventPlace);

        //Pass data to the layout items.
        titleTxt.setText(event.getName());
        titleTxt.setTextColor(color.getColor(colorIds[colorPos]));
        descriptionTitle.setTextColor(color.getColor(colorIds[colorPos]));
        descriptionTxt.setText(event.getDescription());
        Picasso.with(rootView.getContext())
                .load(event.getImgUrl())
                        //.resize(100, 100)
                .fit()
                .into(eventImage);
        timeTitle.setTextColor(color.getColor(colorIds[colorPos]));
        timeTxt.setText(event.getStartTime());
        placeTitle.setTextColor(color.getColor(colorIds[colorPos]));
        placeTxt.setText(event.getLocation());



        descriptionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (descriptionTxt.isShown()) {
                    AppConst.slide_up(getActivity().getApplicationContext(), descriptionTxt);
                    descriptionTxt.setVisibility(View.GONE);
                } else {
                    AppConst.slide_down(getActivity().getApplicationContext(), descriptionTxt);
                    descriptionTxt.setVisibility(View.VISIBLE);
                }
            }
        });





        return rootView;





    }


}
