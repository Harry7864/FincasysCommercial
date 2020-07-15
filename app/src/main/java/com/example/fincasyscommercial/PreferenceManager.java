package com.example.fincasyscommercial;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by in on 22/3/17.
 */

public class PreferenceManager {

    public static PreferenceManager preferenceManager;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public static PreferenceManager getInstance() {
        return preferenceManager;
    }

    public PreferenceManager(Context context) {
        preferenceManager = this;
        mSharedPreferences = context.getSharedPreferences(VariableBag.PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void clearPreferences() {
        mEditor.clear();
        mEditor.commit();
    }

    public void removePref(Context context, String keyToRemove) {
        mSharedPreferences = context.getSharedPreferences(VariableBag.PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.remove(keyToRemove);
        mEditor.commit();
    }

    /*set preference for registration*/

    public void setSocietyId(String id){
        mEditor.putString(VariableBag.SOCIETY_ID, id).commit();
    }

    public  String getSocietyId(){
        return mSharedPreferences.getString(VariableBag.SOCIETY_ID, "0");
    }

    public void setVersionCode(int value) {
        mEditor.putInt(VariableBag.VERSION_CODE, value).commit();
    }
    public int getVersionCode() {
        int getValue = mSharedPreferences.getInt(VariableBag.VERSION_CODE, 0);
        return getValue;
    }


    public void setBlockId(String id){
        mEditor.putString(VariableBag.BLOCK_ID, id).commit();
    }

    public  String getBlockId(){
        return mSharedPreferences.getString(VariableBag.BLOCK_ID, "0");
    }

    public void setFloorId(String id){
        mEditor.putString(VariableBag.FLOOR_ID, id).commit();
    }

    public  String getFloorId(){
        return mSharedPreferences.getString(VariableBag.FLOOR_ID, "0");
    }

    public void setUnitId(String id){
        mEditor.putString(VariableBag.UNIT_ID, id).commit();
    }

    public  String getUnitId(){
        return mSharedPreferences.getString(VariableBag.UNIT_ID, "0");
    }


    public String getRegisteredUserId() {
        String strUserId = mSharedPreferences.getString(VariableBag.USER_ID, "0");
        return strUserId;
    }

    public void setRegisteredUserId(String strUserId) {
        mEditor.putString(VariableBag.USER_ID, strUserId).commit();
    }

    public void setLoginSession() {
        mEditor.putBoolean(VariableBag.LOGIN, true);
        mEditor.commit();
    }

    public boolean getLoginSession() {
        boolean login = mSharedPreferences.getBoolean(VariableBag.LOGIN, false);
        return login;
    }

    public void deleteLoginsession(){
        mEditor.putBoolean(VariableBag.LOGIN, false);
        mEditor.commit();
    }

    public void setWiFiSession(boolean wiFiSession) {
        mEditor.putBoolean("wifi", wiFiSession).commit();
    }

    public boolean getWiFiSession() {
        boolean login = mSharedPreferences.getBoolean("wifi", false);
        return login;
    }


    public void setKeyValueString(String key, String value) {
        mEditor.putString(key, value).commit();
    }

    public void setKeyValueInt(String key, int value) {
        mEditor.putInt(key, value).commit();
    }

    public void setKeyValueBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value).commit();
    }

    public void setFirstTime(boolean key) {

        mEditor.putBoolean("firstTime", key).commit();

    }

    public boolean getFirstTime() {

        boolean val = mSharedPreferences.getBoolean("firstTime", false);
        return val;
    }


    public String getKeyValueString(String key) {
        String getValue = mSharedPreferences.getString(key, " ");
        return getValue;
    }

    public int getKeyValueInt(String key) {
        int getValue = mSharedPreferences.getInt(key, 0);
        return getValue;
    }

    public boolean getKeyValueBoolean(String key) {
        boolean getValue = mSharedPreferences.getBoolean(key, false);
        return getValue;
    }


    public void setApikey(String wiFiSession) {
        mEditor.putString("key", wiFiSession).commit();
    }

    public String getApiKey() {
        return mSharedPreferences.getString("key", "0");
    }

    public void setBaseUrl(String wiFiSession) {
        mEditor.putString("baseurl", wiFiSession).commit();
    }

    public String getBaseUrl() {
        return mSharedPreferences.getString("baseurl", "")+VariableBag.SUB_URL;
    }

    public String getBaseUrlApAdmin() {
        return mSharedPreferences.getString("baseurl", "")+VariableBag.APADMIN;
    }

    public void setBackBanner(String key) {
        mEditor.putString("bannerBack", key).commit();
    }

    public String getBackBanner() {
        String val = mSharedPreferences.getString("bannerBack", VariableBag.BACKIMG);
        return val;
    }

    public String getSocietyName() {
        return mSharedPreferences.getString(VariableBag.SOCIETY_NAME, "0");
    }

    public void setSocietyName(String id) {
        mEditor.putString(VariableBag.SOCIETY_NAME, id).commit();
    }

}


