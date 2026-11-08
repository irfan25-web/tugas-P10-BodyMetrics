package com.example.bodymetrics;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IdealActivity extends AppCompatActivity {

    private PrefsManager prefs;
    private TextView tvData, tvBmi, tvKategori, tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideal);

        prefs = new PrefsManager(this);
        tvData = findViewById(R.id.tvData);
        tvBmi = findViewById(R.id.tvBmi);
        tvKategori = findViewById(R.id.tvKategori);
        tvHasil = findViewById(R.id.tvHasil);
    }

    // Data tersimpan dibaca lagi tiap layar tampil
    @Override
    protected void onResume() {
        super.onResume();

        float tinggi = prefs.getTinggi();
        float berat = prefs.getBerat();

        if (tinggi <= 0 || berat <= 0) {
            tvData.setText("");
            tvBmi.setText("");
            tvKategori.setText("");
            tvHasil.setText("Belum ada data.\nIsi dulu di menu Kalori & Protein.");
            return;
        }

        double m = tinggi / 100.0;
        double bmi = berat / (m * m);

        String kategori;
        if (bmi < 18.5) kategori = "Kurus";
        else if (bmi < 25) kategori = "Normal (ideal)";
        else if (bmi < 30) kategori = "Gemuk";
        else kategori = "Obesitas";

        // Rentang berat ideal dari BMI 18,5 - 24,9
        double bbMin = 18.5 * m * m;
        double bbMax = 24.9 * m * m;
        // Target tengah dari BMI 22
        double bbTarget = 22 * m * m;

        tvData.setText("TB " + (int) tinggi + " cm, BB " + berat + " kg");
        tvBmi.setText("BMI " + String.format("%.1f", bmi));
        tvKategori.setText(kategori);

        String saran;
        if (berat < bbMin) {
            saran = "Perlu naik sekitar " + String.format("%.1f", bbMin - berat) + " kg";
        } else if (berat > bbMax) {
            saran = "Perlu turun sekitar " + String.format("%.1f", berat - bbMax) + " kg";
        } else {
            saran = "Berat badanmu sudah ideal, pertahankan!";
        }

        tvHasil.setText("Rentang berat ideal: "
                + String.format("%.1f", bbMin) + " - " + String.format("%.1f", bbMax) + " kg\n"
                + "Berat target: " + String.format("%.1f", bbTarget) + " kg\n\n"
                + saran);
    }
}