package com.ceria.amat.diclemente;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CERIA Xavier on 04/02/2015.
 */
public class Activity_Contacts extends ActionBarActivity {

    private String jsonContacts;
    private String httpResponse;
    private String httpResponseCONTACTS;
    private String MemoryLogin;
    private String MemoryPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        //Le Bouton pour le retour au menu
        Button buttonToMenu = (Button)findViewById(R.id.buttonBackToMenu);

        //oN ECOUTE L4EVENEMENT
        buttonToMenu.setOnClickListener(
            new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent myIntent = new Intent(Activity_Contacts.this, Activity_Menu.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        );


        Boolean boolCheckInfosCount;
        Gson gson = new Gson();
        ArrayList<Contact> contactList = new ArrayList();
        Cursor contactInfosCursor = getContentResolver()
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        boolCheckInfosCount = contactInfosCursor.getCount() > 0;

        //emails
        Boolean boolCheckMailCount;
        Uri mailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String idEmail = ContactsContract.CommonDataKinds.Email._ID;
        String nameEmail = ContactsContract.CommonDataKinds.Email.DISPLAY_NAME;
        String addressEmail = ContactsContract.CommonDataKinds.Email.ADDRESS;
        Cursor contactMailCursor = getContentResolver().query(mailUri, new String[]{idEmail,nameEmail,addressEmail}, null, null, nameEmail+ " ASC");
        boolCheckMailCount = contactMailCursor.getCount() > 0;

        // SI les deux boolen de test sont bon
        if (boolCheckInfosCount && boolCheckMailCount) {
            while(contactInfosCursor.moveToNext()) {
                Integer idContact = Integer.parseInt(contactInfosCursor.getString(0));
                String nameContact = contactInfosCursor.getString(1);
                String phoneContact = contactInfosCursor.getString(2);
                String mailContact = "";


                contactList.add(new Contact(idContact, nameContact, phoneContact, null));
            }

            jsonContacts = gson.toJson(contactList);
            System.out.println(jsonContacts);
        }

           // On memorise les log car on en a besoin pour les requetes
        MemoryLogin = Class_General.getInstance().userLogin;
        MemoryPassword = Class_General.getInstance().userPassword;

        Toast.makeText(getApplicationContext(), "Getting contacts...", Toast.LENGTH_SHORT).show();
        backupContacts();

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

    private void backupContacts() {

        try {
            // Initialisation du client
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://91.121.105.200/API/");

            //la list de param
            List<NameValuePair> nameValuePairs = new ArrayList<>(4);
            nameValuePairs.add(new BasicNameValuePair("action", "backupcontacts"));
            nameValuePairs.add(new BasicNameValuePair("login", MemoryLogin));
            nameValuePairs.add(new BasicNameValuePair("password", MemoryPassword));
            nameValuePairs.add(new BasicNameValuePair("contacts", jsonContacts));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //ResponseHandler<String> stringResponseHandler = new BasicResponseHandler();
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            String response = EntityUtils.toString(httpEntity);
            System.out.println(response);

            // On converti le string en object JSON
            JSONObject json = null;
            try {
                json = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            assert json != null;
            String str_value=json.getString("success");
            System.out.println(str_value);

            if (str_value == "true") {
                //Si tout s'est bien passé
                Toast.makeText(getApplicationContext(), "Backup Reussi !", Toast.LENGTH_LONG).show();
            }
            else {
                // SI ça va pas
                Toast.makeText(getApplicationContext(), "le Backup à échoué", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e){
            //Le log d'erreur
            Toast.makeText(Activity_Contacts.this.getApplicationContext(),"Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        httpResponseCONTACTS = httpResponse;


    }
}