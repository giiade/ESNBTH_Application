package se.ESNBTH.esnbth.RequestHelper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import se.ESNBTH.esnbth.Activities.MainLayActivity;
import se.ESNBTH.esnbth.R;

public class UpdateService extends Service {

    final String TAG = UpdateService.class.getSimpleName();

    boolean e = false;
    boolean fe = false;
    private Context context;
    private Session session;
    private Bundle bun;

    //Events
    private List<Event> events = new ArrayList<>(); //Here we will save events.
    private ArrayList<Event> imgEvents = new ArrayList<>();
    private ArrayList<Event> infoEvents = new ArrayList<>();

    //Feed
    private List<Feed> feeds = new ArrayList<>();


    @Override
    public void onCreate() {
        Log.i(TAG, "SERVICIO EMPEZADO");
        bun = new Bundle();
        Session.saveSession(Session.getActiveSession(), bun);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(Session.getActiveSession() == null)
        Session.restoreSession(getApplicationContext(),null,null,bun);

        Log.i(TAG, "SERVICIO Llamado " + Calendar.getInstance().getTimeInMillis());
        context = getApplication().getApplicationContext();
        requestAllEvents();
        //createNotification();
        showNotification();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!e || !fe) {
                }
                stopSelf();
            }
        }).start();

        //return super.onStartCommand(intent, flags, startId);

        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //TODO: Create the binding for the service, put service in a different process.


    public void EventSqlConverter(List<Event> events) {


        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            NoSQLEntity<Event> entity = new NoSQLEntity<>(AppConst.EVENTSQL_KEY, e.getId());
            entity.setData(e);
            NoSQL.with(context).using(Event.class).save(entity);
        }


    }

    public void FeedSqlConverter(List<Feed> feeds) {
        for (int i = 0; i < feeds.size(); i++) {
            Feed f = feeds.get(i);
            NoSQLEntity<Feed> entity = new NoSQLEntity<Feed>(AppConst.FEEDSQL_KEY);
            entity.setData(f);
            NoSQL.with(context).using(Feed.class).save(entity);
        }
    }

    public void createNotification() {

        Intent intent = new Intent(context, MainLayActivity.class);

        PendingIntent resultPendingItent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("ESN BTH")
                .setContentText("There are some new events")
                .setContentIntent(resultPendingItent);

        //Set an ID for notification.
        int mNotification = 001;
        //Get an instance of NotificationManager.
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotification, mbuilder.build());
    }


    /**
     * Update the event array with last 100 events.
     */
    private void requestAllEvents() {
        String EVENTS = "events";
        Bundle bun = new Bundle();
        //bun.putString("fields","id,name,description,location,start_time");
        bun.putString("fields", "id");
        bun.putString("since", AppConst.getSinceTime());
        bun.putInt("limit", 100);
        //PAGEID/Events
        String requestStuff = AppConst.Facebook_PageName + EVENTS;
        new Request(Session.getActiveSession(), requestStuff, bun, HttpMethod.GET, new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                Log.i(TAG, response.toString());
                events = new ArrayList<>();
                GraphObject jRequest = response.getGraphObject();
                if(jRequest!=null) {
                    JSONArray jEvents = (JSONArray) jRequest.getProperty("data");
                    if (jEvents != null) {
                        for (int i = 0; i < jEvents.length(); i++) {
                            JSONObject item = null;
                            try {
                                item = (JSONObject) jEvents.get(i);
                                Event event = new Event();
                                event.setId(item.getString(AppConst.ID_KEY));

                                String message = (String) item.get("id");
                                Log.i(TAG + ".ITEM " + i, message);
                                events.add(event);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                //Get the data of the events

                batchRequestEvents(events);



            }
        }).executeAsync();

    }


    /**
     * Called internally by requestAllEvents it fetch data from the event as
     * Id, name,Description,location and start time
     *
     * @param eventsArray Contains an array of Events with their IDs
     */
    private void batchRequestEvents(List<Event> eventsArray) {
        final Session session = Session.getActiveSession();

        //clear the infoEvents
        infoEvents.clear();

        if (session != null && session.isOpened()) {
            //Create a requestBatch
            RequestBatch requestBatch = new RequestBatch();

            //Request the information of the events and save it in @infoEvents
            for (int i = 0; i < eventsArray.size(); i++) {

                //Get id of the current event
                String requestID = eventsArray.get(i).getId();

                //Create the request to the requestID
                Bundle bun = new Bundle();
                bun.putString("fields", "id,name,description,location,start_time");
                Request request = new Request(session, requestID, bun, null, new Request.Callback() {
                    @Override
                    public void onCompleted(Response response) {
                        // Log.i(TAG,response.toString());
                        //Test if we don't have an empty response
                        if (response != null) {
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
                    events = AppConst.mergeAllInfoEvents(events, infoEvents);
                    Log.i("RequestEvents -->" + TAG, events.get(0).getDescription());
                    batchRequestImages(events);

                }
            });

            requestBatch.executeAsync();


        }

    }

    /**
     * Called internally by requestAllEvents it fetch the image
     * that the event will use.
     *
     * @param eventsArray Contains an array of Events with their IDs
     */
    private void batchRequestImages(List<Event> eventsArray) {
        final Session session = Session.getActiveSession();
        //Clear the imgEvents
        imgEvents.clear();

        if (session != null && session.isOpened()) {
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
                    events = AppConst.mergeAllImgEvents(events, imgEvents);
                    Log.i("IMAGE-->" + TAG, events.get(0).getImgUrl());

                    //Check if we have new events.
                    //List<Event> sqlEvents = getSqlEvents();

                    //if(events.size() > sqlEvents.size()){
                    //We have get more events than the one we have
                    //then we have new events
                    //createNotification();
                    //}

                    NoSQL.with(context).using(Event.class).bucketId(AppConst.EVENTSQL_KEY).delete();
                    EventSqlConverter(events);
                    e = true;


                }
            });

            requestBatch.executeAsync();
        }
    }


    /**
     * Make a request to the server and download new events, the compare the size of
     * this array with the one that we retrieve from sql and if the one retrieved is bigger
     * it shows a notification.
     */
    private void showNotification() {
        String EVENTS = "events";
        Date date = Calendar.getInstance().getTime();
        String since = String.valueOf(date.getTime()).substring(0, 10);

        Bundle bun = new Bundle();
        bun.putString("fields", "id");
        bun.putString("since", since);
        bun.putInt("limit", 100);

        String requestStuff = AppConst.Facebook_PageName + EVENTS;
        new Request(Session.getActiveSession(), requestStuff, bun, HttpMethod.GET, new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                Log.i(TAG, response.toString());
                try {
                    if (Session.getActiveSession().isOpened()) {
                        List<Event> auxEvents = new ArrayList<>();
                        GraphObject jRequest = response.getGraphObject();
                        JSONArray jEvents = (JSONArray) jRequest.getProperty("data");
                        if (jEvents != null && jEvents.length() > 0) {
                            for (int i = 0; i < jEvents.length(); i++) {
                                JSONObject item = null;
                                try {
                                    item = (JSONObject) jEvents.get(i);
                                    Event event = new Event();
                                    event.setId(item.getString(AppConst.ID_KEY));

                                    String message = (String) item.get("id");
                                    Log.i(TAG + " -->FUTURE_EVENTS " + i, message);
                                    auxEvents.add(event);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            //Compare it with the events we have on sql.
                            List<Event> sqlEvents = getSqlEvents();

                            if (auxEvents.size() > sqlEvents.size()) {
                                createNotification();
                            }
                        }
                        fe = true;
                    }
                } catch (Exception e) {
                    //NADA
                    fe = true;
                }
            }
        }).executeAsync();
    }

    /**
     * Take all the data from the future events table.
     *
     * @return List of events. List<Event>
     */
    private List<Event> getSqlEvents() {
        final List<Event> e = new ArrayList<>();
        NoSQL.with(context).using(Event.class).bucketId(AppConst.FEVENTSQL_KEY).retrieve(new RetrievalCallback<Event>() {
            @Override
            public void retrievedResults(List<NoSQLEntity<Event>> noSQLEntities) {
                for (int i = 0; i < noSQLEntities.size(); i++) {
                    e.add(noSQLEntities.get(i).getData());
                }
                ;
            }
        });
        return e;
    }
}
