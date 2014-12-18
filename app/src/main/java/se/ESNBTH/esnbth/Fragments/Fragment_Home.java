package se.ESNBTH.esnbth.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import se.ESNBTH.esnbth.Activities.Esn_Inf;
import se.ESNBTH.esnbth.Activities.Event_List;
import se.ESNBTH.esnbth.Activities.News_List;
import se.ESNBTH.esnbth.R;

public class Fragment_Home extends Fragment {

    private Button btnNews;
    private Button btnEvents;
    private Button btnAboutKarlskrona;
    private Button btnAboutUs;
    private Button btnVisitUs;

    private Fragment fragment;
    private FragmentManager fragmentManager;


    public Fragment_Home(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        btnNews = (Button) rootView.findViewById(R.id.btnNews);
        btnEvents = (Button) rootView.findViewById(R.id.btnEvents);
        btnAboutKarlskrona = (Button) rootView.findViewById(R.id.btnAboutKarlskrona);
        btnAboutUs = (Button) rootView.findViewById(R.id.btnAboutUs);
        btnVisitUs = (Button) rootView.findViewById(R.id.btnVisitUs);


        btnAboutKarlskrona.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View v, MotionEvent event){

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    fragmentManager = getFragmentManager();
                    fragment = new Fragment_Karlskrona();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                    return true;
                }
                return true;
            }
        });

        return rootView;
    }


    @Override
    public void onResume(){
        super.onResume();
    }

}
