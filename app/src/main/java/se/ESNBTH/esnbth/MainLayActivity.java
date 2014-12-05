package se.ESNBTH.esnbth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainLayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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

    public void openNewsActivity(View view) {
        Intent intent = new Intent(this, News_List.class);
        startActivity(intent);
    }

    public void openEventsActivity(View view) {
        Intent intent = new Intent(this, Event_List.class);
        startActivity(intent);
    }

    public void openAboutKarlActivity(View view) {
        Intent intent = new Intent(this, Karlskrona_Inf.class);
        startActivity(intent);
    }

    public void openAboutUsActivity(View view) {
        Intent intent = new Intent(this, Esn_Inf.class);
        startActivity(intent);
    }

}
