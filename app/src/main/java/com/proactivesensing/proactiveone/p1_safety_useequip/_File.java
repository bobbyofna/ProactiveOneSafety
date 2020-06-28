package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

//  Code will always download whatever Json file is available on bobby server
//  Then it will run an MD5 hash of the new file and the one currently stored (Using the Settings class)
//  If the MD5 hashes do not match, it saves the new JSON file and hash (Settings class) and overwrite the previous stuff

public class _File extends Thread {

    public _File() { _Variables.DL_STATUS.set(-1); }

    public _File(boolean threaded) {
        _Variables.DL_STATUS.set(-1);
        if(!threaded)
            _Variables.FILE_READ.set(process(false));
    }

    @Override
    public void run() {
        _Variables.FILE_READ.set(process(false));
    }

    public boolean process(boolean first) {
        try {
            Log.e("Debug", "Location 1");
            if (checkConnection()) {
                Log.e("Debug", "Location 2");

                _Variables.DL_STATUS.set(-1);
                ASyncDatabase aSync = new ASyncDatabase();
                aSync.execute();
                int nothing = 0;
                while(_Variables.DL_STATUS.get() != 1)
                    nothing++;

                JSONObject downloadedJson = _Variables.DL_JSON_OBJ.get();
                Log.e("DL JSON", "" + downloadedJson.toString());

                //writeToFile(downloadedJson);
                Log.e("Debug", "Location 3");
                if(!first) {
                    Log.e("Debug", "Location 4");
                    File file = getStoredFile();

                    Log.e("Debug", "Location 5");
                    if(file != null) {
                        Log.e("Debug", "Location 6");
                        JSONObject currentJson = parseFile(getStoredFile());

                        Log.e("Debug", "Comparing dbs");
                        if (!(compareJsonDatabases(downloadedJson, currentJson))) {
                            Log.e("Debug", "Saving downloaded db 1");
                            writeToFile(downloadedJson);
                            Log.e("Debug", "Succeeded writing to file 1");
                            return processJson(downloadedJson);

                        }
                        else {
                            Log.e("Debug", "Processing Stored Json");
                            return processJson(currentJson);
                        }
                    }
                    else {
                        Log.e("Debug", "Saving downloaded db 2");
                        writeToFile(downloadedJson);
                        Log.e("Debug", "Succeeded writing to file 2");
                        return processJson(downloadedJson);
                    }
                }
                else {
                    Log.e("Debug", "Saving downloaded db 3");
                    writeToFile(downloadedJson);
                    Log.e("Debug", "Succeeded writing to file 3");
                    return processJson(downloadedJson);
                }
            }
        } catch (Exception e) { Log.e("ERROR", "" + Log.getStackTraceString(e)); return false; }
        return false;
    }

    private boolean writeToFile(JSONObject jsonObject) {
        try {
            FileWriter file = new FileWriter("" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + _Variables.FILE_NAME.get() + ".json");
            file.write(jsonObject.toString());
            file.flush();
            file.close();

            File saved = new File("" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + _Variables.FILE_NAME.get() + ".json");
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(saved));
            _Variables.CONTEXT.get().sendBroadcast(intent);

            return true;
        } catch(IOException e) {
        } catch(Exception e) { /*Log.e("ERROR", printError(e));*/ }

