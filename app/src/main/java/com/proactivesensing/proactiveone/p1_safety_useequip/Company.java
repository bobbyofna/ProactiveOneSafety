package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.util.Log;
import java.util.ArrayList;

public class Company {

    private String COMPANY_NAME;
    private ArrayList<Unit> MODELS;

    public Company() {
        MODELS = new ArrayList<Unit>();
    }

    public boolean setCompanyName(String name) {
        try {
            COMPANY_NAME = name;
            return true;
        } catch(Exception e) { return false; }
    }

    public boolean addModel(Unit unit) {
        try {
            MODELS.add(unit);
            return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    public String getCompanyName() { return COMPANY_NAME; }
    public int getModelListSize() { return MODELS.size(); }
    public ArrayList<Unit> getFullModelList() { return MODELS; }
    public Unit getModelByIndex(int index) {
        try {
            if ((index >= 0) && (index < MODELS.size()))
                return MODELS.get(index);
            else
                throw new Exception("Model index value is out of range");
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return null;
    }
}
