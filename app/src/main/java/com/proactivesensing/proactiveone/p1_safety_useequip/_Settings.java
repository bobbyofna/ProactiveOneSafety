package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.util.Log;

public class _Settings extends Thread {

    private int MODE = 0;

    public _Settings(boolean threaded, int mode) {
        MODE = mode;
        if (!threaded)
            process();
    }

    public _Settings(boolean threaded) {
        if (!threaded)
            process();
        else
            MODE = 1;
    }

    @Override
    public void run() {
        process();
    }

    public void process() {
        final int tries = 1000;

        for (int i = 0; i < tries; i++) {
            try {
                _Variables.SETTINGS.set(_Variables.CONTEXT.get().getSharedPreferences(_Variables.PREFS_NAME.get(), 0));

                switch (MODE) {
                    case 0:
                        _Variables.FIRST_TIME.set(_Variables.SETTINGS.get().getBoolean(_Variables.PREFS_FIRST.get(), false));
                        break;
                    case 1:
                        _Variables.SETTINGS.get().edit().putBoolean(_Variables.PREFS_FIRST.get(), _Variables.FIRST_TIME.get()).commit();
                        break;
                    case 2:
                        _Variables.JSON_MD5.set(_Variables.SETTINGS.get().getString(_Variables.PREFS_FIRST.get(), ""));
                        break;
                    case 3:
                        _Variables.SETTINGS.get().edit().putString(_Variables.PREFS_JSON.get(), _Variables.JSON_MD5.get()).commit();
                        break;
                    default:
                        break;
                }

                i = tries * tries;
                break;
            } catch (Exception e) { Log.e("ERROR", "" + printError(e)); }
        }
    }

    public String printError(Exception e) {
        String str = "\n";
        str = str + e.getMessage() + "\n";
        str = str + e.getStackTrace()[0].getFileName() + ":";
        str = str + e.getStackTrace()[0].getMethodName() + ":";
        str = str + e.getStackTrace()[0].getLineNumber();

        return str;
    }
}
