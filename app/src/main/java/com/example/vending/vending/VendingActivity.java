package com.example.vending.vending;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class VendingActivity extends ActionBarActivity {

    private ArrayList<Vending> vendings;
    private JSONParser jsonParser = new JSONParser();
    private JSONObject jObj = null;
    public static VendingBaseAdapter adapter = null;
    private static final String VENDINGS_URL = "http://dragon121.startdedicated.com/vendings.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vending);

        new getVendings().execute();
    }

    class getVendings extends AsyncTask<String, String, String> {

        private ProgressDialog pDialog = new ProgressDialog(VendingActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog.setMessage("Getting Vendings...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {

                    getVendings.this.cancel(true);
                }
            });
        }

        @Override
        protected String doInBackground(String... args) {

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                String json = jsonParser.makeHttpRequest(
                        VENDINGS_URL, "POST", params);

                // check your log for json response
                Log.d("Vendings", json.toString());

                return json;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String json) {

            if (json != null){
                vendings = new ArrayList<>();
                try {

                    JSONArray Jarray = new JSONArray(json);
                    for(int i = 0; i < Jarray.length(); i++) {
                        jObj = new JSONObject();
                        jObj = Jarray.getJSONObject(i);
                        Vending vending = new Vending(jObj.getString("title"), jObj.getString("currency"), jObj.getString("id") );
                        vendings.add(vending);
                    }

                    final ListView listView = (ListView) findViewById(R.id.vendingslv);
                    adapter = new VendingBaseAdapter(VendingActivity.this, vendings);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            // When clicked, show a toast with the TextView text

                            Object o = listView.getItemAtPosition(position);
                            Vending vending = (Vending) o;

                            Intent intent = new Intent(VendingActivity.this, VendingItemActivity.class);
                            intent.putExtra("vendingTitle", vending.getVendingName());
                            intent.putExtra("vendingID", vending.getVendingID());
                            startActivity(intent);

                            Toast.makeText(getApplicationContext(),
                                    "Clicked on : " + vending.getVendingName(), Toast.LENGTH_LONG)
                                    .show();

                        }
                    });

                    this.pDialog.dismiss();

                } catch (Exception e) {
                    // TODO: handle exception
                    this.pDialog.dismiss();
                    Log.e("Vending", "Error parsing data "+e.toString());
                }
            }

        }

    }

    public void refreshBtn(MenuItem item){
        startActivity(getIntent());
        finish();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VendingActivity.this);
        alertDialogBuilder.setTitle("You're about to exit ROMP ");
        alertDialogBuilder
                .setMessage("Are you sure you want to exit ROMP?")
                .setCancelable(false)
                .setNeutralButton("  Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("I'm Sure!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
