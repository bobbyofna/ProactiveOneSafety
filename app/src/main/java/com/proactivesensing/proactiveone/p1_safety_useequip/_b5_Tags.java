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
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class _b5_Tags extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private TextView info;
    private int QUES_INDEX = -1, NFC_PER_QUES_INDEX = 0, TAG_PER_NFC_INDEX = 0, TAG_COUNT = 0, NFC_MODE = -1, NFC_INDEX = 0, CURR_NFC_SIZE = 0, QUES_SIZE;
    private boolean nfcEnabled = false;
    private String lingerStr = "";
    private ArrayList<Boolean> QUESTION_ANSWERED = new ArrayList<>();
    private ArrayList<Boolean> QUESTION_HAS_NFC = new ArrayList<>();
    private ArrayList<Integer> TAGS_PER_NFC = new ArrayList<>();
    private ArrayList<ArrayList<Boolean>> NFC_ANSWERED = new ArrayList<>();
    private boolean doingNfcRn = false;
    private ArrayList<ArrayList<String>> TAG_LABELS = new ArrayList<>();
    private ArrayList<ArrayList<String>> TAG_LOCATIONS = new ArrayList<>();
    private ArrayList<ArrayList<String>> TAG_NAMES = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._nfc);

        _Variables.CONTEXT.set(getApplicationContext());
        ((TextView) findViewById(R.id.version)).setText("v" + _Variables.VERSION.get());
        ((TextView) findViewById(R.id.note)).setText("Safety App:  Use Equipment");
        nfcAdapter = nfcAdapter.getDefaultAdapter(this);
        info = (TextView) findViewById(R.id.message);

        QUES_SIZE = _Variables.QUESTIONS.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).size();
        generateTrackingLists();

        QUES_INDEX = -1;
        TAG_PER_NFC_INDEX = 0;
        NFC_PER_QUES_INDEX = 0;
        TAG_COUNT = 0;
        NFC_MODE = 0;
        NFC_INDEX = -1;
        CURR_NFC_SIZE = 0;

        handleQuestions(true, -1);
    }

    public void generateTrackingLists() {
        int nfcIndex = 0;
        int count;
        boolean hasNfc;
        ArrayList<Boolean> temp1;
        ArrayList<String> temp2;
        ArrayList<String> temp3;
        ArrayList<String> temp4;


        for(int i = 0; i < QUES_SIZE; i++) {
            QUESTION_ANSWERED.add(false);
            hasNfc = false;

            for(int k = nfcIndex; k < _Variables.NFC_EN_INDEXES.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).size(); k++) {
                if(_Variables.NFC_EN_INDEXES.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get(k) == i) {
                    hasNfc = true;
                    break;
                }
            }
            QUESTION_HAS_NFC.add(hasNfc);
            
            temp1 = new ArrayList<>();
            temp2 = new ArrayList<>();
            temp3 = new ArrayList<>();
            temp4 = new ArrayList<>();
            count = 0;
            if(hasNfc) {
                for(int k = 0; k < _Variables.NFC_PER_INDEX.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get(nfcIndex); k++, count++) {
                    temp1.add(false);
                    temp2.add(_Variables.NFC_LABELS.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get(k));
                    temp3.add(_Variables.NFC_LOCATIONS.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get(k));
                    temp4.add(_Variables.NFC_NAMES.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get(k));
                }

                nfcIndex++;
            }
            NFC_ANSWERED.add(temp1);
            TAGS_PER_NFC.add(count);
            TAG_LABELS.add(temp2);
            TAG_LOCATIONS.add(temp3);
            TAG_NAMES.add(temp4);
        }
    }
    
    private void handleQuestions(boolean resume, int from) {
        if(resume) {
            //if(QUES_INDEX)
            //lingerStr = "Success!\n\n";

            if(doingNfcRn) {
                if(!QUESTION_ANSWERED.get(QUES_INDEX))
                    QUESTION_ANSWERED.set(QUES_INDEX, true);

                if((CURR_NFC_SIZE == 0) && (from == 3))
                    CURR_NFC_SIZE = TAGS_PER_NFC.get(QUES_INDEX);

                NFC_INDEX++;
                if(NFC_INDEX < CURR_NFC_SIZE)
                    doingNfcRn = true;
                else
                    doingNfcRn = false;

                //if()
                //NFC_ANSWERED.get(QUES_INDEX).set(NFC_INDEX, true);
            }

            if(!doingNfcRn) {
                if((QUES_INDEX > -1) && (from != -1))
                    QUESTION_ANSWERED.set(QUES_INDEX, true);
                QUES_INDEX++;

                if(QUES_INDEX < QUES_SIZE) {
                    NFC_INDEX = -1;
                    CURR_NFC_SIZE = 0;
                }
                else
                    next();
            }
        }
        else
            lingerStr = "Error, Please Try Again...\n\n";

        if(!(QUES_INDEX < QUES_SIZE))
            next();
        else {
            if (!(QUESTION_HAS_NFC.get(QUES_INDEX))) {
                doingNfcRn = false;
                nfcEnabled = false;
                createDialog(2, ("Safety Inspection (" + (QUES_INDEX + 1) + "/" + QUES_SIZE + ")"), ("" + lingerStr + _Variables.QUESTIONS.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get(QUES_INDEX)), "Confirm", "", "Cancel");
            } else {
                //CURR_NFC_SIZE = _Variables.NFC_PER_INDEX.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get()

                doingNfcRn = true;
                if (!QUESTION_ANSWERED.get(QUES_INDEX)) {
                    nfcEnabled = false;
                    createDialog(3, ("Safety Inspection (" + (QUES_INDEX + 1) + "/" + QUES_SIZE + ")"), ("" + lingerStr + _Variables.QUESTIONS.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get(QUES_INDEX)), "Confirm", "", "Cancel");
                } else {
                    nfcEnabled = true;
                    info.setText("" + lingerStr + (("" + makeQuestion().replace("[", "")).replace("]", "")));
                }
            }
        }
    }

    DialogInterface.OnClickListener dialog2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    handleQuestions(true, 2);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    onBackPressed();
                    break;
            }
        }
    };

    DialogInterface.OnClickListener dialog3 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    handleQuestions(true, 3);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    onBackPressed();
                    break;
            }
        }
    };

    public void createDialog(int dNum, String title, String msg, String pos, String meh, String neg) {
        DialogInterface.OnClickListener dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        info.setText("      \n      ");
        nfcEnabled = false;

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
        else if(dNum == 3)
            dialog = dialog3;
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

    private String makeQuestion() {
        String stnd = "" + _Variables.NFC_STND_QUES.get().get(_Variables.EQUIPMENT_ID_INDEX.get());
        String aaa = "" + TAG_LABELS.get(QUES_INDEX).get(NFC_INDEX);
        String bbb = "" + TAG_LOCATIONS.get(QUES_INDEX).get(NFC_INDEX);
        String ccc = "" + TAG_NAMES.get(QUES_INDEX).get(NFC_INDEX);

        try {
            stnd = stnd.replaceAll("aaa", aaa);
            stnd = stnd.replaceAll("bbb", bbb);
            stnd = stnd.replaceAll("ccc", ccc);
            stnd = stnd.replaceAll("And Located", "Located");

//            for(int i = 0; i < 10; i++) {
//                stnd = stnd.replace("[", "");
//                stnd = stnd.replace("]", "");
//            }
        } catch(Exception e) { return ""; }

        return stnd;
    }

    private int nfcIndexCheck(int index) {
        try {
            for (int i = 0; i < _Variables.NFC_EN_INDEXES.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).size(); i++)
                if(index == _Variables.NFC_EN_INDEXES.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get(i))
                    return i;
        } catch(Exception e) { return -1; }

        return -1;
    }

    public boolean checkTag(int addUnit, int valueUnit, int addTag, int valueTag) {
        //if((addUnit == 10000) && (addTag == 20000)) {
        //    if((_Variables.ID.get().get(_Variables.EQUIPMENT_ID_INDEX.get()) == valueUnit) &&  ((_Variables.TAG_IDS.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get(INDEX)) == valueTag))
        //        return true;
        //}
        //Log.e("ERROR", "Could Not Find Matching Tag");
        return true;
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
        if(nfcEnabled) {
            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            readTextFromMessage((NdefMessage) parcelables[0]);
        }
    }

    private void enableForegroundDispatchSystem() {
        Intent intent = new Intent(this, _b5_Tags.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilters = new IntentFilter[] {};
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    private void disableForegroundDispatchSystem() {
        nfcAdapter.disableForegroundDispatch(this);
    }

    private void readTextFromMessage(NdefMessage ndefMessage) {
        try {
            NdefRecord[] ndefRecords = ndefMessage.getRecords();

            int addUnit = getAddFromNdefRecordFirst(ndefRecords[0]);
            int valUnit = getValFromNdefRecordFirst(ndefRecords[0]);
            int addTag = getAddFromNdefRecordLast(ndefRecords[1]);
            int valTag = getValFromNdefRecordLast(ndefRecords[1]);

            handleQuestions(checkTag(addUnit, valUnit, addTag, valTag), 4);

            /*if(checkTag(addUnit, valUnit, addTag, valTag)) {
                if((QUES_INDEX + 1) < TAG_COUNT) {

                    //createDialog(1, "Success!", ("For The Nest Inspection, " + _Variables.TAG_QUESTIONS.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get(INDEX)), "Yes", "", "No");
                }
                else
                    nextActivity();
            }
            else {
                //  TODO:   HAVE THE USER TRY AGAIN IF IT WAS INCORRECT
            }*/
        } catch(Exception e) { createDialog(0, "Error", "Issue Encountered While Attempting To Read Tag.  Would You Like To Try Again?", "Yes", "", "No"); }
    }

    public int getAddFromNdefRecordFirst(NdefRecord ndefRecord) {
        try {
            byte[] payload = ndefRecord.getPayload();

            return (new _Variables()).getIntFromTwoBytes(payload[4], payload[3]);
        } catch (Exception e) {}
        return (-1);
    }

    public int getValFromNdefRecordFirst(NdefRecord ndefRecord) {
        try {
            byte[] payload = ndefRecord.getPayload();

            return (new _Variables()).getIntFromTwoBytes(payload[9], payload[8]);
        } catch (Exception e) {}
        return (-1);
    }

    public int getAddFromNdefRecordLast(NdefRecord ndefRecord) {
        try {
            byte[] payload = ndefRecord.getPayload();

            return (new _Variables()).getIntFromTwoBytes(payload[4], payload[3]);
        } catch (Exception e) {}
        return (-1);
    }

    public int getValFromNdefRecordLast(NdefRecord ndefRecord) {
        try {
            byte[] payload = ndefRecord.getPayload();

            return (new _Variables()).getIntFromFourBytes(payload[11], payload[10], payload[9], payload[8]);
        } catch (Exception e) {}
        return -1;
    }

    DialogInterface.OnClickListener dialog0 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    info.setText("Tap Tag Again");
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
                    QUES_INDEX++;
                    //info.setText("Success!\nNow Tap The " + _Variables.TAG_NAMES.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get(INDEX) + " Once Inspected...");
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    onBackPressed();
                    break;
            }
        }
    };

    private void handleQuestions2(boolean resume) {
        if(resume) {
            QUES_INDEX++;
            //if(((NFC_MODE == 1) || (NFC_MODE == 2)) && ((TAG_PER_NFC_INDEX + 1) < _Variables.))
             //   NFC_MODE = 0;
        }
        if((NFC_MODE == -1) || (NFC_MODE == 0)) {
            if(_Variables.NFC_EN_INDEXES.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get(NFC_PER_QUES_INDEX) != QUES_INDEX) {
                NFC_MODE = 0;
                createDialog(2, "Safety Inspection", ("" + lingerStr + _Variables.QUESTIONS.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get(QUES_INDEX)), "Confirm", "", "Cancel");
            }
            else {
                NFC_MODE = 1;
                createDialog(2, "Safety Inspection", ("" + lingerStr + _Variables.QUESTIONS.get().get(_Variables.EQUIPMENT_ID_INDEX.get()).get(QUES_INDEX)), "Confirm", "", "Cancel");
            }
        }
        else if((NFC_MODE == 1) || (NFC_MODE == 2)) {

            if(NFC_MODE == 1)
                lingerStr = "";
            else if((NFC_MODE == 2) && (resume))
                lingerStr = "Success!\n\n";
            else if((NFC_MODE == 2) && (!resume))
                lingerStr = "Error, Please Try Again...\n\n";
            else
                lingerStr = "";

            NFC_MODE = 2;

            info.setText("" + lingerStr + (("" + makeQuestion().replace("[", "")).replace("]", "")));
            nfcEnabled = true;


        }
        else {

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, _01_Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void next() {
        Intent intent = new Intent(this, _b6_Sign.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
