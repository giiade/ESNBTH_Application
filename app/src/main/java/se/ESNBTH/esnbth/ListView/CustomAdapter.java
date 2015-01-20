package se.ESNBTH.esnbth.ListView;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
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

    private Context context;
    private List<RowItem> rowItems;
    private ViewHolder holder = null;
    private Calendar calendar;
    private int day;
    private int hour;
    private int minute;

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
        TextView openCloseTv;
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
            holder.openCloseTv = (TextView) convertView.findViewById(R.id.openClosed);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RowItem row_pos = rowItems.get(pos);
        if(pos == 0) Picasso.with(context).load(R.drawable.cityy).fit().centerInside().into(holder.shop_pic);
        if(pos == 1) Picasso.with(context).load(R.drawable.elgiganten).fit().centerInside().into(holder.shop_pic);
        if(pos == 2) Picasso.with(context).load(R.drawable.ica).fit().centerInside().into(holder.shop_pic);
        if(pos == 3) Picasso.with(context).load(R.drawable.hemkop).fit().centerInside().into(holder.shop_pic);
        if(pos == 4) Picasso.with(context).load(R.drawable.lidl).fit().centerInside().into(holder.shop_pic);
        if(pos == 5) Picasso.with(context).load(R.drawable.maxburger).fit().centerInside().into(holder.shop_pic);
        if(pos == 6) Picasso.with(context).load(R.drawable.mcdo).fit().centerInside().into(holder.shop_pic);
        if(pos == 7) Picasso.with(context).load(R.drawable.secondhand).fit().centerInside().into(holder.shop_pic);
        if(pos == 8) Picasso.with(context).load(R.drawable.systembo1).fit().centerInside().into(holder.shop_pic);
        if(pos == 9) Picasso.with(context).load(R.drawable.systembo1).fit().centerInside().into(holder.shop_pic);
        if(pos == 10) Picasso.with(context).load(R.drawable.willys1).fit().centerInside().into(holder.shop_pic);
        if(pos == 11) Picasso.with(context).load(R.drawable.willys1).fit().centerInside().into(holder.shop_pic);

        holder.shop_name.setText(row_pos.getShop_name());
        holder.localisation.setText(row_pos.getLocalisation());

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_WEEK);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);


        //CITY GROSS
        if(pos == 0){
            if((hour >= 7 && minute >= 0)  && (hour <= 20 || (hour == 21 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }
            else {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }
        }

        //ELGIGANTEN
        if(pos == 1){

            if((day == 2 || day == 3 || day == 4 || day == 5 || day == 6) && (hour >= 10 && minute >= 0)  && (hour <= 18 || (hour == 19 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 7) && (hour >= 10 && minute >= 0)  && (hour <= 15 || (hour == 16 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 1) && (hour >= 11 && minute >= 0)  && (hour <= 15 || (hour == 16 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }

        }

        //ICA
        if(pos == 2){

            if((hour >= 7 && minute >= 0)  && (hour <= 22 || (hour == 23 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }
            else {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }

        }

        //HEMKOP
        if(pos == 3){

            if((hour >= 7 && minute >= 0)  && (hour <= 21 || (hour == 22 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }
            else {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }

        }

        //LIDL
        if(pos == 4){

            if((day == 2 || day == 3 || day == 4 || day == 5 || day == 6) && (hour >= 9 && minute >= 0)  && (hour <= 19 || (hour == 20 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 7) && (hour >= 9 && minute >= 0)  && (hour <= 17 || (hour == 18 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 1) && (hour >= 10 && minute >= 0)  && (hour <= 17 || (hour == 18 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }

        }

        // MAX BURGER
        if(pos == 5){

            if((day == 2 || day == 3 || day == 4 || day == 5) && (hour >= 10 && minute >= 0)  && (hour <= 22 || (hour == 23 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 6 || day == 7) && ((hour >= 10 && minute >= 0) || (hour <= 0 || (hour == 1 && minute == 0)))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 1) && (hour >= 10 && minute >= 0)  && (hour <= 22 || (hour == 23 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }
        }

        // MC DONALDS
        if(pos == 6){

            if((day == 2 || day == 3 || day == 4 || day == 5 || day == 6 || day == 7) && (hour >= 10 && minute >= 0)  && (hour <= 20 || (hour == 21 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 1) && (hour >= 11 && minute >= 0)  && (hour <= 17 || (hour == 18 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }

        }

        // SECOND HAND
        if(pos == 7){

            if((day == 2) && (hour >= 12 && minute >= 0)  && (hour <= 16 || (hour == 17 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 3) && (hour >= 17 && minute >= 0)  && (hour <= 17 || (hour == 18 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 7) && ((hour >= 9 && minute >= 30) || hour >= 10) && (hour <= 12 || (hour <= 13 && minute <= 30))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if(day == 4 || day == 5 || day == 6 || day == 1) {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }

            else {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }

        }

        // SYSTEMBOLAGET 1
        if(pos == 8){

            if((day == 2 || day == 3 || day == 4) && (hour >= 10 && minute >= 0)  && (hour <= 17 || (hour == 18 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 5) && (hour >= 10 && minute >= 0)  && (hour <= 18 || (hour == 19 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 6) && (hour >= 10 && minute >= 0)  && (hour <= 17 || (hour == 18 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 7) && (hour >= 10 && minute >= 0)  && (hour <= 13 || (hour == 14 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if(day == 1) {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }

            else {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }
        }

        // SYSTEMBOLAGET 2
        if(pos == 9){

            if((day == 2 || day == 3 || day == 4) && (hour >= 10 && minute >= 0)  && (hour <= 17 || (hour == 18 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 5 || day == 6) && (hour >= 10 && minute >= 0)  && (hour <= 18 || (hour == 19 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 7) && (hour >= 10 && minute >= 0)  && (hour <= 14 || (hour == 15 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if(day == 1) {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }

            else {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }

        }

        //WILLYS 1
        if(pos == 10){

            if((hour >= 8 && minute >= 0)  && (hour <= 20 || (hour == 21 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }
            else {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }
        }

        //WILLYS 2
        if(pos == 11){

            if((day == 2 || day == 3 || day == 4 || day == 5 || day == 6 || day == 7) && (hour >= 7 && minute >= 0)  && (hour <= 20 || (hour == 21 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else if((day == 1) && (hour >= 8 && minute >= 0)  && (hour <= 20 || (hour == 21 && minute == 0))) {
                holder.openCloseTv.setTextColor(Color.parseColor("#7CAF00"));
                holder.openCloseTv.setText("Open");
            }

            else {
                holder.openCloseTv.setTextColor(Color.RED);
                holder.openCloseTv.setText("Closed");
            }
        }

        return convertView;
    }

}