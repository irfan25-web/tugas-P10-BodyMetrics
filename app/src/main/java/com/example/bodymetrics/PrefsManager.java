package com.example.bodymetrics;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {
    private final SharedPreferences sp;

    public PrefsManager(Context ctx) {
        sp = ctx.getSharedPreferences("bodymetrics_prefs", Context.MODE_PRIVATE);
    }

    public void setTinggi(float v)  { sp.edit().putFloat("tinggi", v).apply(); }
    public float getTinggi()        { return sp.getFloat("tinggi", 0); }

    public void setBerat(float v)   { sp.edit().putFloat("berat", v).apply(); }
    public float getBerat()         { return sp.getFloat("berat", 0); }

    public void setUmur(int v)      { sp.edit().putInt("umur", v).apply(); }
    public int getUmur()            { return sp.getInt("umur", 0); }

    public void setPria(boolean b)  { sp.edit().putBoolean("pria", b).apply(); }
    public boolean isPria()         { return sp.getBoolean("pria", true); }

    public void setAktivitas(int i) { sp.edit().putInt("aktivitas", i).apply(); }
    public int getAktivitas()       { return sp.getInt("aktivitas", 0); }

    public void setGelap(boolean b) { sp.edit().putBoolean("gelap", b).apply(); }
    public boolean isGelap()        { return sp.getBoolean("gelap", false); }

    public void hapusSemua()        { sp.edit().clear().apply(); }
}