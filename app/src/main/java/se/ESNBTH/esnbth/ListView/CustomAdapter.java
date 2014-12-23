package se.ESNBTH.esnbth.ListView;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import se.ESNBTH.esnbth.R;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItems;

    public CustomAdapter(Context context, List<RowItem> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView shop_pic;
        TextView shop_name;
        TextView localisation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.shop_name = (TextView) convertView
                    .findViewById(R.id.shop_name);
            holder.shop_pic = (ImageView) convertView
                    .findViewById(R.id.shop_pic);
            holder.localisation = (TextView) convertView.findViewById(R.id.localisation);

            RowItem row_pos = rowItems.get(position);

            holder.shop_pic.setImageResource(row_pos.getShop_pic_id());
            holder.shop_name.setText(row_pos.getShop_name());
            holder.localisation.setText(row_pos.getLocalisation());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

}