package incidents.warriors.com.incidentsver10;

import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sarveshpalav on 14/09/15.
 */
public class ListActivity extends ActionBarActivity {

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_DESC = "desc";



    JSONArray incidents = null;

    ArrayList<HashMap<String, String>> incidentList;

    ListView list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        list = (ListView) findViewById(R.id.listView);
        incidentList = new ArrayList<HashMap<String, String>>();
        getData();
    }

    public void getData() {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://incidents.site50.net/list.php");

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                } finally {
                    try {
                        if (inputStream != null) inputStream.close();
                    } catch (Exception squish) {
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            incidents = jsonObj.getJSONArray(TAG_RESULTS);
            for (int i = 0; i < incidents.length(); i++) {
                JSONObject c = incidents.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String title = c.getString(TAG_TITLE);
                String desc = c.getString(TAG_DESC);

                HashMap<String, String> incidents = new HashMap<String, String>();

                incidents.put(TAG_ID, id);
                incidents.put(TAG_TITLE, title);
                incidents.put(TAG_DESC, desc);

                incidentList.add(incidents);

            }

            ListAdapter adapter = new SimpleAdapter(
                    ListActivity.this, incidentList, R.layout.list_incident,
                    new String[]{TAG_ID, TAG_TITLE, TAG_DESC},
                    new int[]{R.id.lid, R.id.ltitle, R.id.ldesc}
            );
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
id=id+1;
String idd= Long.toString(id);
                    Intent i = new Intent(getApplicationContext(), ViewImage.class);
                    i.putExtra("imageid",idd);
                    startActivity(i);


                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
