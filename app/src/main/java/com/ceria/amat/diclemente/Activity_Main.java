package com.ceria.amat.diclemente;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CERIA Xavier on 04/02/2015.
 */

public class Activity_Main extends ActionBarActivity {

    //Pour désactiver la possiblité de retourner sur la page de menu en appuyant
    // sur la touche "back" quand on doit se loger
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button button = (Button)findViewById(R.id.buttonLogin);
        final EditText loginName = (EditText)findViewById(R.id.loginName);
        final EditText loginPassword = (EditText)findViewById(R.id.loginPassword);

        button.setOnClickListener(
            new Button.OnClickListener() {
                public void onClick(View v) {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://91.121.105.200/API/");
                    Class_General.getInstance().userLogin = ((EditText)findViewById(R.id.loginName)).getText().toString();
                    Class_General.getInstance().userPassword = ((EditText)findViewById(R.id.loginPassword)).getText().toString();
                    try {
                        // Ajout des données
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("action", "login"));
                        nameValuePairs.add(new BasicNameValuePair("login", Class_General.getInstance().userLogin));
                        nameValuePairs.add(new BasicNameValuePair("password", Class_General.getInstance().userPassword));
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        // Executela rqueste HTTP Post
                        HttpResponse httpResponse = httpClient.execute(httpPost);

                        HttpEntity httpEntity = httpResponse.getEntity();
                        String response = EntityUtils.toString(httpEntity);
                        System.out.println(response);
                        // Convert String to json object
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
                            Intent myIntent = new Intent(Activity_Main.this, Activity_Menu.class);
                            startActivity(myIntent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login ou mot de passe incorrect !", Toast.LENGTH_LONG).show();
                            loginName.setText("");
                            loginPassword.setText("");
                        }


                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        );
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
