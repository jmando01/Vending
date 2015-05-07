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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class VendingItemActivity extends ActionBarActivity {
    private String vendingTitle;
    private String vendingID;
    private JSONParser jsonParser = new JSONParser();
    private JSONObject jObj = null;
    private static final String VENDING_ITEMS_URL = "http://dragon121.startdedicated.com/vending_items.php";
    public static VendingItemBaseAdapter adapter = null;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vending_items);

        Intent intent = getIntent();
        vendingTitle = intent.getStringExtra("vendingTitle");
        vendingID = intent.getStringExtra("vendingID");
        Log.d("Vending ID", vendingID);

        setTitle(vendingTitle);

        new getVendingItems().execute();
    }

    class getVendingItems extends AsyncTask<String, String, String> {

        private ProgressDialog pDialog = new ProgressDialog(VendingItemActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog.setMessage("Getting Items...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {

                    getVendingItems.this.cancel(true);
                }
            });
        }

        @Override
        protected String doInBackground(String... args) {

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("vending_id", vendingID));


                Log.d("request!", "starting");
                // getting product details by making HTTP request
                String json = jsonParser.makeHttpRequest(
                        VENDING_ITEMS_URL, "POST", params);


                // check your log for json response
                Log.d("Getting Items", json.toString());

                return json;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String json) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (json != null){
                ArrayList<VendingItem> vendingItems = new ArrayList<VendingItem>();

                try {

                    JSONArray Jarray = new JSONArray(json);
                    for(int i = 0; i < Jarray.length(); i++) {
                        jObj = new JSONObject();
                        jObj = Jarray.getJSONObject(i);
                        VendingItem vendingItem = new VendingItem(jObj.getString("name"),"Price: " + jObj.getString("price"), 1, "Amount: "  + jObj.getString("amount") );
                        vendingItems.add(vendingItem);
                    }

                    final ListView lv1 = (ListView) findViewById(R.id.vendingItemLv);
                    adapter = new VendingItemBaseAdapter(VendingItemActivity.this, vendingItems);
                    lv1.setAdapter(adapter);

                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                            Object o = lv1.getItemAtPosition(position);
                            final VendingItem item = (VendingItem)o;

                            Toast.makeText(getApplicationContext(),
                                    "Clicked on : " + item.getItemName(), Toast.LENGTH_LONG)
                                    .show();

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VendingItemActivity.this);
                            alertDialogBuilder.setTitle("Confirm Item ");

                            LinearLayout lila1= new LinearLayout(VendingItemActivity.this);
                            lila1.setOrientation(LinearLayout.VERTICAL);

                            final EditText inputConfirm = new EditText(VendingItemActivity.this);
                            inputConfirm.setHint("Type 'confirm' to buy item");
                            inputConfirm.setGravity(Gravity.CENTER);
                            inputConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                            inputConfirm.setText("confirm");

                            final EditText inputAmount = new EditText(VendingItemActivity.this);
                            inputAmount.setHint("Amount of items");
                            inputAmount.setGravity(Gravity.CENTER);
                            inputAmount.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);

                            lila1.addView(inputAmount);
                            lila1.addView(inputConfirm);

                            alertDialogBuilder
                                    .setView(lila1)
                                    .setIcon(R.drawable.roimage)
                                    .setMessage("Item: " + item.getItemName() + ".\n" + item.getItemPrice() + ".\n" + item.getItemCounter() + ".")
                                    .setCancelable(false)
                                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setPositiveButton("Buy Item!", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if(isNumeric(inputAmount.getText().toString())){
                                                if(Integer.parseInt(inputAmount.getText().toString()) <= Integer.parseInt(item.getItemCounter().substring(8)) && Integer.parseInt(inputAmount.getText().toString()) > 0){
                                                    if(inputConfirm.getText().toString().toLowerCase().equals("confirm")){
                                                        Toast.makeText(VendingItemActivity.this, "Item bought confirmed", Toast.LENGTH_LONG).show();
                                                        amount = inputAmount.getText().toString();
                                                        //Aqui se haran las demas cosas que tienen que ver ocn la compra.
                                                        dialog.cancel();
                                                    }else{
                                                        Toast.makeText(VendingItemActivity.this, "Item bought denied, please type 'confirm' to buy item.", Toast.LENGTH_LONG).show();
                                                        dialog.cancel();
                                                    }
                                                }else{
                                                    Toast.makeText(VendingItemActivity.this, "You typed an invalid amount. Please try again.", Toast.LENGTH_LONG).show();
                                                    dialog.cancel();
                                                }
                                            }else{
                                                Toast.makeText(VendingItemActivity.this, "You typed an invalid amount. Please try again.", Toast.LENGTH_LONG).show();
                                                dialog.cancel();
                                            }
                                        }
                                    });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    });
            }catch (Exception e){
                    // TODO: handle exception
                    this.pDialog.dismiss();
                    Log.e("VendingItemActivity", "Error parsing data "+e.toString());
                }
            }

        }

    }

    public boolean isNumeric(String isNumber){
        try {
            int temp = Integer.parseInt(isNumber);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void refreshBtn(MenuItem item){
        startActivity(getIntent());
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vending_items, menu);
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
