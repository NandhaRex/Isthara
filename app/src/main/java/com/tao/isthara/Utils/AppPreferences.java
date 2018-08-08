package com.tao.isthara.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {


    public static final String KEY_USER_ID = "userid";
    public static final String KEY_USER_RESIDENT_ID = "userresidentid";
    public static final String KEY_USER_BLOCK = "blockid";
    public static final String KEY_USER_TYPE = "usertype";

    public static final String KEY_USER_IS_VERIFIED = "verified";
    public static final String KEY_IS_RATED = "rated";


    private static final String APP_SHARED_PREFS = AppPreferences.class.getSimpleName(); //  Name of the file -.xml
    private SharedPreferences _sharedPrefs;
    private Editor _prefsEditor;

    public AppPreferences(Context context) {
        this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }

    public String getUserID() {
        return _sharedPrefs.getString(KEY_USER_ID, "");
    }

    public void saveUserID(String text) {
        _prefsEditor.putString(KEY_USER_ID, text);
        _prefsEditor.commit();
    }

    public String getBlockID() {
        return _sharedPrefs.getString(KEY_USER_BLOCK, "");
    }

    public void saveBlockID(String text) {
        _prefsEditor.putString(KEY_USER_BLOCK, text);
        _prefsEditor.commit();
    }


    public String getUserType() {
        return _sharedPrefs.getString(KEY_USER_TYPE, "Resident");
    }

    public void saveUserType(String text) {
        _prefsEditor.putString(KEY_USER_TYPE, text);
        _prefsEditor.commit();
    }

    public boolean getUserIsVerified() {
        return _sharedPrefs.getBoolean(KEY_USER_IS_VERIFIED, false);
    }

    public void saveUserIsVerified(boolean text) {
        _prefsEditor.putBoolean(KEY_USER_IS_VERIFIED, text);
        _prefsEditor.commit();
    }

    public boolean getIsRated() {
        return _sharedPrefs.getBoolean(KEY_IS_RATED, false);
    }

    public void saveIsRated(boolean text) {
        _prefsEditor.putBoolean(KEY_IS_RATED, text);
        _prefsEditor.commit();
    }

    public int getResidentId() {
        return _sharedPrefs.getInt(KEY_USER_RESIDENT_ID,0);
    }

    public void saveResidentId(int id) {
        _prefsEditor.putInt(KEY_USER_RESIDENT_ID, id);
        _prefsEditor.commit();
    }

}
