package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.io.File;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class _Variables {

    private static final String file_name_reference =                  "Safety";
    private static final int compilerMode =                             ((BuildConfig.FLAVOR == "Wireless") ? 1 : ((BuildConfig.FLAVOR == "ConfigEquip") ? 2 :
                                                                        ((BuildConfig.FLAVOR == "UseEquip") ? 3 : ((BuildConfig.FLAVOR == "ManageRental") ? 4 : 0))));

    public static final AtomicReference<String>                         VERSION = new AtomicReference<>("" + BuildConfig.VERSION_NAME);
    public static final AtomicReference<File>                           FILE = new AtomicReference<>();
    public static final AtomicReference<Integer>                        DL_STATUS = new AtomicReference<>(-1);
    public static final AtomicReference<Boolean>                        FILE_READ = new AtomicReference<>(false);
    public static final AtomicReference<String>                         FILE_NAME = new AtomicReference<>("p1_safety");
    public static final AtomicReference<JSONObject>                     DL_JSON_OBJ = new AtomicReference<>();
    public static final AtomicReference<Long>                           EPOCH_VERSION = new AtomicReference<>();
    public static final AtomicReference<ArrayList<Company>>             COMPANIES = new AtomicReference<>(new ArrayList<Company>());

    public static final AtomicReference<String>                         _FILE_NAME_REF = new AtomicReference<>(file_name_reference);
    public static final AtomicReference<Long>                           _FILE_EPOCH = new AtomicReference<>();
    public static final AtomicReference<ArrayList<String>>              COMPANY = new AtomicReference<>(new ArrayList<String>());
    public static final AtomicReference<ArrayList<String>>              TYPE = new AtomicReference<>(new ArrayList<String>());
    public static final AtomicReference<ArrayList<String>>              SUBTYPE = new AtomicReference<>(new ArrayList<String>());
    public static final AtomicReference<ArrayList<String>>              PRODUCT = new AtomicReference<>(new ArrayList<String>());
    public static final AtomicReference<ArrayList<Integer>>             UNIT_ID = new AtomicReference<>(new ArrayList<Integer>());

    public static final AtomicReference<ArrayList<Integer>>             QUES_COUNT = new AtomicReference<>(new ArrayList<Integer>());
    public static final AtomicReference<ArrayList<Boolean>>             NFC_ENABLED = new AtomicReference<>(new ArrayList<Boolean>());
    public static final AtomicReference<ArrayList<ArrayList<String>>>   QUESTIONS = new AtomicReference<>(new ArrayList<ArrayList<String>>());
    public static final AtomicReference<ArrayList<ArrayList<Integer>>>  NFC_EN_INDEXES = new AtomicReference<>(new ArrayList<ArrayList<Integer>>());
    public static final AtomicReference<ArrayList<ArrayList<Integer>>>  NFC_PER_INDEX = new AtomicReference<>(new ArrayList<ArrayList<Integer>>());
    public static final AtomicReference<ArrayList<ArrayList<String>>>   NFC_LABELS = new AtomicReference<>(new ArrayList<ArrayList<String>>());
    public static final AtomicReference<ArrayList<ArrayList<String>>>   NFC_LOCATIONS = new AtomicReference<>(new ArrayList<ArrayList<String>>());
    public static final AtomicReference<ArrayList<ArrayList<String>>>   NFC_NAMES = new AtomicReference<>(new ArrayList<ArrayList<String>>());
    public static final AtomicReference<ArrayList<ArrayList<Integer>>>  NFC_IDS = new AtomicReference<>(new ArrayList<ArrayList<Integer>>());
    public static final AtomicReference<ArrayList<String>>              NFC_STND_QUES = new AtomicReference<>(new ArrayList<String>());

    public static final AtomicReference<SharedPreferences>              SETTINGS = new AtomicReference<>();
    public static final AtomicReference<Context>                        CONTEXT = new AtomicReference<>(null);
    public static final AtomicReference<Boolean>                        FIRST_TIME = new AtomicReference<>(false);
    public static final AtomicReference<String>                         JSON_MD5 = new AtomicReference<>("");
    public static final AtomicReference<String>                         PREFS_NAME = new AtomicReference<>("preferences001");
    public static final AtomicReference<String>                         PREFS_FIRST = new AtomicReference<>("FirstTime");
    public static final AtomicReference<String>                         PREFS_JSON = new AtomicReference<>("JsonHash");

    public static final AtomicReference<Integer>                          OPERATOR_ID = new AtomicReference<>();
    public static final AtomicReference<Integer>                        EQUIPMENT_ID_INDEX = new AtomicReference<>();
    public static final AtomicReference<Boolean>                        USE_EQUIP_MODE = new AtomicReference<>(false);

    public static final AtomicReference<Integer>                          CUSTOMER_ID = new AtomicReference<>();
    public static final AtomicReference<Boolean>                        MANAGE_RENTAL_MODE = new AtomicReference<>(false);

    public static final AtomicReference<Integer>                          COMPILER_MODE = new AtomicReference<>(compilerMode);
    public static final AtomicReference<CookieManager>                  COOKIE_MONSTER = new AtomicReference<>();

    public _Variables() {}

    public int getIntFromTwoBytes(byte low, byte high) {
        return ((((high & 0x00ff) << 8) | ((low & 0x00ff))));
    }

    public int getIntFromFourBytes(byte lowLow, byte lowHigh, byte highLow, byte highHigh) {
        return (((highHigh & 0x00ff) << 24) | ((highLow & 0x00ff) << 16) | ((lowHigh & 0x00ff) << 8) | ((lowLow & 0x00ff)));
    }
}
