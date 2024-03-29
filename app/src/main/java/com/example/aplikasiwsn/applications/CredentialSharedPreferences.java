package com.example.aplikasiwsn.applications;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class CredentialSharedPreferences {
    private SharedPreferences sharedPref;
    private static final String NAMA_SHARED_PREF = "sp_credential";
    private static final String KEY_LOGIN_DATE = "LOGIN_DATE";
    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_TOKEN = "TOKEN";
    private static final String JUMLAH_KODE_PETAK = "JUMLAH_KODE_PETAK";
    private static final String JUMLAH_NODE_SENSING = "JUMLAH_NODE_SENSING";

    private Context context;

    public CredentialSharedPreferences(Context context) {
        this.context = context;
        this.sharedPref = context.getSharedPreferences(NAMA_SHARED_PREF, 0);
    }

    public void saveUsername(String username) {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }

    public String loadUsername() {
        return sharedPref.getString(KEY_USERNAME, "NONE");
    }

    public void saveLoginDate() {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putLong(KEY_LOGIN_DATE, new Date().getTime());
        editor.commit();
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public void saveJumlahKodePetak(String jumlah) {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString(JUMLAH_KODE_PETAK, jumlah);
        editor.commit();
    }

    public void saveJumlahNodeSensing(String jumlah) {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString(JUMLAH_NODE_SENSING, jumlah);
        editor.commit();
    }

    public String loadToken() {
        return sharedPref.getString(KEY_TOKEN, "NONE");
    }

    public Date loadLoginDate() {
        return new Date(sharedPref.getLong(KEY_LOGIN_DATE, 0));
    }

    public String loadJumlahKodePetak() {
        return sharedPref.getString(JUMLAH_KODE_PETAK, "0");
    }

    public String loadJumlahNode() {
        return sharedPref.getString(JUMLAH_NODE_SENSING, "0");
    }

    public void clearCredential() {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.clear();
        editor.commit();
    }
}