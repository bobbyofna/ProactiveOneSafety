package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class _b2_EnterId extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _Variables.CONTEXT.set(getApplicationContext());

        switch(_Variables.COMPILER_MODE.get()) {
            case 1: //  WIRELESS
                break;
            case 2: //  CONFIGURE EQUIPMENT
                break;
            case 3: //  USE EQUIPMENT
                setContentView(R.layout.b_02___enter_op_id);
                ((TextView) findViewById(R.id.note)).setText("Safety App:  Use Equipment");
                ((TextView) findViewById(R.id.version)).setText("v" + _Variables.VERSION.get());
                ((Button) findViewById(R.id.button_1)).setText("Send");

                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                break;
            case 4: //  MANAGE RENTAL
                setContentView(R.layout.b_02___enter_op_id);
                ((TextView) findViewById(R.id.note)).setText("Safety App:  Manage Rental");
                ((TextView) findViewById(R.id.version)).setText("v" + _Variables.VERSION.get());
                ((Button) findViewById(R.id.button_1)).setText("Send");

                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                break;
            default:
                Log.e("ERROR", "_b2_EnterId (1): public void onCreate(Bundle savedInstanceState)");
                break;
        }
    }

    public void send(View view) {
        //  TODO:   PREVENT THE USER FROM BEING ABLE TO ENTER TOO LARGE OR TOO SMALL OF A NUMBER
        short input = 0;
        int testInput = 0;

        try {
            testInput = Integer.parseInt("" + ((EditText) findViewById(R.id.edittext_1)).getText());
            input = (short) testInput;
        } catch(Exception e) {
            Toast.makeText(_Variables.CONTEXT.get(), "Invalid Input, Please Try Again", Toast.LENGTH_SHORT).show();
            ((EditText) findViewById(R.id.edittext_1)).setText("");
        }

        switch (_Variables.COMPILER_MODE.get()) {
            case 1: //  WIRELESS
                break;
            case 2: //  CONFIGURE EQUIPMENT
                break;
            case 3: //  USE EQUIPMENT
                _Variables.OPERATOR_ID.set(input);
                _Variables.USE_EQUIP_MODE.set(false);
                break;
            case 4: //  MANAGE RENTAL
                _Variables.CUSTOMER_ID.set(input);
                break;
            default:
                Log.e("ERROR", "_b2_EnterId (1): public void onCreate(Bundle savedInstanceState)");
                break;
        }

        Intent intent = new Intent(_Variables.CONTEXT.get(), _02_Nfc.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(_Variables.CONTEXT.get(), _01_Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
