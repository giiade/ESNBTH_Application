package se.ESNBTH.esnbth.Fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.colintmiller.simplenosql.DataComparator;
import com.colintmiller.simplenosql.NoSQL;
import com.colintmiller.simplenosql.NoSQLEntity;
import com.colintmiller.simplenosql.RetrievalCallback;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import se.ESNBTH.esnbth.ListView.EventAdapter;
import se.ESNBTH.esnbth.R;
import se.ESNBTH.esnbth.RequestHelper.AppConst;
import se.ESNBTH.esnbth.RequestHelper.Event;

public class Fragment_Events extends Fragment {

    private final String TAG = Fragment_Events.class.getSimpleName();

    private List<Event> events = new ArrayList<>(); //Here we will save events.
    private ArrayList<Event> imgEvents = new ArrayList<>();
    private ArrayList<Event> infoEvents = new ArrayList<>();

    private EventAdapter eventAdapter;
    private ListView eventList;



    public Fragment_Events() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        eventList = (ListView) rootView.findViewById(R.id.eventsList);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //LIST

        eventAdapter = new EventAdapter(getActivity(), events);
        eventList.setAdapter(eventAdapter);

        if (Session.getActiveSession().isOpened()) {
            Log.i(TAG, "I have a session");
            //requestAllEvents();
            //NoSQL.with(getActivity().getApplicationContext()).using(Event.class).bucketId(AppConst.FEVENTSQL_KEY).delete();
            TakeFromSql();
        } else {
            Log.i(TAG, "I don't have a session");
            Toast.makeText(getActivity().getApplicationContext(), "No Internet Conecction", Toast.LENGTH_SHORT).show();
        }

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //We get the event by position.
                Event item = events.get(position);
                //Create the fragment
                Fragment_SingleEvent newFragment = new Fragment_SingleEvent();
                //Put the data into a bundle to send it to the fragment
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConst.EVENT_KEY,item);
                newFragment.setArguments(bundle);
                //Change the fragment
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container,newFragment);
                transaction.commit();

            }
        });
    }

    /**
     * Update the event array with last 100 events.
     */
    private void requestAllEvents(){
        String EVENTS = "events";
        Bundle bun = new Bundle();
        //bun.putString("fields","id,name,description,location,start_time");
        bun.putString("fields","id");
        bun.putString("since",AppConst.getSinceTime());
        bun.putInt("limit",100);
        //PAGEID/Events
        String requestStuff = AppConst.Facebook_PageName+EVENTS;
        new Request(Session.getActiveSession(),requestStuff,bun, HttpMethod.GET, new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                Log.i(TAG, response.toString());
                events = new ArrayList<>();
                GraphObject jRequest = response.getGraphObject();
                JSONArray jEvents = (JSONArray) jRequest.getProperty("data");
                if (jEvents != null) {
                    for (int i = 0; i < jEvents.length(); i++) {
                        JSONObject item = null;
                        try {
                            item = (JSONObject) jEvents.get(i);
                            Event event = new Event();
                            event.setId(item.getString(AppConst.ID_KEY));
                            //event.setName(item.getString(AppConst.NAME_KEY));
                            //event.setDescription(item.getString(AppConst.DESCRIPTION_KEY));
                            //event.setLocation(item.getString(AppConst.DESCRIPTION_KEY));
                            //event.setStartTime(item.getString(AppConst.START_TIME_KEY));

                            String message = (String) item.get("id");
                            Log.i(TAG + ".ITEM " + i, message);
                            events.add(event);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                //Get the data of the events
                //batchRequestImages(events);
                //Get the Images of the events
                batchRequestEvents(events);


            }
        }).executeAsync();

    }


    /**
     * Called internally by requestAllEvents it fetch data from the event as
     * Id, name,Description,location and start time
     * @param eventsArray Contains an array of Events with their IDs
     */
    private void batchRequestEvents(List<Event> eventsArray){
        final Session session = Session.getActiveSession();

        //clear the infoEvents
        infoEvents.clear();


        if(session != null && session.isOpened()){
            //Create a requestBatch
            RequestBatch requestBatch = new RequestBatch();


            //Request the information of the events and save it in @infoEvents
            for( int i = 0; i<eventsArray.size();i++){

                //Get id of the current event
                String requestID = eventsArray.get(i).getId();

                //Create the request to the requestID
                Bundle bun = new Bundle();
                bun.putString("fields","id,name,description,location,start_time");
                Request request = new Request(session,requestID,bun,null,new Request.Callback() {
                    @Override
                    public void onCompleted(Response response) {
                        // Log.i(TAG,response.toString());
                        //Test if we don't have an empty response
                        if(response != null) {
                            GraphObject graphObject = response.getGraphObject();
                            Event item = new Event();
                            item.setId((String) graphObject.getProperty(AppConst.ID_KEY));
                            item.setName((String) graphObject.getProperty(AppConst.NAME_KEY));
                            item.setDescription((String) graphObject.getProperty(AppConst.DESCRIPTION_KEY));
                            item.setLocation((String) graphObject.getProperty(AppConst.LOCATION_KEY));
                            item.setStartTime((String) graphObject.getProperty(AppConst.START_TIME_KEY));

                            //add the item to events
                            infoEvents.add(item);
                        }
                    }
                });



                requestBatch.add(request);

            }


            requestBatch.addCallback(new RequestBatch.Callback() {
                @Override
                public void onBatchCompleted(RequestBatch batch) {
                    events = AppConst.mergeAllInfoEvents(events,infoEvents);
                    Log.i("HOLA", events.get(0).getDescription());
                    batchRequestImages(events);


                }
            });

            requestBatch.executeAsync();


        }

    }

    /**
     * Called internally by requestAllEvents it fetch the image
     * that the event will use.
     * @param eventsArray Contains an array of Events with their IDs
     */
    private void batchRequestImages(List<Event> eventsArray){
        final Session session = Session.getActiveSession();
        //Clear the imgEvents
        imgEvents.clear();

        if(session != null && session.isOpened()) {
            //Create a requestBatch
            RequestBatch requestBatch = new RequestBatch();

            //Request the information of the events and save it in @infoEvents
            for (int i = 0; i < eventsArray.size(); i++) {

                //Get de id of the current event
                String requestID = eventsArray.get(i).getId();
                // Log.i(TAG, requestID);
                Bundle imageBun = new Bundle();
                imageBun.putString("fields", "images");
                requestBatch.add(new Request(session, requestID + "/photos", imageBun, null, new Request.Callback() {
                    @Override
                    public void onCompleted(Response response) {
                        //Test if we don't have an empty response
                        if (response != null) {
                            //Get the graphObject
                            GraphObject graphObject = response.getGraphObject();
                            //Get the data from the graphObject
                            JSONArray jsonArray = (JSONArray) graphObject.getProperty("data");
                            try {
                                //Get the first JsonObject of data that is an JSONArray
                                JSONObject imgArray = (JSONObject) jsonArray.get(0);
                                jsonArray = (JSONArray) imgArray.get("images");
                                //Get the first JsonObject of images.
                                JSONObject imgItem = (JSONObject) jsonArray.get(0);
                                Event item = new Event();
                                item.setImgUrl(imgItem.getString(AppConst.IMAGE_SOURCE_KEY));
                                imgEvents.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }));
            }
            requestBatch.addCallback(new RequestBatch.Callback() {
                @Override
                public void onBatchCompleted(RequestBatch batch) {
                    events = AppConst.mergeAllImgEvents(events,imgEvents);

                    Log.i("IMAGE", events.get(0).getImgUrl());
                    eventAdapter.swapItems(events);
                }
            });

            requestBatch.executeAsync();
        }
    }

    private void TakeFromSql(){
        NoSQL.with(getActivity().getApplicationContext()).using(Event.class).bucketId(AppConst.EVENTSQL_KEY)
                //Order the data by the date.
                .orderBy(new DataComparator<Event>() {
                    @Override
                    public int compare(NoSQLEntity<Event> lhs, NoSQLEntity<Event> rhs) {
                        if (lhs != null && lhs.getData() != null) {
                            if (rhs != null && rhs.getData() != null) {
                                return AppConst.StrtoDateLite(rhs.getData().getStartTime())
                                        .compareTo(AppConst.StrtoDateLite(lhs.getData().getStartTime()));
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
                .retrieve(new RetrievalCallback<Event>() {
                    @Override
                    public void retrievedResults(List<NoSQLEntity<Event>> noSQLEntities) {
                        for (int i = 0; i < noSQLEntities.size(); i++) {
                            events.add(noSQLEntities.get(i).getData());
                        }
                        eventAdapter.swapItems(events);
                    }
                });
    }

}


