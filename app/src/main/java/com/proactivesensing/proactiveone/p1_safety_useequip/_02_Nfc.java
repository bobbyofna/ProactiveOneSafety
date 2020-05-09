package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class _02_Nfc extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private TextView info;
    private String toSend;
    private boolean sent = false;
    screenControl sc;

    View[] viewToInflate = new View[3];
    LinearLayout[] layoutToAdd = new LinearLayout[2];
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._nfc_v2);

        _Variables.CONTEXT.set(getApplicationContext());

        sc = new screenControl();
        layoutToAdd[0] = (LinearLayout) findViewById(R.id.layout_expansion_1);
        layoutToAdd[1] = (LinearLayout) findViewById(R.id.layout_expansion_2);

        String msg = "";
        switch(_Variables.COMPILER_MODE.get()) {
            case 1: //  WIRELESS
                break;
            case 2: //  CONFIGURE EQUIPMENT
                break;
            case 3: //  USE EQUIPMENT
                //((TextView) findViewById(R.id.note)).setText("Safety App:  Use Equipment");
                if(!_Variables.USE_EQUIP_MODE.get())
                    msg = "Tap The ProactiveOne To Send The Given Operator ID\n\nPull Phone Away When Prompted";
                else
                    msg = "Tap The ProactiveOne To Confirm Verification Of Unit\n\nPull Phone Away When Prompted";
                break;
            case 4: //  MANAGE RENTAL
                //((TextView) findViewById(R.id.note)).setText("Safety App:  Manage Rental");
                if(!_Variables.MANAGE_RENTAL_MODE.get())
                    msg = "Tap The ProactiveOne To Initiate The Rental\n\nPull Phone Away When Prompted";
                else
                    msg = "Tap The ProactiveOne To Terminate The Rental\n\nPull Phone Away When Prompted";
                break;
            default:
                Log.e("ERROR", "_02_Nfc (1): public void onCreate(Bundle savedInstanceState)");
                break;
        }
        sc.openNormalTextBox(msg);

        nfcAdapter = nfcAdapter.getDefaultAdapter(_Variables.CONTEXT.get());
    }

    public void next() {
        Intent intent = null;
        switch(_Variables.COMPILER_MODE.get()) {
            case 1: //  WIRELESS
                break;
            case 2: //  CONFIGURE EQUIPMENT
                break;
            case 3: //  USE EQUIPMENT
                if(!_Variables.USE_EQUIP_MODE.get()) {
                    intent = new Intent(this, _b4_GetEquipId.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                    sc.openNormalTextBox("Success!");
                break;
            case 4: //  MANAGE RENTAL
                sc.openNormalTextBox("Success!");
                break;
            default:
                Log.e("ERROR", "_02_Nfc (1): public void next()");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableForegroundDispatchSystem();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableForegroundDispatchSystem();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(!sent) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            NdefMessage ndefMessage = createNdefMessage();
            writeNdefMessage(tag, ndefMessage);
        }
    }
    
    private void enableForegroundDispatchSystem() {
        Intent intent = new Intent(_Variables.CONTEXT.get(), _02_Nfc.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(_Variables.CONTEXT.get(), 0, intent, 0);
        IntentFilter[] intentFilters = new IntentFilter[]{};
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    private void disableForegroundDispatchSystem() {
        nfcAdapter.disableForegroundDispatch(this);
    }

    private void formatTag(Tag tag, NdefMessage ndefMessage) {
        try {
            NdefFormatable ndefFormatable = NdefFormatable.get(tag);

            ndefFormatable.connect();
            ndefFormatable.format(ndefMessage);
            ndefFormatable.close();

            sc.openDialog(2, "Potential Success", "Please Pull Away Phone\n\nDid The ProactiveOne Beep?  (Might Take A Few Seconds)", "Yes", "No");
        } catch (Exception e) {
            Log.e("ERROR", "" + Log.getStackTraceString(e));
            sc.openDialog(0, "Exception", "Error(2) Ecountered While Attempting To Send Programming To The ProactiveOne Unit.  Would You Like To Try Again?", "Yes", "No");
        }
    }

    private void writeNdefMessage(Tag tag, NdefMessage ndefMessage) {
        if (tag == null)
            sc.openDialog(0, "Exception", "Error(1) Ecountered While Attempting To Send Programming To The ProactiveOne Unit.  Would You Like To Try Again?", "Yes", "No");
        else {
            Ndef ndef = Ndef.get(tag);

            if (ndef == null)
                formatTag(tag, ndefMessage);
            else {
                try {
                    ndef.connect();
                    ndef.writeNdefMessage(ndefMessage);
                    ndef.close();

                    sc.openDialog(2, "Potential Success", "Please Pull Away Phone\n\nDid The ProactiveOne Beep?  (Might Take A Few Seconds)", "Yes", "No");
                } catch (Exception e) {
                    Log.e("ERROR", "" + Log.getStackTraceString(e));
                    sc.openDialog(0, "Exception", "Error(3) Ecountered While Attempting To Send Programming To The ProactiveOne Unit.  Would You Like To Try Again?", "Yes", "No");
                }
            }
        }
    }

    private NdefRecord createRecord(short address, short value) {
        ByteArrayOutputStream payload = new ByteArrayOutputStream();

        payload.write('M');
        payload.write('1');
        payload.write(16);
        payload.write((byte) ((address >> 8) & 0x00ff));
        payload.write((byte) (address & 0x00ff));
        payload.write(0);
        payload.write(1);
        payload.write(2);
        payload.write((byte) ((value >> 8) & 0x00ff));
        payload.write((byte) (value & 0x00ff));

        return new NdefRecord(NdefRecord.TNF_MIME_MEDIA, NdefRecord.RTD_TEXT, null, payload.toByteArray());
    }

    private NdefMessage createNdefMessage() {
        ArrayList<NdefRecord> ndef = new ArrayList<>();

        switch(_Variables.COMPILER_MODE.get()) {
            case 1: //  WIRELESS
                break;
            case 2: //  CONFIGURE EQUIPMENT
                break;
            case 3: //  USE EQUIPMENT
                ndef.add(createRecord(((short) 2302), _Variables.OPERATOR_ID.get()));
                if(!_Variables.USE_EQUIP_MODE.get())
                    ndef.add(createRecord(((short) 2303), ((short) 1)));
                else
                    ndef.add(createRecord(((short) 2303), ((short) 2)));
                break;
            case 4: //  MANAGE RENTAL
                if(!_Variables.MANAGE_RENTAL_MODE.get()) {
                    ndef.add(createRecord(((short) 2300), _Variables.CUSTOMER_ID.get()));
                    ndef.add(createRecord(((short) 2301), ((short) 1)));
                }
                else
                    ndef.add(createRecord(((short) 2301), ((short) 2)));
                break;
            default:
                Log.e("ERROR", "_02_Nfc (1): public void createNdefMessage()");
                break;
        }

        NdefRecord[] ndefr = new NdefRecord[ndef.size()];
        for (short i = 0; i < ndef.size(); i++)
            ndefr[i] = ndef.get(i);
        NdefMessage ndefMessage = new NdefMessage(ndefr);

        return ndefMessage;
    }

    public void startHome() {
        Intent intent = new Intent(this, _01_Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void createDialog(int dNum, String title, String msg, String pos, String meh, String neg) {
        sent = true;

        DialogInterface.OnClickListener dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(!((title.equals("")) || (title.isEmpty()) || (title == "") || (title == null)))
            builder.setTitle("" + title);
        if(!((msg.equals("")) || (msg.isEmpty()) || (msg == "") || (msg == null)))
            builder.setMessage("" + msg);

        if(dNum == 0)
            dialog = dialog0;
        else if(dNum == 1)
            dialog = dialog1;
        else if(dNum == 2)
            dialog = dialog2;
        else
            dialog = dialog0;

        if(!((pos.equals("")) || (pos.isEmpty()) || (pos == "") || (pos == null)))
            builder.setPositiveButton(("" + pos), dialog);
        if(!((meh.equals("")) || (meh.isEmpty()) || (meh == "") || (meh == null)))
            builder.setNeutralButton(("" + meh), dialog);
        if(!((neg.equals("")) || (neg.isEmpty()) || (neg == "") || (neg == null)))
            builder.setNegativeButton(("" + neg), dialog);

        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        startHome();
    }

    private class screenControl {

        boolean NORM_TEXT_IS_VISIBLE = false;
        boolean ALERT_TEXT_IS_VISIBLE = false;
        boolean DIALOG_IS_VISIBLE = false;

        public screenControl() {}

        private void openNormalTextBox(String msg) {
            if(NORM_TEXT_IS_VISIBLE && ALERT_TEXT_IS_VISIBLE)
                Log.e("ERROR", "SOMEHOW CODE THINKS BOTH TEXT BOX THINGS ARE OPEN");
            else if(NORM_TEXT_IS_VISIBLE)
                ((TextView) findViewById(R.id.main_text)).setText(msg);
            else {
                if(ALERT_TEXT_IS_VISIBLE) {
                    layoutToAdd[0].removeView(viewToInflate[1]);
                    ALERT_TEXT_IS_VISIBLE = false;
                }

                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                viewToInflate[0] = inflater.inflate(R.layout._nfc_z_textdialog_norm_child, null);
                layoutToAdd[0].addView(viewToInflate[0]);

                ((TextView) findViewById(R.id.main_text)).setText(msg);
                NORM_TEXT_IS_VISIBLE = true;
            }
        }

        private void openAlertTextBox(String msg) {
            if(NORM_TEXT_IS_VISIBLE && ALERT_TEXT_IS_VISIBLE)
                Log.e("ERROR", "SOMEHOW CODE THINKS BOTH TEXT BOX THINGS ARE OPEN");
            else if(ALERT_TEXT_IS_VISIBLE)
                ((TextView) findViewById(R.id.main_text)).setText(msg);
            else {
                if(NORM_TEXT_IS_VISIBLE) {
                    layoutToAdd[0].removeView(viewToInflate[0]);
                    NORM_TEXT_IS_VISIBLE = false;
                }

                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                viewToInflate[1] = inflater.inflate(R.layout._nfc_z_textdialog_alert_child, null);
                layoutToAdd[0].addView(viewToInflate[1]);

                ((TextView) findViewById(R.id.main_text)).setText(msg);
                ALERT_TEXT_IS_VISIBLE = true;
            }
        }

        private void closeTextBox() {
            if(NORM_TEXT_IS_VISIBLE && ALERT_TEXT_IS_VISIBLE)
                Log.e("ERROR", "SOMEHOW CODE THINKS BOTH TEXT BOX THINGS ARE OPEN");
            else if(!NORM_TEXT_IS_VISIBLE && !ALERT_TEXT_IS_VISIBLE)
                Log.e("ERROR", "BOTH TEXT BOX THINGS ARE ALREADY CLOSED");
            else {
                if(NORM_TEXT_IS_VISIBLE) {
                    layoutToAdd[0].removeView(viewToInflate[0]);
                    NORM_TEXT_IS_VISIBLE = false;
                }
                else {
                    layoutToAdd[0].removeView(viewToInflate[1]);
                    ALERT_TEXT_IS_VISIBLE = false;
                }
            }
        }

        private void openDialog(int mode, String title, String msg, String posStr, String negStr) {
            if(DIALOG_IS_VISIBLE)
                Log.e("ERROR", "DIALOG IS ALREADY OPENED");
            else {
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                viewToInflate[2] = inflater.inflate(R.layout._nfc_z_popupdialog_child, null);
                layoutToAdd[1].addView(viewToInflate[2]);

                ((TextView) findViewById(R.id.title_text)).setText(title);
                ((TextView) findViewById(R.id.msg_text)).setText(msg);
                Button neg = findViewById(R.id.neg_button);
                Button pos = findViewById(R.id.pos_button);
                neg.setText(negStr);
                pos.setText(posStr);

                DIALOG_IS_VISIBLE = true;
                final int MODE = mode;

                pos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String msg = "";
                        switch(MODE) {
                            case 0:
                                sent = false;
                                switch(_Variables.COMPILER_MODE.get()) {
                                    case 1: //  WIRELESS
                                        break;
                                    case 2: //  CONFIGURE EQUIPMENT
                                        break;
                                    case 3: //  USE EQUIPMENT
                                        if(!_Variables.USE_EQUIP_MODE.get())
                                            msg = "Tap The ProactiveOne To Send The Given Operator ID\n\nPull Phone Away When Prompted";
                                        else
                                            msg = "Tap The ProactiveOne To Confirm Verification Of Unit\n\nPull Phone Away When Prompted";
                                        break;
                                    case 4: //  MANAGE RENTAL
                                        if(!_Variables.MANAGE_RENTAL_MODE.get())
                                            msg = "Tap The ProactiveOne To Initiate The Rental\n\nPull Phone Away When Prompted";
                                        else
                                            msg = "Tap The ProactiveOne To Terminate The Rental\n\nPull Phone Away When Prompted";
                                        break;
                                    default:
                                        Log.e("ERROR", "_02_Nfc (1): dialog0");
                                        break;
                                }
                                sc.openNormalTextBox(msg);
                                sc.closeDialog();
                                break;
                            case 1:
                                sc.closeDialog();
                                break;
                            case 2:
                                next();
                                sent = true;
                                sc.openNormalTextBox("Success!");
                                sc.closeDialog();
                                break;
                        }
                    }
                });

                neg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String msg = "";
                        switch(MODE) {
                            case 0:
                                onBackPressed();
                                break;
                            case 1:
                                startHome();
                                break;
                            case 2:
                                sent = false;
                                switch(_Variables.COMPILER_MODE.get()) {
                                    case 1: //  WIRELESS
                                        break;
                                    case 2: //  CONFIGURE EQUIPMENT
                                        break;
                                    case 3: //  USE EQUIPMENT
                                        if(!_Variables.USE_EQUIP_MODE.get())
                                            msg = "Tap The ProactiveOne To Send The Given Operator ID\n\nPull Phone Away When Prompted";
                                        else
                                            msg = "Tap The ProactiveOne To Confirm Verification Of Unit\n\nPull Phone Away When Prompted";
                                        break;
                                    case 4: //  MANAGE RENTAL
                                        if(!_Variables.MANAGE_RENTAL_MODE.get())
                                            msg = "Tap The ProactiveOne To Initiate The Rental\n\nPull Phone Away When Prompted";
                                        else
                                            msg = "Tap The ProactiveOne To Terminate The Rental\n\nPull Phone Away When Prompted";
                                        break;
                                    default:
                                        Log.e("ERROR", "_02_Nfc (1): dialog2");
                                        break;
                                }
                                sc.openNormalTextBox(msg);
                                sc.closeDialog();
                                break;
                        }
                    }
                });
            }
        }

        private void closeDialog() {
            if(DIALOG_IS_VISIBLE) {
                layoutToAdd[1].removeView(viewToInflate[2]);
                DIALOG_IS_VISIBLE = false;
            }
        }
    }



    DialogInterface.OnClickListener dialog0 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    onBackPressed();
                    break;
            }
        }
    };

    DialogInterface.OnClickListener dialog1 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    startHome();
                    break;
            }
        }
    };

    DialogInterface.OnClickListener dialog2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    next();
                    sent = true;
                    info.setText("Success!");
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    sent = false;
                    switch(_Variables.COMPILER_MODE.get()) {
                        case 1: //  WIRELESS
                            break;
                        case 2: //  CONFIGURE EQUIPMENT
                            break;
                        case 3: //  USE EQUIPMENT
                            if(!_Variables.USE_EQUIP_MODE.get())
                                info.setText("Tap The ProactiveOne To Send The Given Operator ID\n\n\nPull Phone Away When Prompted");
                            else
                                info.setText("Tap The ProactiveOne To Confirm Verification Of Unit\n\n\nPull Phone Away When Prompted");
                            break;
                        case 4: //  MANAGE RENTAL
                            if(!_Variables.MANAGE_RENTAL_MODE.get())
                                info.setText("Tap The ProactiveOne To Initiate The Rental\n\n\nPull Phone Away When Prompted");
                            else
                                info.setText("Tap The ProactiveOne To Terminate The Rental\n\n\nPull Phone Away When Prompted");
                            break;
                        default:
                            Log.e("ERROR", "_02_Nfc (1): dialog2");
                            break;
                    }
                    break;
            }
        }
    };
}
