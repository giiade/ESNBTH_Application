package se.ESNBTH.esnbth.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import se.ESNBTH.esnbth.R;

public class Fragment_Karlskrona extends Fragment {

    private Button btnTimetable;

    private Fragment fragment;
    private FragmentManager fragmentManager;

    public Fragment_Karlskrona(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_karlskrona, container, false);

        //UPDATE LIST VIEW
        Fragment_Timetables.selection = 0;

        btnTimetable = (Button) rootView.findViewById(R.id.btnTimetable);

        btnTimetable.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View v, MotionEvent event){

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    fragmentManager = getFragmentManager();
                    fragment = new Fragment_Timetables();
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
