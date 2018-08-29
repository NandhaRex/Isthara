package com.tao.isthara.Utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.renderscript.Sampler;

public class AppPreferences {


    public static final String KEY_USER_ID = "userid";
    public static final String KEY_USER_RESIDENT_ID = "userresidentid";
    public static final String KEY_USER_BLOCK = "blockid";
    public static final String KEY_USER_TYPE = "usertype";

    public static final String KEY_USER_IS_VERIFIED = "verified";
    public static final String KEY_IS_RATED = "rated";

    public static final String KEY_IS_CHECKEDOUT = "ischeckout";
    public static final String KEY_CHECKEDOUT_DATE = "checkedoutdate";
    public static final String KEY_BANK_NAME = "bankname";
    public static final String KEY_ACC_HOLDER_NAME = "accname";
    public static final String KEY_ACC_NO = "accnumber";
    public static final String KEY_IFSC = "ifsc";
    public static final String KEY_REASON = "reason";




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
        return _sharedPrefs.getInt(KEY_USER_RESIDENT_ID, 0);
    }

    public void saveResidentId(int id) {
        _prefsEditor.putInt(KEY_USER_RESIDENT_ID, id);
        _prefsEditor.commit();
    }

    public void saveIsCheckOut(boolean value) {
        _prefsEditor.putBoolean(KEY_IS_CHECKEDOUT, value);
        _prefsEditor.commit();
    }

    public boolean getIsCheckedOut() {
        return _sharedPrefs.getBoolean(KEY_IS_CHECKEDOUT, false);
    }

    public void saveCheckOutReqDate(String date) {
        _prefsEditor.putString(KEY_CHECKEDOUT_DATE, date);
        _prefsEditor.commit();
    }

    public void saveBankName(String bankName) {
        _prefsEditor.putString(KEY_BANK_NAME, bankName);
        _prefsEditor.commit();
    }

    public void saveAccHolderName(String name) {
        _prefsEditor.putString(KEY_ACC_HOLDER_NAME, name);
        _prefsEditor.commit();
    }

    public void saveAccNo(String accno) {
        _prefsEditor.putString(KEY_ACC_NO, accno);
        _prefsEditor.commit();
    }

    public void saveIFSC(String ifsc) {
        _prefsEditor.putString(KEY_IFSC, ifsc);
        _prefsEditor.commit();
    }

    public void saveReason(String reason){
        _prefsEditor.putString(KEY_REASON, reason);
        _prefsEditor.commit();
    }

    public String getKeyCheckedoutDate() {
        return _sharedPrefs.getString(KEY_CHECKEDOUT_DATE,"");
    }

    public String getAccHolderName() {
        return _sharedPrefs.getString(KEY_ACC_HOLDER_NAME, "");
    }

    public String getAccNo() {
        return _sharedPrefs.getString(KEY_ACC_NO, "");
    }

    public String getKeyBankName() {
        return _sharedPrefs.getString(KEY_BANK_NAME, "");
    }

    public String getKeyIfsc() {
        return _sharedPrefs.getString(KEY_IFSC, "");
    }

    public String getKeyReason() {
        return _sharedPrefs.getString(KEY_REASON, "No Reason");
    }
}
