package se.ESNBTH.esnbth.Fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import se.ESNBTH.esnbth.Activities.MainLayActivity;
import se.ESNBTH.esnbth.ListView.CustomAdapter;
import se.ESNBTH.esnbth.ListView.RowItem;
import se.ESNBTH.esnbth.R;

public class Fragment_Timetables extends Fragment implements AdapterView.OnItemClickListener {

    String[] shop_names;
    TypedArray shop_pics;
    String[] localisation;
    String[] openClosed;
    TextView openClosedText;
    public static int selection;

    List<RowItem> rowItems;
    public static ListView mylistview;
    CustomAdapter adapter;

    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_timetables, container, false);

        rowItems = new ArrayList<RowItem>();

        shop_names = getResources().getStringArray(R.array.ShopsName);

        shop_pics = getResources().obtainTypedArray(R.array.ShopsPicture);

        localisation = getResources().getStringArray(R.array.ShopsLocalisation);

        openClosed = getResources().getStringArray(R.array.OpenClosed);

        openClosedText = (TextView) rootView.findViewById(R.id.openClosed);

        ((MainLayActivity)getActivity()).setNavigationDrawer(4);


        for (int i = 0; i < shop_names.length; i++) {
            RowItem item = new RowItem(shop_names[i],
                    shop_pics.getResourceId(i, -1),
                    localisation[i],
                    openClosed[i]);
            rowItems.add(item);

        }

        mylistview = (ListView) rootView.findViewById(R.id.list);
        adapter = new CustomAdapter(getActivity().getApplicationContext(), rowItems);

        mylistview.setAdapter(adapter);
        mylistview.setSelection(selection);
        shop_pics.recycle();
        mylistview.setOnItemClickListener(this);


        MainLayActivity.previousFragment = 1;

        return rootView;
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        fragmentManager = getFragmentManager();
        fragment = Fragment_DetailTimetable.newInstance(position, rowItems.get(position).getLocalisation(), rowItems.get(position).getShop_pic_id());
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

}
