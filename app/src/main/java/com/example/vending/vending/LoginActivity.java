package com.example.vending.vending;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends ActionBarActivity {

    private EditText usernameEdit;
    private EditText passwordEdit;
    private String username;
    private String password;

    private static final String LOGIN_URL = "http://dragon121.startdedicated.com/login.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static JSONObject jObj = null;

    private Context context;
    private JSONParser jsonParser = new JSONParser();
    private long mLastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    public void loginbtn(View view){

        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();

        if(!isNetworkConnected()){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("No Network Available!");
            alertDialogBuilder
                    .setMessage("Your device is not connected to the Internet. Please check your connection and try again.")
                    .setCancelable(false)
                    .setNeutralButton("Got it!",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        }else {
            new AttemptLogin().execute();

        }
    }

    class AttemptLogin extends AsyncTask<String, String, String> {

        private ProgressDialog pDialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {

                    AttemptLogin.this.cancel(true);
                }
            });
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;

            usernameEdit = (EditText) findViewById(R.id.username);
            passwordEdit = (EditText) findViewById(R.id.password);
            username = usernameEdit.getText().toString();
            password = passwordEdit.getText().toString();

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("userid", username));
                params.add(new BasicNameValuePair("user_pass", password));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                String json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                // try parse the string to a JSON object
                try {
                    jObj = new JSONObject(json);
                } catch (JSONException e) {
                    Log.e("JSON Object", "Error parsing data " + e.toString());
                }

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = jObj.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", jObj.toString());
                    Intent intent = new Intent(LoginActivity.this, VendingActivity.class);
                    finish();
                    startActivity(intent);
                    return jObj.getString(TAG_MESSAGE);
                }else{
                    Log.d("Login Failure!", jObj.getString(TAG_MESSAGE));
                    return jObj.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(LoginActivity.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