        return false;
    }

    public boolean processJson(JSONObject main_obj) {
        try {
            _Variables.EPOCH_VERSION.set(main_obj.getLong("epoch"));
            JSONArray main_array = main_obj.getJSONArray("main_array");

            JSONObject obj;
            String company, type, subtype, model, nfc_stnd_ques = "";
            boolean nfc_enabled;
            int id;
            ArrayList<String> questions, nfc_labels, nfc_locations, nfc_names;
            ArrayList<Integer> nfc_en_indexes, nfc_per_index, nfc_ids;
            for (int i = 0; i < main_array.length(); i++) {
                obj = (JSONObject) main_array.get(i);

                company = "" + obj.getString("company");
                type = "" + obj.getString("type");
                subtype = "" + obj.getString("subtype");
                model = "" + obj.getString("product");
                id = obj.getInt("unit_id");

                questions = new ArrayList<>();
                JSONArray json_ques = obj.getJSONArray("questions");
                for (int k = 0; k < json_ques.length(); k++)
                    questions.add("" + json_ques.get(k));

                nfc_enabled = obj.getBoolean("nfc_enabled");

                nfc_en_indexes = new ArrayList<>();
                nfc_per_index = new ArrayList<>();
                nfc_labels = new ArrayList<>();
                nfc_locations = new ArrayList<>();
                nfc_names = new ArrayList<>();
                nfc_ids = new ArrayList<>();

                if (nfc_enabled) {
                    JSONArray json_nfc_en_indexes = obj.getJSONArray("nfc_en_indexes");
                    JSONArray json_nfc_per_index = obj.getJSONArray("nfc_per_index");
                    JSONArray json_nfc_labels = obj.getJSONArray("nfc_labels");
                    JSONArray json_nfc_locations = obj.getJSONArray("nfc_locations");
                    JSONArray json_nfc_names = obj.getJSONArray("nfc_names");
                    JSONArray json_nfc_ids = obj.getJSONArray("nfc_ids");

                    for (int k = 0; k < json_nfc_en_indexes.length(); k++)
                        nfc_en_indexes.add(json_nfc_en_indexes.getInt(k));
                    for (int k = 0; k < json_nfc_per_index.length(); k++)
                        nfc_per_index.add(json_nfc_per_index.getInt(k));
                    for (int k = 0; k < json_nfc_labels.length(); k++)
                        nfc_labels.add(json_nfc_labels.getString(k));
                    for (int k = 0; k < json_nfc_locations.length(); k++)
                        nfc_locations.add(json_nfc_locations.getString(k));
                    for (int k = 0; k < json_nfc_names.length(); k++)
                        nfc_names.add(json_nfc_names.getString(k));
                    for (int k = 0; k < json_nfc_ids.length(); k++)
                        nfc_ids.add(json_nfc_ids.getInt(k));

                    nfc_stnd_ques = "" + obj.getString("nfc_stnd_ques");
                }

                int index = checkForExistingCompany(company);
                if (index == -1) {
                    Company c = new Company();
                    c.setCompanyName(company);
                    _Variables.COMPANIES.get().add(c);
                    index = checkForExistingCompany(company);
                }
                Unit u = new Unit(model);
                Type t = new Type();
                Subtype s = new Subtype();
                t.setType(type);
                s.setSubtype(subtype);
                u.setType(t);
                u.setSubtype(s);
                u.setIdentifier(id);

                ArrayList<String> temp_labels, temp_locations, temp_names;
                ArrayList<Integer> temp_ids;
                int enabled_index = 0, label_index = 0, location_index = 0, name_index = 0, id_index = 0;
                for (int k = 0; k < questions.size(); k++) {
                    if (nfc_enabled) {
                        if ((enabled_index < nfc_en_indexes.size()) && (k == nfc_en_indexes.get(enabled_index))) {
                            temp_labels = new ArrayList<>();
                            temp_locations = new ArrayList<>();
                            temp_names = new ArrayList<>();
                            temp_ids = new ArrayList<>();
                            for (int h = 0; h < nfc_per_index.get(enabled_index); h++) {
                                temp_labels.add(nfc_labels.get(label_index++));
                                temp_locations.add(nfc_locations.get(location_index++));
                                temp_names.add(nfc_names.get(name_index++));
                                temp_ids.add(nfc_ids.get(id_index++));
                            }
                            u.addQuestion(questions.get(k), temp_labels.size(), temp_labels, temp_locations, temp_names, temp_ids);
                            enabled_index++;
                        } else
                            u.addQuestion(questions.get(k));
                    } else
                        u.addQuestion(questions.get(k));
                }

                if (nfc_enabled)
                    u.setStandardNfcQuestion(nfc_stnd_ques);

                _Variables.COMPANIES.get().get(index).addModel(u);
            }
            return true;
        } catch(JSONException e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e));
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    public boolean compareJsonDatabases(JSONObject newJson, JSONObject currJson) {
        try {
            String newEpochVersion = "" + newJson.getLong("epoch");
            String currEpochVersion = "" + currJson.getLong("epoch");

            Log.e("Epoch1", "Curr:  " + currEpochVersion);
            Log.e("Epoch2", "New:   " + newEpochVersion);

            return (newEpochVersion.equals(currEpochVersion));
        }
        catch(JSONException e) {}
        //catch(Exception e) {}

        return false;
        //return true;
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

    public File getStoredFile() {
        ArrayList<File> fileList;

        try {
            fileList = new ArrayList<>(Arrays.asList(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).listFiles()));
            if(fileList.size() == 0)
                return null;
        } catch(Exception e) { Log.e("Exception!", "Error occurred while trying to retrieve file...\n" + (new Utils()).printError(e)); return null; }

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
            //try {
            if((("" + fileList.get(i).getName().substring(fileList.get(i).getName().lastIndexOf('.'))).toUpperCase()).equals((".json").toUpperCase())) {
                String fileNameStart = "" + fileList.get(i).getName().substring(0, fileList.get(i).getName().lastIndexOf('.'));

                if(fileNameStart.contains(_Variables.FILE_NAME.get()))
                    return fileList.get(i);
            }
            //} catch(Exception e) {  }
        }

        Log.e("Error", "Could not find file?");
        return null;
    }

    public int checkForExistingCompany(String company_name) {
        try {
            for (int i = 0; i < _Variables.COMPANIES.get().size(); i++)
                if (_Variables.COMPANIES.get().get(i).getCompanyName().toLowerCase().equals(company_name.toLowerCase()))
                    return i;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return -1;
    }

    public String printJson(String json) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(json);
            return gson.toJson(je);
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return null;
    }

    public boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) _Variables.CONTEXT.get().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected1 = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED);
        boolean connected2 = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        return (connected1 || connected2);
        //return false;
    }

    private static class ASyncDatabase extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                _Variables.DL_STATUS.set(0);
                URL url = new URL("http://52.188.145.23:5000/safety");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoInput(true);
                conn.setConnectTimeout(15000);
                conn.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line + "\n");                }
                br.close();

                String filteredResult = result.toString().replaceAll("'", "\"");
                filteredResult = result.toString().replaceAll("\'", "\"");

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Getting Latest Safety App DB\n");
                stringBuilder.append("Url:  " + url.toString() + "\n");
                stringBuilder.append("Code: " + conn.getResponseCode() + "\n");
                stringBuilder.append("Msg:  " + conn.getResponseMessage() + "\n");

                conn.disconnect();

                Log.e("RESULT", "" + filteredResult);
                _Variables.DL_JSON_OBJ.set(new JSONObject(filteredResult));
                _Variables.DL_STATUS.set(1);
            }
            catch (java.net.SocketTimeoutException e) { Log.e("ERROR", "" + (new Utils()).printError(e)); _Variables.DL_STATUS.set(-100); }
            catch (MalformedURLException e) { Log.e("ERROR", "" + (new Utils()).printError(e)); }
            catch (IOException e) { Log.e("ERROR", "" + (new Utils()).printError(e)); }
            catch (Exception e) { Log.e("ERROR", "" + (new Utils()).printError(e)); }

            return null;
        }
    }
}
