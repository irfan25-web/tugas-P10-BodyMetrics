package com.example.bodymetrics;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class KaloriActivity extends AppCompatActivity {

    private static final String[] AKTIVITAS = {
            "Jarang olahraga",
            "Olahraga 1-3x/minggu",
            "Olahraga 3-5x/minggu",
            "Olahraga 6-7x/minggu"};
    private static final double[] FAKTOR = {1.2, 1.375, 1.55, 1.725};
    private static final double[] PROTEIN_PER_KG = {0.8, 1.0, 1.2, 1.6};

    private PrefsManager prefs;
    private EditText etTinggi, etBerat, etUmur;
    private RadioButton rbPria, rbWanita;
    private Spinner spAktivitas;
    private TextView tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalori);

        prefs = new PrefsManager(this);
        etTinggi = findViewById(R.id.etTinggi);
        etBerat = findViewById(R.id.etBerat);
        etUmur = findViewById(R.id.etUmur);
        rbPria = findViewById(R.id.rbPria);
        rbWanita = findViewById(R.id.rbWanita);
        spAktivitas = findViewById(R.id.spAktivitas);
        tvHasil = findViewById(R.id.tvHasil);
        Button btnHitung = findViewById(R.id.btnHitung);

        spAktivitas.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, AKTIVITAS));

        btnHitung.setOnClickListener(v -> hitung());
    }

    // Data tersimpan dibaca lagi tiap layar tampil
    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getTinggi() > 0) etTinggi.setText(fmt(prefs.getTinggi()));
        if (prefs.getBerat() > 0) etBerat.setText(fmt(prefs.getBerat()));
        if (prefs.getUmur() > 0) etUmur.setText(String.valueOf(prefs.getUmur()));
        rbPria.setChecked(prefs.isPria());
        rbWanita.setChecked(!prefs.isPria());
        spAktivitas.setSelection(prefs.getAktivitas());
    }

    private void hitung() {
        String sT = etTinggi.getText().toString().trim();
        String sB = etBerat.getText().toString().trim();
        String sU = etUmur.getText().toString().trim();
        if (sT.isEmpty() || sB.isEmpty() || sU.isEmpty()) {
            tvHasil.setText("Isi tinggi, berat, dan umur dulu ya.");
            return;
        }

        float tinggi = Float.parseFloat(sT);
        float berat = Float.parseFloat(sB);
        int umur = Integer.parseInt(sU);
        boolean pria = rbPria.isChecked();
        int akt = spAktivitas.getSelectedItemPosition();

        // Simpan ke SharedPreferences pakai apply()
        prefs.setTinggi(tinggi);
        prefs.setBerat(berat);
        prefs.setUmur(umur);
        prefs.setPria(pria);
        prefs.setAktivitas(akt);

        // BMR rumus Mifflin-St Jeor
        double bmr = 10 * berat + 6.25 * tinggi - 5 * umur + (pria ? 5 : -161);
        double kalori = bmr * FAKTOR[akt];
        double protein = berat * PROTEIN_PER_KG[akt];

        tvHasil.setText("BMR (kalori dasar): " + Math.round(bmr) + " kkal\n"
                + "Kebutuhan kalori harian: " + Math.round(kalori) + " kkal\n"
                + "Kebutuhan protein harian: " + Math.round(protein) + " gram");
    }

    private String fmt(float v) {
        return (v == (int) v) ? String.valueOf((int) v) : String.valueOf(v);
    }
}