package se.ESNBTH.esnbth.ListView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import se.ESNBTH.esnbth.RequestHelper.AppController;
import se.ESNBTH.esnbth.RequestHelper.Event;

/**
 * Created by JulioLopez on 20/1/15.
 */
public class EventAdapter extends BaseAdapter {

    //Variables
    private Activity activity;
    private LayoutInflater inflater;
    private List<Event> eventItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

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
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
