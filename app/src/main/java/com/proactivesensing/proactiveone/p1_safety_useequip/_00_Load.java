package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class _00_Load extends AppCompatActivity {

    private boolean PERM = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._load);

        new _Variables();
        _Variables.CONTEXT.set(getApplicationContext());
        ((TextView) findViewById(R.id.version)).setText("v" + _Variables.VERSION.get());

        new _Settings(false, 3);
        new _Settings(false, 2);


        switch(_Variables.COMPILER_MODE.get()) {
            case 1: //  WIRELESS
                break;
            case 2: //  CONFIGURE EQUIPMENT
                break;
            case 3: //  USE EQUIPMENT
                ((TextView) findViewById(R.id.note)).setText("Safety App:  Use Equipment");
                if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE))
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 138);
                else
                    PERM = true;

                if(PERM)
                    next(true);
                break;
            case 4: //  MANAGE RENTAL
                ((TextView) findViewById(R.id.note)).setText("Safety App:  Manage Rental");
                next(false);
                break;
            default:
                Log.e("ERROR", "_01_Home (1): public void onCreate(Bundle savedInstanceState)");
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE))
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 138);
        else
            PERM = true;

        if(PERM)
            next(true);
    }

    public void next(boolean threaded) {
        if(threaded)
            new Thread(new _File(true)).start();

        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                //new _Settings(false);
                new _File(false);

                Intent intent = new Intent(_Variables.CONTEXT.get(), _01_Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        };
        Handler handler1 = new Handler();
        handler1.removeCallbacks(run1);
        handler1.postDelayed(run1, 1000);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
