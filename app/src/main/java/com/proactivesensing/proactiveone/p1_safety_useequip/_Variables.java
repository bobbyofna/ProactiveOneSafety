package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class _Variables {

    private static final String file_name_reference =                  "Safety";
    private static final short compilerMode =                           ((BuildConfig.FLAVOR == "Wireless") ? 1 : ((BuildConfig.FLAVOR == "ConfigEquip") ? 2 :
                                                                        ((BuildConfig.FLAVOR == "UseEquip") ? 3 : ((BuildConfig.FLAVOR == "ManageRental") ? 4 : 0))));

    public static final AtomicReference<String>                         VERSION = new AtomicReference<>("" + BuildConfig.VERSION_NAME);

    public static final AtomicReference<File>                           FILE = new AtomicReference<>();

    public static final AtomicReference<String>                         _FILE_NAME_REF = new AtomicReference<>(file_name_reference);
    public static final AtomicReference<String>                         _FILE_NAME = new AtomicReference<>();
    public static final AtomicReference<String>                         _FILE_LAST_MOD = new AtomicReference<>();
    public static final AtomicReference<Integer>                        _FILE_VERSION_1 = new AtomicReference<>();
    public static final AtomicReference<Integer>                        _FILE_VERSION_2 = new AtomicReference<>();
    public static final AtomicReference<Integer>                        _FILE_VERSION_3 = new AtomicReference<>();
    public static final AtomicReference<Integer>                        _FILE_YEAR_LAST_MOD = new AtomicReference<>();
    public static final AtomicReference<Integer>                        _FILE_MONTH_LAST_MOD = new AtomicReference<>();
    public static final AtomicReference<Integer>                        _FILE_DAY_LAST_MOD = new AtomicReference<>();
    public static final AtomicReference<Integer>                        _FILE_HOUR_LAST_MOD = new AtomicReference<>();
    public static final AtomicReference<Integer>                        _FILE_MIN_LAST_MOD = new AtomicReference<>();

    public static final AtomicReference<ArrayList<String>>              COMPANY = new AtomicReference<>(new ArrayList<String>());
    public static final AtomicReference<ArrayList<String>>              TYPE = new AtomicReference<>(new ArrayList<String>());
    public static final AtomicReference<ArrayList<String>>              SUBTYPE = new AtomicReference<>(new ArrayList<String>());
    public static final AtomicReference<ArrayList<String>>              MODEL = new AtomicReference<>(new ArrayList<String>());
    public static final AtomicReference<ArrayList<Short>>               ID = new AtomicReference<>(new ArrayList<Short>());

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

    public static final AtomicReference<Short>                          OPERATOR_ID = new AtomicReference<>();
    public static final AtomicReference<Integer>                        EQUIPMENT_ID_INDEX = new AtomicReference<>();
    public static final AtomicReference<Boolean>                        USE_EQUIP_MODE = new AtomicReference<>(false);

    public static final AtomicReference<Short>                          CUSTOMER_ID = new AtomicReference<>();
    public static final AtomicReference<Boolean>                        MANAGE_RENTAL_MODE = new AtomicReference<>(false);

    public static final AtomicReference<Short>                          COMPILER_MODE = new AtomicReference<>(compilerMode);

    public _Variables() {}

    public short getShortFromTwoBytes(byte low, byte high) {
        return ((short) (((high & 0x00ff) << 8) | ((low & 0x00ff))));
    }

    public int getIntFromFourBytes(byte lowLow, byte lowHigh, byte highLow, byte highHigh) {
        return (((highHigh & 0x00ff) << 24) | ((highLow & 0x00ff) << 16) | ((lowHigh & 0x00ff) << 8) | ((lowLow & 0x00ff)));
    }
}
