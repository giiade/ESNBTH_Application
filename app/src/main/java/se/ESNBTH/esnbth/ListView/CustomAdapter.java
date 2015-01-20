package se.ESNBTH.esnbth.ListView;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import se.ESNBTH.esnbth.R;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItems;
    ViewHolder holder = null;

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
    static class ViewHolder {
        ImageView shop_pic;
        TextView shop_name;
        TextView localisation;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {



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

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RowItem row_pos = rowItems.get(pos);
        if(pos == 0) Picasso.with(context).load(R.drawable.cityy).fit().into(holder.shop_pic);
        if(pos == 1) Picasso.with(context).load(R.drawable.elgiganten).fit().into(holder.shop_pic);
        if(pos == 2) Picasso.with(context).load(R.drawable.ica).fit().into(holder.shop_pic);
        if(pos == 3) Picasso.with(context).load(R.drawable.hemkop).fit().into(holder.shop_pic);
        if(pos == 4) Picasso.with(context).load(R.drawable.lidl).fit().into(holder.shop_pic);
        if(pos == 5) Picasso.with(context).load(R.drawable.maxburger).fit().into(holder.shop_pic);
        if(pos == 6) Picasso.with(context).load(R.drawable.mcdo).fit().into(holder.shop_pic);
        if(pos == 7) Picasso.with(context).load(R.drawable.secondhand).fit().into(holder.shop_pic);
        if(pos == 8) Picasso.with(context).load(R.drawable.systembo1).fit().into(holder.shop_pic);
        if(pos == 9) Picasso.with(context).load(R.drawable.systembo1).fit().into(holder.shop_pic);
        if(pos == 10) Picasso.with(context).load(R.drawable.willys1).fit().into(holder.shop_pic);
        if(pos == 11) Picasso.with(context).load(R.drawable.willys1).fit().into(holder.shop_pic);

        holder.shop_name.setText(row_pos.getShop_name());
        holder.localisation.setText(row_pos.getLocalisation());



        return convertView;
    }

}