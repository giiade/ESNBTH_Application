package se.ESNBTH.esnbth.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.util.Arrays;
import java.util.List;

import se.ESNBTH.esnbth.Fragments.Fragment_Karlskrona;
import se.ESNBTH.esnbth.R;

public class Splash_Screen extends FragmentActivity {

    //TODO:
    /*
    Facebook Api.
    Pagina web ESNBTH
    https://developers.facebook.com/tools/explorer?method=GET&path=348101891945454%2Fevents&version=v2.2
    Recoger eventos desde ahí.

     */

    private static int SPLASH_TIMEOUT = 2000;
    Animation fadeOut;
    RelativeLayout splashLay;
    TextView splashText;

    private LoginButton loginBtn;

    private UiLifecycleHelper uiHelper;

    private static final List<String> PERMISSIONS =Arrays.asList("user_likes", "user_events");

    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            if (state.isOpened()) {
                Log.d("FacebookSampleActivity", "Facebook session opened");
            } else if (state.isClosed()) {
                Log.d("FacebookSampleActivity", "Facebook session closed");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uiHelper = new UiLifecycleHelper(this,statusCallback);
        uiHelper.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        splashLay = (RelativeLayout) findViewById(R.id.splashLayout);
        splashText = (TextView) findViewById(R.id.splshTxt);
        loginBtn = (LoginButton) findViewById(R.id.fbLoginBtn);
        loginBtn.setVisibility(View.GONE);
        loginBtn.setReadPermissions(PERMISSIONS);
        loginBtn.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                if (user != null) {
                    loginBtn.setVisibility(View.GONE);
                    splashText.setVisibility(View.VISIBLE);
                }

            }
        });

        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                /* */
                if(Session.getActiveSession().isOpened()){
                    Intent i = new Intent(Splash_Screen.this, MainLayActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    splashText.setVisibility(View.GONE);
                    loginBtn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //Nothing
            }
        });


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                if(Session.getActiveSession().isOpened()){
                  splashLay.startAnimation(fadeOut);
                }else{
                  splashText.startAnimation(fadeOut);
                }


            }
        }, SPLASH_TIMEOUT);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }


}
