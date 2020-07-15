package com.example.fincasyscommercial;

import android.app.NotificationManager;
import android.content.Intent;

import org.json.JSONObject;

public class VariableBag {

    public static final String MAIN_KEY ="Bms" ;
    public static String PREF_NAME = "BMS";
    public static String USER_ID = "user_id";
    public static String SOCIETY_ID="society_id";
    public static String FULL_NAME="user_full_name";
    public static String FIRST_NAME="user_first_name";
    public static String LAST_NAME="user_last_name";
    public static String MOBILE="user_mobile";
    public static String EMAIL="user_email";
    public static String ID_PROOF="user_id_proof";
    public static String USER_TYPE="user_type";
    public static String BLOCK_ID="block_id";
    public static String FLOOR_ID="floor_id";
    public static String UNIT_ID="unit_id";
    public static String USER_STATUS="user_status";
    public static String SUCCESS_CODE="200";
    public static String FAIL_CODE="201";
    public static String LOGIN = "LOGIN";
    public static String Rupee = "₹ ";

    public static String VERSION_CODE="version_code";

    public static String SUB_URL = "adminApi/";
    public static String APADMIN = "apAdmin/";

    public static String MAIN_URL="https://www.fincasys.com/mainApi/";

    public static String BACKIMG="";

    public static String SOCIETY_NAME="";
    public static String URL_CLICK="";


    public static NotificationManager visitorNotificationNM;
    public static  int NOTIFICATION_SOS_ID = 888;
    public static JSONObject mainObjectJSON;
    public static boolean isBackground=false;
    public static Intent svc;


}
