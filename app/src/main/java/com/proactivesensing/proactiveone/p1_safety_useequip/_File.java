package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

//  Code will always download whatever Json file is available on FTP server
//  Then it will run an MD5 hash of the new file and the one currently stored (Using the Settings class)
//  If the MD5 hashes do not match, it saves the new JSON file and hash (Settings class) and overwrite the previous stuff

public class _File extends Thread {

    public _File(boolean threaded) {
        if(!threaded)
            process(false);  //  TODO:   CHANGE THIS TO TRUE ONCE THE FIRST TIME STUFF IS DONE
    }

    @Override
    public void run() {
        process(false);
        //(new _01_Home()).setButtonColorBackToNormal();
    }

    public void process(boolean first) {
        try {
            if(!first) {
                if (checkConnection()) {
                    if (checkForNewerFile())
                        saveNewFile();

                    parseJson(parseFile(getStoredFile()));
                    Toast.makeText(_Variables.CONTEXT.get(), "Database Has Been Updated", Toast.LENGTH_SHORT).show();
                }
                else
                    parseJson(parseFile(getStoredFile()));
            }
            /*else {
                if (checkConnection()) {
                    saveNewFile();
                }
                else {
                    //  TODO:   CHECK TO SEE IF A COMPATIBLE JSON FILE ALREADY EXISTS IN THE
                    //          DOWNLOADS FOLDER.  IF NOT THEN GET A DEFAULT JSON FILE TO BE
                    //          STORED SOMEWHERE IN THE APP WHICH WILL THEN BE SAVED IN THE
                    //          DOWNLOADS FOLDER TO HENCE PROCEED AS PER USUAL
                }

                parseJson(parseFile(getStoredFile()));
            }*/
        } catch (Exception e) { Log.e("ERROR", "" + Log.getStackTraceString(e)); }
    }

    public JSONObject parseFile(File file) {
        try {
			FileInputStream stream = new FileInputStream(file);
			FileChannel fc = stream.getChannel();
			MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

			String jsonStr = "" + Charset.defaultCharset().decode(mbb).toString();
			stream.close();


			return new JSONObject(jsonStr);
		} catch(FileNotFoundException e) { Log.e("ERROR", "Something (4): " + e.toString());
        } catch(JSONException e) { Log.e("ERROR", "Something (5): " + e.toString());
        } catch(IOException e) { Log.e("ERROR", "Something (6): " + e.toString());
        } catch(Exception e) {}

		return null;
    }

