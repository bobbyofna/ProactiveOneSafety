package com.proactivesensing.proactiveone.p1_safety_useequip;

import android.util.Log;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {

    public Utils() {}

    public int printAndVerify(ArrayList<String> prints, int min, int max) {
        Scanner in = new Scanner(System.in);
        while(true) {
            try {
                for (int i = 0; i < prints.size(); i++)
                    Log.e("Debug", prints.get(i));
                int choice = Integer.parseInt("" + in.nextLine());
                if((choice == -1) || ((choice >= min) && (choice <= max)))
                    return choice;
                else
                    throw null;
            } catch(Exception e) { Log.e("Error", "\n\nBad input, please try again\n\n"); }
        }
    }

    public int printAndVerify(ArrayList<String> prints, int mode) {
        Scanner in = new Scanner(System.in);
        while(true) {
            try {
                for (int i = 0; i < prints.size(); i++)
                    Log.e("Debug", prints.get(i));
                int choice = Integer.parseInt("" + in.nextLine());
                return choice;
            } catch(Exception e) { Log.e("Error", "\n\nBad input, please try again\n\n"); }
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

    public String printError(FileNotFoundException e) {
        String str = "\n";
        str = str + e.getMessage() + "\n";
        str = str + e.getStackTrace()[0].getFileName() + ":";
        str = str + e.getStackTrace()[0].getMethodName() + ":";
        str = str + e.getStackTrace()[0].getLineNumber();

        return str;
    }

    public String printError(IOException e) {
        String str = "\n";
        str = str + e.getMessage() + "\n";
        str = str + e.getStackTrace()[0].getFileName() + ":";
        str = str + e.getStackTrace()[0].getMethodName() + ":";
        str = str + e.getStackTrace()[0].getLineNumber();

        return str;
    }

    public String printError(JSONException e) {
        String str = "\n";
        str = str + e.getMessage() + "\n";
        str = str + e.getStackTrace()[0].getFileName() + ":";
        str = str + e.getStackTrace()[0].getMethodName() + ":";
        str = str + e.getStackTrace()[0].getLineNumber();

        return str;
    }
}
