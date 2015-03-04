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
                    //HashMap theMap = (HashMap)objectIn.readObject();
                    //HashMap familyMap = (HashMap)theMap.get("members");
                    HashMap familyMap = (HashMap)objectIn.readObject();
//                    ArrayList family = new ArrayList();
//                    for (int i = 0; i > familyMap.size(); i++) {
//                        family.add(i, "name");
//                    }

//                    strParsedValue = family.get(1).toString();
//                    strParsedValue +=" is " + familyMap.get("age");
//                    strParsedValue += familyMap.get("gender");
//                    strParsedValue += " loves " + familyMap.get("favcolor") + ".\n";


                    // get the family name
                    //strParsedValue = "Meet the " + familyMap.get("members") + " Clan:\n";

                    //strParsedValue += "There are " + familyMap.size() + " people in the family.\n\n";

                    //get the family members and info

                    strParsedValue = (String) familyMap.get("name");
                    strParsedValue +=" is " + familyMap.get("age");
                    strParsedValue += familyMap.get("gender");
                    strParsedValue += " loves " + familyMap.get("favcolor") + ".\n";

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
