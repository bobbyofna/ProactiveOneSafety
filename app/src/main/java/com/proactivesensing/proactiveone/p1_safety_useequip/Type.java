package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.util.Log;

import java.util.ArrayList;

public class Type {

    private String TYPE;
    private static ArrayList<String> ALL_TYPES = new ArrayList<>();

    public Type() {}

    public boolean setType(String type) {
        try {
            if(!checkIfTypeExists(type)) {
                TYPE = type;
                ALL_TYPES.add(type);
            }
            else
                TYPE = getTypeByIndex(getIndexOfExistingType(type));
            return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    public String getTypeName() { return TYPE; }

    public int getTypeListSize() { return ALL_TYPES.size(); }

    public ArrayList<String> getListOfTypes() { return ALL_TYPES; }

    public String getTypeByIndex(int index) { return ALL_TYPES.get(index); }

    public boolean setTypeByIndex(int index) {
        try {
            TYPE = ALL_TYPES.get(index);
            return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    private int getIndexOfExistingType(String check) {
        try {
            for (int i = 0; i < getTypeListSize(); i++)
                if (getTypeByIndex(i).toLowerCase().equals(check.toLowerCase()))
                    return i;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return -1;
    }

    private boolean checkIfTypeExists(String check) {
        try {
            for (int i = 0; i < getTypeListSize(); i++)
                if (getTypeByIndex(i).toLowerCase().equals(check.toLowerCase()))
                    return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }
}
