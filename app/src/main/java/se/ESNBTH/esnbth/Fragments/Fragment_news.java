package se.ESNBTH.esnbth.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.colintmiller.simplenosql.DataComparator;
import com.colintmiller.simplenosql.NoSQL;
import com.colintmiller.simplenosql.NoSQLEntity;
import com.colintmiller.simplenosql.RetrievalCallback;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import se.ESNBTH.esnbth.ListView.FeedAdapter;
import se.ESNBTH.esnbth.R;
import se.ESNBTH.esnbth.RequestHelper.AppConst;
import se.ESNBTH.esnbth.RequestHelper.Event;
import se.ESNBTH.esnbth.RequestHelper.Feed;

public class Fragment_news extends Fragment {

    private final String TAG = Fragment_news.class.getSimpleName();

    private List<Feed> feeds = new ArrayList<>();
    private ListView feedList;
    private FeedAdapter adapter;

    public Fragment_news() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        feedList = (ListView) rootView.findViewById(R.id.newsList);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        adapter = new FeedAdapter(getActivity(), feeds);
        feedList.setAdapter(adapter);

        if (Session.getActiveSession().isOpened()) {
            Log.i(TAG, "I have a session");
            //requestFeed();
            TakeFromSql();
        } else {
            Log.i(TAG, "I don't have a session");
            Toast.makeText(getActivity().getApplicationContext(), "No Internet Conecction", Toast.LENGTH_SHORT).show();
        }
    }




    /**
     * all last 50 feeds from the @Url Facebook
     */
    private void requestFeed() {

        String requestTxt = AppConst.Facebook_PageName + "feed";
        Bundle bun = new Bundle();
        bun.putString("fields", "message,name");
        bun.putInt("limit", 50);
        new Request(Session.getActiveSession(), requestTxt, bun, null, new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                if (response != null) {
                    Log.i(TAG, response.toString());
                    GraphObject gEvent = response.getGraphObject();
                    JSONArray jFeedArray = (JSONArray) gEvent.getProperty(AppConst.DATA_KEY);
                    for (int i = 0; i < jFeedArray.length(); i++) {
                        Feed f = new Feed();
                        JSONObject item = new JSONObject();
                        try {
                            //we get feed items
                            item = (JSONObject) jFeedArray.get(i);
                            String msg = item.getString(AppConst.MSG_KEY);
                            if (!msg.equals("")) {
                                f.setTitle(item.getString(AppConst.NAME_KEY));
                                f.setDescription(msg);

                                //Add Feed item to list
                                feeds.add(f);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    adapter.swapItems(feeds);
                }

            }
        }).executeAsync();

    }

    private void TakeFromSql(){
        NoSQL.with(getActivity().getApplicationContext()).using(Feed.class).bucketId(AppConst.FEEDSQL_KEY)
                //Order the data by the date.
                .orderBy(new DataComparator<Feed>() {
                    @Override
                    public int compare(NoSQLEntity<Feed> lhs, NoSQLEntity<Feed> rhs) {
                        if (lhs != null && lhs.getData() != null) {
                            if (rhs != null && rhs.getData() != null) {
                                return AppConst.StrtoDateLite(rhs.getData().getCreatedAt())
                                        .compareTo(AppConst.StrtoDateLite(lhs.getData().getCreatedAt()));
                            } else {
                                return 1;
                            }
                        } else if (rhs != null && rhs.getData() != null) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                })
                        //take the data
                .retrieve(new RetrievalCallback<Feed>() {
                    @Override
                    public void retrievedResults(List<NoSQLEntity<Feed>> noSQLEntities) {
                        for (int i = 0; i < noSQLEntities.size(); i++) {
                            feeds.add(noSQLEntities.get(i).getData());
                        }
                        adapter.swapItems(feeds);
                    }
                });
    }


}
