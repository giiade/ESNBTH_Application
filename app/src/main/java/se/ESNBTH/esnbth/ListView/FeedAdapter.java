package se.ESNBTH.esnbth.ListView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import se.ESNBTH.esnbth.R;
import se.ESNBTH.esnbth.RequestHelper.Feed;

/**
 * Created by JulioLopez on 21/1/15.
 */
public class FeedAdapter extends BaseAdapter{

    //Variables
    private Activity activity;
    private LayoutInflater inflater;
    private List<Feed> feedItems;

    public FeedAdapter(Activity activity, List<Feed> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    public void swapItems(List<Feed> items) {
        this.feedItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int position) {
        return feedItems.get(position);
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
            convertView = inflater.inflate(R.layout.newslist_adapter, null);

        //Items that we need


        TextView title = (TextView) convertView.findViewById(R.id.newsTitleAdapter);
        TextView description = (TextView) convertView.findViewById(R.id.newsDescriptionAdapter);

        // getting event data for the row
        Feed f = feedItems.get(position);

        //Title
        title.setText(f.getTitle());

        //Descrition
        description.setText(f.getDescription());

        return convertView;
    }
}
