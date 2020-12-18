package com.stubborn.savingperticularsmstofirestore;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    String[] PERMISSIONS = {Manifest.permission.READ_SMS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView lViewSMS = (ListView) findViewById(R.id.listViewSMS);

        if (fetchInbox() != null) {
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fetchInbox());
            lViewSMS.setAdapter(adapter);
        }
    }

    public ArrayList fetchInbox() {
        ArrayList sms = new ArrayList();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            requestContactsPermissions();

            return requestContactsPermissions();
        }

        Uri uriSms = Uri.parse("content://sms/inbox");
//        Cursor cursor = getContentResolver().query(uriSms,
//                new String[]{"_id", "address = '+918484934914'", "date", "body"}, null, null, null);

        String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };

        //for fetching particular contact sms sending value to querry
        Cursor cursor = getContentResolver().query(uriSms, projection, "address='+918484934914'", null, "date desc");

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String address = cursor.getString(1);
            String body = cursor.getString(3);
            // String deafult_add = "+918484934914";

            Log.e("adress****************", address + "");
            Log.e("body*****************", body + "");

            sms.add("Address = " + address + "\n" + "n SMS =  " + body);





    }
        return sms;

}

    private ArrayList requestContactsPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
        return null;
    }
}

