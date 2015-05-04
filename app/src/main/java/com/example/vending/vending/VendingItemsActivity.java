package com.example.vending.vending;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class VendingItemsActivity extends ActionBarActivity {
    private String vendingTitle;
    public static VendingBaseAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vending_items);

        Intent intent = getIntent();
        vendingTitle = intent.getStringExtra("VendingTitle");

        setTitle(vendingTitle);


        VendingItem vendingItems = new VendingItem("Viola Pollos","Zeny: 820000", 1,"X2");
        VendingItem vendingItems1 = new VendingItem("Ezreal Favorite","Zeny: 12820000", 1,"X1");
        VendingItem vendingItems2 = new VendingItem("Dildo","Zeny: 2820000", 1,"X10 ");


        ArrayList<VendingItem> results = new ArrayList<VendingItem>();
        results.add(vendingItems);
        results.add(vendingItems1);
        results.add(vendingItems2);

        final ListView lv1 = (ListView) findViewById(R.id.vendingItemLv);
        adapter = new VendingBaseAdapter(this, results);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Object o = lv1.getItemAtPosition(position);
                VendingItem item = (VendingItem)o;

                Toast.makeText(getApplicationContext(),
                        "Clicked on : " + item.getItemName(), Toast.LENGTH_LONG)
                        .show();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VendingItemsActivity.this);
                alertDialogBuilder.setTitle("Confirm Item ");
                alertDialogBuilder
                        .setIcon(R.drawable.roimage)
                        .setMessage("Item: " + item.getItemName() +".\n" + item.getItemPrice() + ".")
                        .setCancelable(false)
                        .setNeutralButton("Buy Item!",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


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
