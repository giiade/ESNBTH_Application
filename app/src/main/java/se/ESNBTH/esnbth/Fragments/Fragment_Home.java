package se.ESNBTH.esnbth.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import se.ESNBTH.esnbth.R;
import se.ESNBTH.esnbth.RequestHelper.AppConst;
import se.ESNBTH.esnbth.RequestHelper.Event;

public class Fragment_Home extends Fragment {

    private static final String TAG = Fragment_Home.class.getSimpleName();

    private Button btnNews;
    private Button btnEvents;
    private Button btnAboutKarlskrona;
    private Button btnAboutUs;
    private Button btnVisitUs;

    private UiLifecycleHelper uiHelper;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    String requestId;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(final Session session, final SessionState state, final Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };


    public Fragment_Home(){}


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (Session.getActiveSession().isOpened()){
            Log.i(TAG, "ESTOY DENTRO");
        }else{
            Log.i(TAG,"ESTOY FUERA");
        }

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        btnNews = (Button) rootView.findViewById(R.id.btnNews);
        btnEvents = (Button) rootView.findViewById(R.id.btnEvents);
        btnAboutKarlskrona = (Button) rootView.findViewById(R.id.btnAboutKarlskrona);
        btnAboutUs = (Button) rootView.findViewById(R.id.btnAboutUs);
        btnVisitUs = (Button) rootView.findViewById(R.id.btnVisitUs);




        btnAboutKarlskrona.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View v, MotionEvent event){

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    Fragment_Karlskrona newFragment = new Fragment_Karlskrona();

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.frame_container, newFragment);
                    transaction.commit();


                   /* fragmentManager = getFragmentManager();
                    fragment = new Fragment_Karlskrona();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();*/
                    return true;
                }
                return true;
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume(){
        super.onResume();

        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            btnAboutUs.setVisibility(View.VISIBLE);

            doBatchRequest();
            requestAllEvents();
            // Request user data and show the results
            /*Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        // Display the parsed user info
                        btnAboutUs.setText(buildUserInfoDisplay(user));
                    }
                }
            });*/
        } else if (state.isClosed()) {
            btnAboutUs.setVisibility(View.INVISIBLE);

        }
    }

    private void doBatchRequest() {

        String[] requestIds = {"me", "EsnBth"};

        RequestBatch requestBatch = new RequestBatch();
        for (final String requestId : requestIds) {
            requestBatch.add(new Request(Session.getActiveSession(),
                    requestId, null, null, new Request.Callback() {
                public void onCompleted(Response response) {
                    GraphObject graphObject = response.getGraphObject();
                    String s = btnAboutUs.getText().toString();
                    if (graphObject != null) {
                        if (graphObject.getProperty("id") != null) {
                            s = s + String.format("%s: %s\n",
                                    graphObject.getProperty("id"),
                                    graphObject.getProperty("name"));
                        }
                    }
                    btnAboutUs.setText(s);
                }
            }));
        }
        requestBatch.executeAsync();


    }

    /**
     * Update the event array with last 100 events of the user.
     */
    private void requestAllEvents(){
        String EVENTS = "events";
        Bundle bun = new Bundle();
        bun.putString("fields","id,name,description,location,start_time");
        bun.putInt("limit",100);
        //PAGEID/Events
        String requestStuff = AppConst.Facebook_PageName+EVENTS;
        new Request(Session.getActiveSession(),requestStuff,bun, HttpMethod.GET, new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                Log.i(TAG,response.toString());
                List<Event> events = new ArrayList<>();
                GraphObject jRequest = response.getGraphObject();
                JSONArray jEvents = (JSONArray) jRequest.getProperty("data");
                if (jEvents != null) {
                    for (int i = 0; i < jEvents.length(); i++) {
                        JSONObject item = null;
                        try {
                            item = (JSONObject) jEvents.get(i);
                            Event event = new Event();
                            event.setId(item.getString(AppConst.ID_KEY));
                            event.setName(item.getString(AppConst.NAME_KEY));
                            event.setDescription(item.getString(AppConst.DESCRIPTION_KEY));
                            event.setLocation(item.getString(AppConst.DESCRIPTION_KEY));
                            event.setStartTime(item.getString(AppConst.START_TIME_KEY));
                            String message = (String) item.get("id");
                            Log.i(TAG + ".ITEM " + i, message);
                            events.add(event);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //TODO:SET ADAPTER FOR THE LISTVIEW
                //TODO:IF EVENTS NOT WORKS TRY WITH FEED LINKS.

            }
        }).executeAsync();

    }

    /**
     * Update the event array with last 100 event of the user.
     */
    private void requestEventsByFeed(){}

    /**
     * Update data of selected event
     * @param eventID Current id of the event.
     */
    private void requestEvent(String eventID){}

    /**
     * Update photo of selected event
     * @param eventID current id of the event.
     */
    private void requestEventImage(String eventID){}

    /**
     * Update the array just with the event of the next week
     */
    private void requestFurtherWeekEvents(){}

    /**
     * Update both photo and event data for the list of events
     * @param eventIDS current id of the event.
     */
    private void batchRequestEvents(String[] eventIDS){}

    private String buildUserInfoDisplay(GraphUser user) {
        StringBuilder userInfo = new StringBuilder("");

        // Example: typed access (name)
        // - no special permissions required
        userInfo.append(String.format("Name: %s\n\n",
                user.getName()));

        // Example: access via property name (locale)
        // - no special permissions required
        userInfo.append(String.format("Locale: %s\n\n",
                user.getProperty("locale")));

        // Example: access via key for array (languages)
        // - requires user_likes permission

        // Option 3: Get the language data from the typed interface and after
        // sub-classing GraphUser object to get at the languages.
        GraphObjectList<MyGraphLanguage> languages = (user.cast(MyGraphUser.class)).getLanguages();
        if (languages.size() > 0) {
            ArrayList<String> languageNames = new ArrayList<String>();
            for (MyGraphLanguage language : languages) {
                // Add the language name to a list. Use the name
                // getter method to get access to the name field.
                languageNames.add(language.getName());
            }
            userInfo.append(String.format("Languages: %s\n\n",
                    languageNames.toString()));
        }
        return userInfo.toString();
    }

    // Private interface for GraphUser that includes
    // the languages field: Used in Option 3
    private interface MyGraphUser extends GraphUser {
        // Create a setter to enable easy extraction of the languages field
        GraphObjectList<MyGraphLanguage> getLanguages();
    }

    // Private interface for a language Graph Object
    // for a User: Used in Options 2 and 3
    private interface MyGraphLanguage extends GraphObject {
        // Getter for the ID field
        String getId();
        // Getter for the Name field
        String getName();
    }


}

