package com.proactivesensing.proactiveone.p1_safety_useequip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class _01_Home extends AppCompatActivity {

    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _Variables.CONTEXT.set(getApplicationContext());

        switch(_Variables.COMPILER_MODE.get()) {
            case 1: //  WIRELESS
                break;
            case 2: //  CONFIGURE EQUIPMENT
                break;
            case 3: //  USE EQUIPMENT
                setContentView(R.layout.a_01___home);
                ((TextView) findViewById(R.id.version)).setText("v" + _Variables.VERSION.get());
                ((TextView) findViewById(R.id.note)).setText("Safety App:  Use Equipment");
                ((Button) findViewById(R.id.button_1)).setText("Operate Equipment");
                ((Button) findViewById(R.id.button_2)).setText("Check For Updates");
                update = findViewById(R.id.button_2);
                setButtonColorBackToNormal();
                break;
            case 4: //  MANAGE RENTAL
                setContentView(R.layout.b_01___home);
                ((TextView) findViewById(R.id.note)).setText("Safety App:  Manage Rental");
                ((TextView) findViewById(R.id.version)).setText("v" + _Variables.VERSION.get());
                ((Button) findViewById(R.id.button_1)).setText("Initiate Rental");
                ((Button) findViewById(R.id.button_2)).setText("Terminate Rental");
                break;
            default:
                Log.e("ERROR", "_01_Home (1): public void onCreate(Bundle savedInstanceState)");
                break;
        }
    }

    public _01_Home() {}

    public void setButtonColorBackToNormal() {
        if(Build.VERSION.SDK_INT >= 22)
            ((Button) findViewById(R.id.button_2)).setBackground(getDrawable(R.drawable.material_button_blue));
        else
            ((Button) findViewById(R.id.button_2)).setBackground(getResources().getDrawable(R.drawable.material_button_blue));
    }

    public void button1(View view) { button(1); }
    public void button2(View view) { button(2); }

    public void button(int which) {
        Intent intent = null;
        switch(_Variables.COMPILER_MODE.get()) {
            case 1: //  WIRELESS
                break;
            case 2: //  CONFIGURE EQUIPMENT
                break;
            case 3: //  USE EQUIPMENT
                if(which == 1) {
                    intent = new Intent(this, _b2_EnterId.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else if(which == 2) {
                    //if(Build.VERSION.SDK_INT >= 22)
                    //    ((Button) findViewById(R.id.button_2)).setBackground(getDrawable(R.drawable.material_button_grey));
                    //else
                    //    ((Button) findViewById(R.id.button_2)).setBackground(getResources().getDrawable(R.drawable.material_button_grey));

                    //Toast.makeText(this, "Database Is Up To Date", Toast.LENGTH_SHORT).show();
                    (new Thread(new _File(true))).start();
                }
                else
                    Log.e("ERROR", "_01_Home (1): public void button1(View view)");
                break;
            case 4: //  MANAGE RENTAL
                if(which == 1)
                    _Variables.MANAGE_RENTAL_MODE.set(false);
                else if (which == 2)
                    _Variables.MANAGE_RENTAL_MODE.set(true);
                else
                    Log.e("ERROR", "_01_Home (2): public void button1(View view)");
                intent = new Intent(this, _b2_EnterId.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            default:
                Log.e("ERROR", "_01_Home (3): public void button1(View view)");
                break;
        }

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void example() {
        switch(_Variables.COMPILER_MODE.get()) {
            case 1: //  WIRELESS
                break;
            case 2: //  CONFIGURE EQUIPMENT
                break;
            case 3: //  USE EQUIPMENT
                break;
            case 4: //  MANAGE RENTAL
                break;
            default:
                Log.e("ERROR", "_1_Home (1): public void onCreate(Bundle savedInstanceState)");
                break;
        }
    }
}
