package com.friday.etsfinalone;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CODE = 1234;
    private Activity mActivity;
    private Context mContext;
    private Button mCustomer;
    private Button mDriver;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1102R.layout.activity_main);
        this.mDriver = (Button) findViewById(C1102R.C1105id.responder);
        this.mCustomer = (Button) findViewById(C1102R.C1105id.user);
        startService(new Intent(this, onAppKilled.class));
        this.mDriver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ResponderLoginActivity.class));
                MainActivity.this.finish();
            }
        });
        this.mCustomer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, UserLoginActivity.class));
                MainActivity.this.finish();
            }
        });
        checkPermission();
    }

    /* access modifiers changed from: protected */
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") + ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") + ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") + ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") + ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            return;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_CONTACTS") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage((CharSequence) "Camera, Read Contacts, Location and Read External Storage permissions are required to do the task.");
            builder.setTitle((CharSequence) "Please grant those permissions");
            builder.setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.CAMERA", "android.permission.READ_CONTACTS", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION"}, MainActivity.MY_PERMISSIONS_REQUEST_CODE);
                }
            });
            builder.setNeutralButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) null);
            builder.create().show();
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA", "android.permission.READ_CONTACTS", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION"}, MY_PERMISSIONS_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0 || grantResults[0] + grantResults[1] + grantResults[2] + grantResults[3] + grantResults[4] != 0) {
                Toast.makeText(this, "Permissions denied.", 0).show();
            } else {
                Toast.makeText(this, "Permissions granted.", 0).show();
            }
        }
    }
}