    public void parseJson(JSONObject mainObj) throws Exception {
        _Variables._FILE_VERSION_1.set(mainObj.getInt("version1"));
        _Variables._FILE_VERSION_2.set(mainObj.getInt("version2"));
        _Variables._FILE_VERSION_3.set(mainObj.getInt("version3"));
        _Variables._FILE_YEAR_LAST_MOD.set(mainObj.getInt("year_last_mod"));
        _Variables._FILE_MONTH_LAST_MOD.set(mainObj.getInt("month_last_mod"));
        _Variables._FILE_DAY_LAST_MOD.set(mainObj.getInt("day_last_mod"));
        _Variables._FILE_HOUR_LAST_MOD.set(mainObj.getInt("hour_last_mod"));
        _Variables._FILE_MIN_LAST_MOD.set(mainObj.getInt("min_last_mod"));

        JSONArray arr = (JSONArray) mainObj.get("main_array");
        ArrayList<JSONArray> unit = new ArrayList<>();

        boolean nfcEnabled;
        for (int i = 0; i < arr.length(); i++) {
            unit.clear();
            unit = new ArrayList<>();
            nfcEnabled = true;

            JSONObject obj = (JSONObject) arr.get(i);

            _Variables.COMPANY.get().add("" + obj.get("company").toString());
            _Variables.TYPE.get().add("" + obj.get("type").toString());
            _Variables.SUBTYPE.get().add("" + obj.get("subtype").toString());
            _Variables.MODEL.get().add("" + obj.get("product").toString());
            _Variables.ID.get().add((short) obj.getInt("unit_id"));

            _Variables.QUES_COUNT.get().add(obj.getInt("ques_count"));
            unit.add((JSONArray) obj.get("questions"));

            try {
                unit.add((JSONArray) obj.get("nfc_en_indexes"));
                unit.add((JSONArray) obj.get("nfc_per_index"));
                unit.add((JSONArray) obj.get("nfc_labels"));
                unit.add((JSONArray) obj.get("nfc_locations"));
                unit.add((JSONArray) obj.get("nfc_names"));
                unit.add((JSONArray) obj.get("nfc_ids"));
                _Variables.NFC_STND_QUES.get().add("" + obj.get("nfc_stnd_ques").toString());
            } catch(Exception e) { nfcEnabled = false; }

            ArrayList<String>  arr0 = new ArrayList<>();
            ArrayList<Integer> arr1 = new ArrayList<>();
            ArrayList<Integer> arr2 = new ArrayList<>();
            ArrayList<String>  arr3 = new ArrayList<>();
            ArrayList<String>  arr4 = new ArrayList<>();
            ArrayList<String>  arr5 = new ArrayList<>();
            ArrayList<Integer> arr6 = new ArrayList<>();

            for(int k = 0; k < unit.size(); k++) {
                for (int h = 0; h < unit.get(k).length(); h++) {
                    if(k == 0)                        { arr0.add("" + unit.get(k).get(h).toString()); }
                    else if((k == 1) && (nfcEnabled)) { arr1.add(unit.get(k).getInt(h)); }
                    else if((k == 2) && (nfcEnabled)) { arr2.add(unit.get(k).getInt(h)); }
                    else if((k == 3) && (nfcEnabled)) { arr3.add("" + unit.get(k).get(h).toString()); }
                    else if((k == 4) && (nfcEnabled)) { arr4.add("" + unit.get(k).get(h).toString()); }
                    else if((k == 5) && (nfcEnabled)) { arr5.add("" + unit.get(k).get(h).toString()); }
                    else if((k == 6) && (nfcEnabled)) { arr6.add(unit.get(k).getInt(h)); }
                }

                _Variables.QUESTIONS.get().add(arr0);
                _Variables.NFC_ENABLED.get().add(nfcEnabled);
                _Variables.NFC_EN_INDEXES.get().add(arr1);
                _Variables.NFC_PER_INDEX.get().add(arr2);
                _Variables.NFC_LABELS.get().add(arr3);
                _Variables.NFC_LOCATIONS.get().add(arr4);
                _Variables.NFC_NAMES.get().add(arr5);
                _Variables.NFC_IDS.get().add(arr6);
            }
        }
    }

    public boolean checkForNewerFile() {
        File stored, downloaded;

        try {
            stored = getStoredFile();
            downloaded = getFtpFile();
        } catch(Exception e) {}



        return false;
    }

    public File getStoredFile() throws Exception {
        ArrayList<File> fileList;

        try {
            fileList = new ArrayList<>(Arrays.asList(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).listFiles()));
            if(fileList.size() == 0)
                throw new Exception("Error Retrieveing Database File (1)");
        } catch(Exception e) { throw new Exception("Error Retrieveing Database File (2)"); }

        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                long k = file1.lastModified() - file2.lastModified();
                if (k > 0)
                    return -1;
                else if (k < 0)
                    return 1;
                else
                    return 0;
            }
        });

        for(int i = 0; i < fileList.size(); i++) {
            try {
                if((("" + fileList.get(i).getName().substring(fileList.get(i).getName().lastIndexOf('.'))).toUpperCase()).equals((".json").toUpperCase())) {
                    String fileNameStart = "" + fileList.get(i).getName().substring(0, fileList.get(i).getName().lastIndexOf('.'));

                    if(fileNameStart.contains(_Variables._FILE_NAME_REF.get()))
                        return fileList.get(i);
                }
            } catch(Exception e) {}
        }

        throw new Exception("Error Retrieveing Database File (3)");
    }

    public File getFtpFile() throws Exception {
        FTPClient ftp = new FTPClient();

        try {
            ftp.connect("192.168.1.97");
            ftp.login("anonymous", "nobody");
            ftp.enterLocalPassiveMode();
            ftp.changeWorkingDirectory("/files");

            InputStream is = ftp.retrieveFileStream("Safety.json");
            InputStreamReader isr = new InputStreamReader(is, "UTF8");
            //File file =

        } catch(Exception e) {}


        return null;
    }

    public void saveNewFile() throws Exception {

        //throw new Exception("Error Saving File To Phone");
    }

    public boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) _Variables.CONTEXT.get().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected1 = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED);
        boolean connected2 = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        //return (connected1 || connected2);
        return false;
    }
}
