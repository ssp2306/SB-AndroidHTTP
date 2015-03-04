package com.stephensparker.sb_androidhttp;

import android.app.Activity;
import android.os.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.quickconnectfamily.json.*;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.*;

public class MainActivity extends Activity {

    private Button buttonOpenURL, buttonClear;
    TextView txtViewParsedValue;
    String strParsedValue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // find the ID of the TextView
        txtViewParsedValue = (TextView) findViewById(R.id.editTextURLOutput);
        buttonOpenURL = (Button) findViewById(R.id.buttonOpenURL);
        buttonClear = (Button) findViewById(R.id.buttonClear);

        addListenerOnClickOpenURL();
        addListenerOnClickClear();
    }

    public void addListenerOnClickClear() {

        buttonClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // clear the output view
                txtViewParsedValue.setText(null);
            }

        });

    }

    public void addListenerOnClickOpenURL() {

        //Select a specific button to bundle it with the action you want

        buttonOpenURL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // grab values from the input fields and pass them to the output text
                EditText editURL = (EditText) findViewById(R.id.editURL);
                String eURL = editURL.getText().toString();

                try {
                    URL url = new URL(eURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    JSONInputStream objectIn = new JSONInputStream(con.getInputStream());

                    // create the Hashmap from the JSON string
                    HashMap familyMap = (HashMap)objectIn.readObject();
                    ArrayList<HashMap> familyMembers = (ArrayList<HashMap>)familyMap.get("familyMembers");

                    // get the family name and size
                    strParsedValue = "Meet the " + familyMap.get("family") + " Clan:\n";
                    strParsedValue += "There are " + familyMembers.size() + " people in the family.\n\n";

                    // pull all the family members from the hashmap
                    for(HashMap person: familyMembers) {

                        strParsedValue += person.get("name").toString();
                        strParsedValue +=" is " + person.get("age");
                        strParsedValue += person.get("gender");
                        strParsedValue += " loves " + person.get("favcolor") + ".\n";
                    }

                    txtViewParsedValue.setText(strParsedValue);

                } catch (Exception e) {
                    e.printStackTrace();
                    strParsedValue = "Something Wrong " + e.toString();
                    txtViewParsedValue.setText(strParsedValue);

                }

            }


        });

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
