package com.example.bodymetrics;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private PrefsManager prefs;
    private TextView tvRingkasan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = new PrefsManager(this);
        tvRingkasan = findViewById(R.id.tvRingkasan);

        findViewById(R.id.cardKalori).setOnClickListener(v ->
                startActivity(new Intent(this, KaloriActivity.class)));
        findViewById(R.id.cardIdeal).setOnClickListener(v ->
                startActivity(new Intent(this, IdealActivity.class)));
        findViewById(R.id.cardPengaturan).setOnClickListener(v ->
                startActivity(new Intent(this, PengaturanActivity.class)));
    }

    // Data tersimpan dibaca lagi tiap dashboard tampil
    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getTinggi() > 0 && prefs.getBerat() > 0) {
            tvRingkasan.setText("Data terakhir: TB " + (int) prefs.getTinggi()
                    + " cm, BB " + prefs.getBerat() + " kg");
        } else {
            tvRingkasan.setText("Belum ada data. Mulai dari menu Kalori & Protein.");
        }
    }
}