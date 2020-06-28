package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.util.Log;

import java.util.ArrayList;

public class Unit {

    //private String company_name = null;
    private Type TYPE = null;
    private Subtype SUBTYPE = null;
    private String NAME = null;
    private int ID = -1;
    private ArrayList<String> QUESTIONS = null;
    private ArrayList<Integer> NFC_EN_PER_QUES = null;
    private ArrayList<ArrayList<String>> NFC_LABELS = null;
    private ArrayList<ArrayList<String>> NFC_LOCATIONS = null;
    private ArrayList<ArrayList<String>> NFC_NAMES;
    private ArrayList<ArrayList<Integer>> NFC_IDS = null;

    private String STANDARD_NFC_QUESTION = "Tap The NFC Tag Labeled aaa And Located bbb To Verify That You Have Inspected ccc";

    private static ArrayList<Integer> ALL_IDS = new ArrayList<>();
    private static ArrayList<Integer> ALL_NFC_IDS = new ArrayList<>();

    private final static int STARTING_ID_NUMBER = 1000000;

    public Unit() {
        try {
            QUESTIONS = new ArrayList<>();
            NFC_EN_PER_QUES = new ArrayList<>();
            NFC_LABELS = new ArrayList<>();
            NFC_LOCATIONS = new ArrayList<>();
            NFC_NAMES = new ArrayList<>();
            NFC_IDS = new ArrayList<>();
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
    }

    public Unit(String name) {
        try {
            NAME = name;

            QUESTIONS = new ArrayList<>();
            NFC_EN_PER_QUES = new ArrayList<>();
            NFC_LABELS = new ArrayList<>();
            NFC_LOCATIONS = new ArrayList<>();
            NFC_NAMES = new ArrayList<>();
            NFC_IDS = new ArrayList<>();
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
    }

    public String getName() { return NAME; }
    public Type getType() { return TYPE; }
    public String getTypeName() { return TYPE.getTypeName(); }
    public Subtype getSubtype() { return SUBTYPE; }
    public String getSubtypeName() { return SUBTYPE.getSubtypeName(); }
    public int getId() { return ID; }
    public int getQuestionCount() { return QUESTIONS.size(); }
    public String getQuestionByIndex(int index) { return QUESTIONS.get(index); }
    public ArrayList<String> getFullQuestionList() { return QUESTIONS; }
    public boolean getNfcEnabledByQuestionIndex(int index) { return (NFC_EN_PER_QUES.get(index) > 0); }
    public int getNfcCountByQuestionIndex(int index) { return NFC_EN_PER_QUES.get(index); }

    public ArrayList<String> getNfcLabelsListByQuestionIndex(int index) { return NFC_LABELS.get(index); }
    public ArrayList<String> getNfcLocationsListByQuestionIndex(int index) { return NFC_LOCATIONS.get(index); }
    public ArrayList<String> getNfcNamesListByQuestionIndex(int index) { return NFC_NAMES.get(index); }
    public ArrayList<Integer> getNfcIdsListByQuestionIndex(int index) { return NFC_IDS.get(index); }

    public int getNfcLabelsSizeByQuestionIndex(int index) { return NFC_LABELS.get(index).size(); }
    public int getNfcLocationsSizeByQuestionIndex(int index) { return NFC_LOCATIONS.get(index).size(); }
    public int getNfcNamesSizeByQuestionIndex(int index) { return NFC_NAMES.get(index).size(); }
    public int getNfcIdssSizeByQuestionIndex(int index) { return NFC_IDS.get(index).size(); }

    public int getUnitIdFromFullListByindex(int index) { return ALL_IDS.get(index); }
    public int getNfcIdFromFullListByindex(int index) { return ALL_NFC_IDS.get(index); }
    public ArrayList<Integer> getFullIdList() { return ALL_IDS; }
    public ArrayList<Integer> getFullNfcIdList() { return ALL_NFC_IDS; }

    public void setStandardNfcQuestion(String stnd_nfc_ques) { STANDARD_NFC_QUESTION = stnd_nfc_ques; }
    public String getStandardNfcQuestion() { return STANDARD_NFC_QUESTION; }

    public boolean getNfcEnabled() {
        try {
            for(int i = 0; i < NFC_LABELS.size(); i++)
                if(NFC_LABELS.get(i).size() > 0)
                    return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    public int getNfcIdsSize() {
        try {
            int size = 0;
            for (int i = 0; i < NFC_IDS.size(); i++)
                size += NFC_IDS.get(i).size();
            return size;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return -1;
    }

    public int getNfcNamesSize() {
        try {
            int size = 0;
            for (int i = 0; i < NFC_NAMES.size(); i++)
                size += NFC_NAMES.get(i).size();
            return size;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return -1;
    }

    public int getNfcLocationsSize() {
        try {
            int size = 0;
            for (int i = 0; i < NFC_LOCATIONS.size(); i++)
                size += NFC_LOCATIONS.get(i).size();
            return size;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return -1;
    }

    public int getNfcLabelsSize() {
        try {
            int size = 0;
            for (int i = 0; i < NFC_LOCATIONS.size(); i++)
                size += NFC_LOCATIONS.get(i).size();
            return size;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return -1;
    }

    public ArrayList<Integer> getNfcIdsList() {
        try {
            ArrayList<Integer> nfc_ids = new ArrayList<>();
            for (int i = 0; i < NFC_IDS.size(); i++)
                for (int k = 0; k < NFC_IDS.get(i).size(); k++)
                    nfc_ids.add(NFC_IDS.get(i).get(k));
            return nfc_ids;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return null;
    }

    public ArrayList<String> getNfcNamesList() {
        try {
            ArrayList<String> nfc_names = new ArrayList<>();
            for (int i = 0; i < NFC_NAMES.size(); i++)
                for (int k = 0; k < NFC_NAMES.get(i).size(); k++)
                    nfc_names.add(NFC_NAMES.get(i).get(k));
            return nfc_names;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return null;
    }

    public ArrayList<String> getNfcLocationsList() {
        try {
            ArrayList<String> nfc_locations = new ArrayList<>();
            for (int i = 0; i < NFC_LOCATIONS.size(); i++)
                for (int k = 0; k < NFC_LOCATIONS.get(i).size(); k++)
                    nfc_locations.add(NFC_LOCATIONS.get(i).get(k));
            return nfc_locations;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return null;
    }

    public ArrayList<String> getNfcLabelsList() {
        try {
            ArrayList<String> nfc_labels = new ArrayList<>();
            for (int i = 0; i < NFC_LABELS.size(); i++)
                for (int k = 0; k < NFC_LABELS.get(i).size(); k++)
                    nfc_labels.add(NFC_LABELS.get(i).get(k));
            return nfc_labels;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return null;
    }

    public int getNfcEnabledIndexSize() {
        try {
            int size = 0;
            for (int i = 0; i < NFC_EN_PER_QUES.size(); i++)
                if (NFC_EN_PER_QUES.get(i) > 0)
                    size++;
            return size;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return -1;
    }

    public int getNfcPerIndexListSize() {
        try {
            int size = 0;
            for (int i = 0; i < NFC_EN_PER_QUES.size(); i++)
                if (NFC_EN_PER_QUES.get(i) > 0)
                    size++;
            return size;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return -1;
    }

    public ArrayList<Integer> getNfcPerIndexList() {
        try {
            ArrayList<Integer> nfc_per_index = new ArrayList<>();
            for (int i = 0; i < NFC_EN_PER_QUES.size(); i++)
                if (NFC_EN_PER_QUES.get(i) > 0)
                    nfc_per_index.add(NFC_EN_PER_QUES.get(i));
            return nfc_per_index;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return null;
    }

    public ArrayList<Integer> getNfcEnabledIndexList() {
        try {
            ArrayList<Integer> nfc_en_indexes = new ArrayList<>();
            for (int i = 0; i < NFC_EN_PER_QUES.size(); i++)
                if (NFC_EN_PER_QUES.get(i) > 0)
                    nfc_en_indexes.add(i);
            return nfc_en_indexes;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return null;
    }

    public boolean setName(String name) {
        try {
            NAME = name;
            return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    public boolean setType(Type type) {
        try {
            TYPE = type;
            return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    public boolean setSubtype(Subtype subtype) {
        try {
            SUBTYPE = subtype;
            return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    public boolean setIdentifier(int id) {
        try {
            if(!checkForExistingId(id))
                ID = id;
            else
                throw null;
            return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    public boolean setIdentifier() {
        try {
            int id = -1;

            if(ALL_IDS.size() > 0)
                id = findUnusedIdNumber();
            else
                id = STARTING_ID_NUMBER - 1;

            ID = id;
            ALL_IDS.add(id);
            return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    public boolean addQuestion(String question, int nfc_count, ArrayList<String> nfc_labels, ArrayList<String> nfc_locations, ArrayList<String> nfc_names, ArrayList<Integer> nfc_ids) {
        try {
            QUESTIONS.add(question);
            NFC_EN_PER_QUES.add(nfc_count);
            if(nfc_count > 0) {
                if(!((nfc_ids.size() != nfc_locations.size()) || (nfc_ids.size() != nfc_names.size()) || (nfc_ids.size() != nfc_labels.size()))) {
                    NFC_LABELS.add(nfc_labels);
                    NFC_LOCATIONS.add(nfc_locations);
                    NFC_NAMES.add(nfc_names);
                    NFC_IDS.add(nfc_ids);
                    for(int i = 0; i < nfc_ids.size(); i++)
                        ALL_NFC_IDS.add(nfc_ids.get(i));
                }
                else
                    return false;
            }
            else {
                NFC_LABELS.add(new ArrayList<String>());
                NFC_LOCATIONS.add(new ArrayList<String>());
                NFC_NAMES.add(new ArrayList<String>());
                NFC_IDS.add(new ArrayList<Integer>());
            }
            return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    public boolean addQuestion(String question, int nfc_count, ArrayList<String> nfc_labels, ArrayList<String> nfc_locations, ArrayList<String> nfc_names) {
        try {
            QUESTIONS.add(question);
            NFC_EN_PER_QUES.add(nfc_count);
            if(nfc_count > 0) {
                if(!((nfc_names.size() != nfc_locations.size()) || (nfc_names.size() != nfc_labels.size()))) {
                    NFC_LABELS.add(nfc_labels);
                    NFC_LOCATIONS.add(nfc_locations);
                    NFC_NAMES.add(nfc_names);
                    NFC_IDS.add(findUnusedNfcIdNumbers(nfc_labels.size()));
                }
                else
                    return false;
            }
            else {
                NFC_LABELS.add(new ArrayList<String>());
                NFC_LOCATIONS.add(new ArrayList<String>());
                NFC_NAMES.add(new ArrayList<String>());
                NFC_IDS.add(new ArrayList<Integer>());
            }
            return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    public boolean addQuestion(String question) {
        try {
            QUESTIONS.add(question);
            NFC_EN_PER_QUES.add(0);
            NFC_LABELS.add(new ArrayList<String>());
            NFC_LOCATIONS.add(new ArrayList<String>());
            NFC_NAMES.add(new ArrayList<String>());
            NFC_IDS.add(new ArrayList<Integer>());
            return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    private int findUnusedIdNumber() {
        try {
            for (int k = ALL_IDS.get(ALL_IDS.size() - 1) - 1; k >= 0; k--)
                if (!checkForExistingId(k))
                    return k;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return -1;
    }

    private ArrayList<Integer> findUnusedNfcIdNumbers(int size) {
        try {
            ArrayList<Integer> nfc_ids = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                int proposed = -1;
                if (ALL_NFC_IDS.size() > 0) {
                    for (int k = ALL_NFC_IDS.get(ALL_NFC_IDS.size() - 1) - 1; k >= 0; k--) {
                        if (!checkForExistingNfcId(k)) {
                            proposed = k;
                            k = -1;
                            break;
                        }
                    }
                } else
                    proposed = STARTING_ID_NUMBER - 1;

                if (proposed != -1) {
                    nfc_ids.add(proposed);
                    ALL_NFC_IDS.add(proposed);
                } else
                    throw new Exception("Error, no matches found");
            }

            if (nfc_ids.size() == size)
                return nfc_ids;
            else
                throw new Exception("Error, somehow sizes do not match up");
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return null;
    }

    private boolean checkForExistingNfcId(int id) {
        try {
            for (int i = 0; i < ALL_NFC_IDS.size(); i++)
                if (ALL_NFC_IDS.get(i) == id)
                    return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }

    private boolean checkForExistingId(int id) {
        try {
            for (int i = 0; i < ALL_IDS.size(); i++)
                if (ALL_IDS.get(i) == id)
                    return true;
        } catch(Exception e) { Log.e("Error", "\n\nERROR...\n" + (new Utils()).printError(e)); }
        return false;
    }
}
