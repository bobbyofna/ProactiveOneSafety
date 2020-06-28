package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.util.Log;

import java.util.ArrayList;

public class Subtype {

    private String SUBTYPE;
    private static ArrayList<String> ALL_SUBTYPES = new ArrayList<>();

    public Subtype() {}

    public boolean setSubtype(String subtype) {
        try {
            if(!checkIfSubtypeExists(subtype)) {
                SUBTYPE = subtype;
                ALL_SUBTYPES.add(subtype);
            }
            else
                SUBTYPE = getSubtypeByIndex(getIndexOfExistingSubtype(subtype));
            return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    public String getSubtypeName() { return SUBTYPE; }

    public int getSubtypeListSize() { return ALL_SUBTYPES.size(); }

    public ArrayList<String> getListOfSubtypes() { return ALL_SUBTYPES; }

    public String getSubtypeByIndex(int index) { return ALL_SUBTYPES.get(index); }

    public boolean setSubtypeByIndex(int index) {
        try {
            SUBTYPE = ALL_SUBTYPES.get(index);
            return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    private int getIndexOfExistingSubtype(String check) {
        try {
            for (int i = 0; i < getSubtypeListSize(); i++)
                if (getSubtypeByIndex(i).toLowerCase().equals(check.toLowerCase()))
                    return i;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return -1;
    }

    private boolean checkIfSubtypeExists(String check) {
        try {
            for (int i = 0; i < getSubtypeListSize(); i++)
                if (getSubtypeByIndex(i).toLowerCase().equals(check.toLowerCase()))
                    return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }
}
