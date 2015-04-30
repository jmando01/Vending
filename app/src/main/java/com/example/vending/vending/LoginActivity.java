package com.example.vending.vending;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class LoginActivity extends ActionBarActivity {

    private EditText usernameEdit;
    private EditText passwordEdit;
    private String username;
    private String password;
    private String DBpassword;
    private ProgressDialog Asycdialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        // There are no active networks.
        if (ni == null) return false;
        else
            return true;
    }

    public void loginbtn(View view){

        if(!isNetworkConnected()){

            Asycdialog.dismiss();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("No Network Avaliable!");
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

            Asycdialog = new ProgressDialog(LoginActivity.this);
            Asycdialog.setMessage("Logging In...");
            Asycdialog.setCanceledOnTouchOutside(false);
            Asycdialog.show();

            usernameEdit = (EditText) findViewById(R.id.username);
            passwordEdit = (EditText) findViewById(R.id.password);
            username = usernameEdit.getText().toString();
            password = passwordEdit.getText().toString();

            if(password.equals(GetPasswordWithUsername(username))){//Next Activity if valid.

                Asycdialog.dismiss();

            }else{//Wrong Credentials

                Asycdialog.dismiss();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Invalid Credentials!");
                alertDialogBuilder
                        .setMessage("Your user ID or password is wrong.")
                        .setCancelable(false)
                        .setNeutralButton("Got it!",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
    }

    public String GetPasswordWithUsername(String username){
        //El proceso para buscar datos en la clave en la base de datos se debe de hacer con el username.
        DBpassword = " ";

        return DBpassword;
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
