package com.example.vending.vending;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class VendingActivity extends ActionBarActivity {

    ArrayList<String> vendings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vending);

        new task().execute();
    }

    class task extends AsyncTask<String, String, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(VendingActivity.this);
        InputStream is = null ;
        String result = "";
        protected void onPreExecute() {


            progressDialog.setMessage("Loading Vendings...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {

                    task.this.cancel(true);

                }

            });
        }

        @Override
        protected Void doInBackground(String... params) {

            String url_select = "http://dragon121.startdedicated.com/vendings.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url_select);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {

                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                //read content
                is =  httpEntity.getContent();

            } catch (Exception e) {

                Log.e("log_tag", "Error in http connection " + e.toString());
            }

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line=br.readLine())!=null) {

                    sb.append(line+"\n");
                }

                is.close();
                result=sb.toString();

            } catch (Exception e) {

                // TODO: handle exception
                Log.e("log_tag", "Error converting result "+e.toString());
            }

            return null;

        }

        protected void onPostExecute(Void v) {

            vendings = new ArrayList<>();

            try {

                JSONArray Jarray = new JSONArray(result);
                for(int i=0;i<Jarray.length();i++) {
                    JSONObject Jasonobject = null;
                    Jasonobject = Jarray.getJSONObject(i);
                    vendings.add(Jasonobject.getString("title"));
                }

                final ListView listView = (ListView) findViewById(R.id.vendingslv);

                listView.setAdapter(new ArrayAdapter<String>(VendingActivity.this, android.R.layout.simple_list_item_1, vendings));

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // When clicked, show a toast with the TextView text

                        Object o = listView.getItemAtPosition(position);
                        String title = (String)o;

                        Intent intent = new Intent(VendingActivity.this, VendingItemsActivity.class);
                        intent.putExtra("VendingTitle", title);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(),
                                "Clicked on : " + title, Toast.LENGTH_LONG)
                                .show();


                    }
                });

                this.progressDialog.dismiss();

            } catch (Exception e) {
                // TODO: handle exception
                this.progressDialog.dismiss();
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vending, menu);
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
}
