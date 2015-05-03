package com.example.vending.vending;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class VendingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vending);

        VendingList();
    }

    public void VendingList(){

        String [] status = new String [] {"[Cash] todo a 5", "[Cash] Regalo Cortesia DropingParty", "[Cash] Barato y BonitO", "[Cash] o.o", "[Premium Ticket] dsfhgj", "[Cash] 20 todo"} ;

        ListView listView = (ListView) findViewById(R.id.vendingslv);

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, status));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text


                Toast.makeText(getApplicationContext(),
                        "Clicked on : " + position, Toast.LENGTH_LONG)
                        .show();


            }
        });

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
