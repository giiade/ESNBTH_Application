package se.ESNBTH.esnbth.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import se.ESNBTH.esnbth.R;

public class Fragment_Karlskrona extends Fragment {

    public Fragment_Karlskrona(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_karlskrona, container, false);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View v, MotionEvent event){

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
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
