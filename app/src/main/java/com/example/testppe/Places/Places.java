package com.example.testppe.Places;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testppe.R;

//import com.actionbarsherlock.app.SherlockActivity;
public class Places extends AppCompatActivity implements ActionBar.OnNavigationListener,
        OnItemClickListener {
public ArrayList<GetterSetter> myArrayList;
        ListView myList;
private String[] places;
        ProgressDialog dialog;
        TextView nodata;
        CustomAdapter adapter;
        GetterSetter addValues;

@Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity);

        if (!isNetworkAvailable()) {
        Toast.makeText(getApplicationContext(), "Enable internet connection and RE-LAUNCH!!",
        Toast.LENGTH_LONG).show();
        return;
        }

        myList = (ListView) findViewById(R.id.myList);

        places = getResources().getStringArray(R.array.places);
        Context context = getSupportActionBar().getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.places,
        R.layout.support_simple_spinner_dropdown_item);
        list.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setListNavigationCallbacks(list, this);

        }

private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
        }

@Override public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        dialog = ProgressDialog.show(this, "", "Please wait", true);
        // Add your Google Places Access key, and update the location values.
        new readFromGooglePlaceAPI()
        .execute("https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
        + "location=45,3&radius=100000&sensor=true&"
        + "key=AIzaSyBDxZlj-wQXgfWbtdUrncZmbfsOv85JWok&types="
        + places[itemPosition]);
        myList.setOnItemClickListener(this);
        return true;
        }

public class readFromGooglePlaceAPI extends AsyncTask<String, Void, String> {
    @Override protected String doInBackground(String... param) {
        return readJSON(param[0]);
    }

    protected void onPostExecute(String str) {
        myArrayList = new ArrayList<GetterSetter>();
        try {
            JSONObject root = new JSONObject(str);
            JSONArray results = root.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject arrayItems = results.getJSONObject(i);
                JSONObject geometry = arrayItems.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                addValues = new GetterSetter();
                addValues.setLat(location.getString("lat"));
                addValues.setLon(location.getString("lng"));
                addValues.setName(arrayItems.getString("name").toString());
                addValues.setRating(arrayItems.getString("rating").toString());
                addValues.setVicinity(arrayItems.getString("vicinity").toString());
                myArrayList.add(addValues);

                Log.d("Before", myArrayList.toString());

            }

        } catch (Exception e) {

        }
        System.out
                .println("############################################################################");
        Log.d("After:", myArrayList.toString());
        nodata = (TextView) findViewById(R.id.nodata);
        nodata.setVisibility(View.GONE);
        adapter = new CustomAdapter(Places.this, R.layout.listview, myArrayList);
        myList.setAdapter(adapter);
        // adapter.notifyDataSetChanged();
        dialog.dismiss();
    }

}

    public String readJSON(String URL) {
        StringBuilder sb = new StringBuilder();
        HttpGet httpGet = new HttpGet(URL);
        HttpClient client = new DefaultHttpClient();

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } else {
                Log.e("JSON", "Couldn't find JSON file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Intent details = new Intent(Places.this, Details.class);
        details.putExtra("name", myArrayList.get(arg2).getName());
        details.putExtra("rating", myArrayList.get(arg2).getRating());
        details.putExtra("vicinity", myArrayList.get(arg2).getVicinity());
        details.putExtra("lat", myArrayList.get(arg2).getLat());
        details.putExtra("lon", myArrayList.get(arg2).getLon());
        startActivity(details);
    }

}