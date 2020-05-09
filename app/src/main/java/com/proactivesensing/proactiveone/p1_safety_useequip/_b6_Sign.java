package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class _b6_Sign extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_03___sign);

        _Variables.CONTEXT.set(getApplicationContext());
        ((TextView) findViewById(R.id.note)).setText("Safety App:  Use Equipment");
        ((TextView) findViewById(R.id.version)).setText("v" + _Variables.VERSION.get());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void sign(View view) {
        String input = "" + ((EditText) findViewById(R.id.edittext_1)).getText();
        if(input.length() > 0) {
            _Variables.USE_EQUIP_MODE.set(true);
            Intent intent = new Intent(_Variables.CONTEXT.get(), _02_Nfc.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else
            Toast.makeText(_Variables.CONTEXT.get(), "Input Too Short, Please Try Again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(_Variables.CONTEXT.get(), _01_Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}