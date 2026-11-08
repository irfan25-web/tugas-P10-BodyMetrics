package com.example.bodymetrics;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class PengaturanActivity extends AppCompatActivity {

    private PrefsManager prefs;
    private SwitchCompat swGelap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        prefs = new PrefsManager(this);
        swGelap = findViewById(R.id.swGelap);
        Button btnHapus = findViewById(R.id.btnHapus);

        // Simpan otomatis tiap mode gelap diubah
        swGelap.setOnCheckedChangeListener((btn, checked) -> {
            prefs.setGelap(checked);
            AppCompatDelegate.setDefaultNightMode(checked
                    ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        });

        btnHapus.setOnClickListener(v -> {
            prefs.hapusSemua();
            swGelap.setChecked(false);
            Toast.makeText(this, "Data tersimpan sudah dihapus", Toast.LENGTH_SHORT).show();
        });
    }

    // Pengaturan dibaca lagi tiap layar tampil
    @Override
    protected void onResume() {
        super.onResume();
        swGelap.setChecked(prefs.isGelap());
    }
}