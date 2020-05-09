package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class _b4_GetEquipId extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._nfc);

        _Variables.CONTEXT.set(getApplicationContext());

        ((TextView) findViewById(R.id.version)).setText("v" + _Variables.VERSION.get());
        ((TextView) findViewById(R.id.note)).setText("Safety App:  Use Equipment");

        nfcAdapter = nfcAdapter.getDefaultAdapter(this);
        info = (TextView) findViewById(R.id.message);
        info.setText("Tap ProactiveOne To Get The Equipment ID");

    }

    public boolean findMatchingEquipType(short address, short value) {
        /*
        if(address == 10000) {
            for (int i = 0; i < _Variables.ID.get().size(); i++) {
                if (((int) _Variables.ID.get().get(i)) == ((int) value)) {
                    _Variables.EQUIPMENT_ID_INDEX.set(i);
                    return true;
                }
            }
        }
        else
            Log.e("ERROR", "Wrong Address:  " + address);

        info.setText("Equipment ID Read Does Not Match Any On Record\nPlease Try Again...\n\nTap ProactiveOne To Get The Equipment ID");
        return false;
        */
        _Variables.EQUIPMENT_ID_INDEX.set(0);
        return true;
    }

    public void createDialog(int dNum, String title, String msg, String pos, String meh, String neg) {
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
        else
            dialog = dialog0;

        if(!((pos.equals("")) || (pos.isEmpty()) || (pos == "") || (pos == null)))
            builder.setPositiveButton(("" + pos), dialog);
        if(!((meh.equals("")) || (meh.isEmpty()) || (meh == "") || (meh == null)))
            builder.setNeutralButton(("" + meh), dialog);
        if(!((neg.equals("")) || (neg.isEmpty()) || (neg == "") || (neg == null)))
            builder.setNegativeButton(("" + neg), dialog);

        builder.show();
    }

    DialogInterface.OnClickListener dialog0 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    info.setText("Tap ProactiveOne To Get The Equipment ID");
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
                    next();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    onBackPressed();
                    break;
            }
        }
    };

    public void next() {
        Intent intent = new Intent(this, _b5_Tags.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, _01_Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
        Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        readTextFromMessage((NdefMessage) parcelables[0]);
    }

    private void readTextFromMessage(NdefMessage ndefMessage) {
        try {
            NdefRecord[] ndefRecords = ndefMessage.getRecords();
            if(findMatchingEquipType(getDataFromNdefRecord(ndefRecords[0])[0], getDataFromNdefRecord(ndefRecords[0])[1]))
                createDialog(1, "Success", "Found Matching Equipment ID, Would You Like To Start The Safety Inspection?", "Yes", "", "Cancel");
            else {
                //  TODO:   CODE TO TRY TO READ AGAIN
            }
        } catch(Exception e) {
            createDialog(0, "Error", "Issue Encountered While Attempting To Read Data From The ProactiveOne.  Would You Like To Try Again?", "Yes", "", "No");
        }
    }

    public short[] getDataFromNdefRecord(NdefRecord ndefRecord) throws Exception {
        try {
            byte[] payload = ndefRecord.getPayload();
            return new short[]{(new _Variables()).getShortFromTwoBytes(payload[4], payload[3]), (new _Variables()).getShortFromTwoBytes(payload[9], payload[8])};
        } catch (Exception e) {}
        throw null;
    }

    private void enableForegroundDispatchSystem() {
        Intent intent = new Intent(this, _b4_GetEquipId.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilters = new IntentFilter[] {};
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    private void disableForegroundDispatchSystem() {
        nfcAdapter.disableForegroundDispatch(this);
    }
}
