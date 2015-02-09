package com.ceria.amat.diclemente;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by CERIA Xavier on 04/02/2015.
 */

public class Activity_Menu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        Button buttonBUContacts = (Button)findViewById(R.id.buttonBUContacts);
        Button buttonBUSMS = (Button)findViewById(R.id.buttonBUSMS);
        Button buttonAbout = (Button)findViewById(R.id.buttonAbout);
        Button buttonLogout = (Button)findViewById(R.id.buttonLogout);

        buttonBUSMS.setOnClickListener(
            new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent myIntent = new Intent(Activity_Menu.this, Activity_SMS.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        );

        buttonBUContacts.setOnClickListener(
            new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent myIntent = new Intent(Activity_Menu.this, Activity_Contacts.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        );

        buttonAbout.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(Activity_Menu.this, Activity_About.class);
                        startActivityForResult(myIntent, 0);
                    }
                }
        );

        buttonLogout.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(Activity_Menu.this, Activity_Main.class);
                        startActivityForResult(myIntent, 0);
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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
