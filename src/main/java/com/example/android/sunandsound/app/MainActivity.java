package com.example.android.sunandsound.app;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Http;
import com.soundcloud.api.Request;
import com.soundcloud.api.Token;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        AsynhTaskHelper myHelper = new AsynhTaskHelper();
        myHelper.execute();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public static class AsynhTaskHelper extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            File WRAPPER_SER = new File("wrapper.ser");

            ApiWrapper wrapper = new ApiWrapper("a55a47809ea1931beefc46c39bbba83f", "524bb61f08a3870bd9e45fe851de9126", null, null);
            Token token = null;
            try {
                token = wrapper.login("okuklina@hotmail.com", "*******", Token.SCOPE_NON_EXPIRING);

                //HttpResponse resp = wrapper.get(Request.to("/me"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                GetResource(wrapper);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }


        public static void GetResource(ApiWrapper wrapper) throws Exception {


            final Request resource = Request.to("/me");
            System.out.println("GET " + resource);
            try {
                HttpResponse resp = wrapper.get(resource);
                if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String res = Http.getString(resp);
                    JSONObject json = new JSONObject(res);
                    Log.d("MainActivity", json.getString("first_name") + " " + json.getString("last_name"));
                    System.out.println("\n" + Http.formatJSON(res));
                } else {
                    System.err.println("Invalid status received: " + resp.getStatusLine());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
