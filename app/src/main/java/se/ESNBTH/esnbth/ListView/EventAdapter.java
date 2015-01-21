package se.ESNBTH.esnbth.ListView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import se.ESNBTH.esnbth.R;
import se.ESNBTH.esnbth.RequestHelper.Event;


/**
 * Created by JulioLopez on 20/1/15.
 */
public class EventAdapter extends BaseAdapter {

    //Variables
    private Activity activity;
    private LayoutInflater inflater;
    private List<Event> eventItems;



    public EventAdapter(Activity activity, List<Event> eventItems) {
        this.activity = activity;
        this.eventItems = eventItems;
    }

    public void swapItems(List<Event> items) {
        this.eventItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return eventItems.size();
    }

    @Override
    public Object getItem(int position) {
        return eventItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.eventlist_adapter, null);


        ImageView thumbNail = (ImageView) convertView
                .findViewById(R.id.imgAdapter);
        TextView title = (TextView) convertView.findViewById(R.id.titleTxt_adapter);
        TextView day = (TextView) convertView.findViewById(R.id.dayText);
        TextView place = (TextView) convertView.findViewById(R.id.place_adapter);

        // getting event data for the row
        Event e = eventItems.get(position);

        // thumbnail image
        String imageUrl = e.getImgUrl();



        Picasso.with(convertView.getContext())
                .load(imageUrl)
                //.resize(100, 100)
                .fit()
                .into(thumbNail);
        //thumbNail.setImageUrl(imageUrl, imageLoader);


        // title
        title.setText(e.getName());

        //Day
        day.setText(e.getStartTime());

        //Place
        place.setText(e.getLocation());




        return convertView;
    }
}
