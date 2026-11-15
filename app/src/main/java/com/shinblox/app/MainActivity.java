
package com.shinblox.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;
import android.content.SharedPreferences;

public class MainActivity extends Activity {

    LinearLayout layout;
    SharedPreferences akun;

    void setup() {
        layout = new LinearLayout(this);
        layout.setOrientation(1);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(25, 25, 25, 25);
        layout.setBackgroundColor(Color.rgb(25, 25, 35));
        setContentView(layout);
    }

    void judul(String tulisan, int ukuran) {
        TextView t = new TextView(this);
        t.setText(tulisan);
        t.setTextSize(ukuran);
        t.setTextColor(Color.WHITE);
        t.setGravity(Gravity.CENTER);
        t.setPadding(10, 20, 10, 20);
        layout.addView(t);
    }

    Button tombol(String tulisan) {
        Button b = new Button(this);
        b.setText(tulisan);
        layout.addView(b);
        return b;
    }

    EditText kolom(String petunjuk, boolean password) {
        EditText e = new EditText(this);
        e.setHint(petunjuk);
        e.setSingleLine(true);
        e.setTextColor(Color.WHITE);
        e.setHintTextColor(Color.LTGRAY);
        if (password) e.setInputType(129);
        layout.addView(e);
        return e;
    }

    void beranda() {
        setup();
        judul("SHINBLOX", 34);
        judul("CREATE • PLAY • EXPLORE", 15);
        judul("Selamat datang!", 20);

        tombol("LOGIN").setOnClickListener(v -> login());
        tombol("DAFTAR").setOnClickListener(v -> daftar());
        tombol("JELAJAHI GAME").setOnClickListener(v ->
            pesan("Fitur game segera hadir!"));
        tombol("AVATAR").setOnClickListener(v ->
            pesan("Fitur avatar segera hadir!"));
        tombol("SHINBUX").setOnClickListener(v ->
            pesan("Fitur Shinbux segera hadir!"));
    }

    void daftar() {
        setup();
        judul("BUAT AKUN", 28);
        EditText nama = kolom("Nama pengguna", false);
        EditText sandi = kolom("Password", true);

        tombol("DAFTAR SEKARANG").setOnClickListener(v -> {
            String n = nama.getText().toString().trim();
            String s = sandi.getText().toString();

            if (n.isEmpty() || s.isEmpty()) {
                pesan("Isi semua kolom!");
            } else if (akun.contains(n)) {
                pesan("Nama pengguna sudah digunakan!");
            } else {
                akun.edit().putString(n, s).apply();
                pesan("Akun berhasil dibuat!");
                beranda();
            }
        });

        tombol("KEMBALI").setOnClickListener(v -> beranda());
    }

    void login() {
        setup();
        judul("LOGIN SHINBLOX", 28);
        EditText nama = kolom("Nama pengguna", false);
        EditText sandi = kolom("Password", true);

        tombol("MASUK").setOnClickListener(v -> {
            String n = nama.getText().toString().trim();
            String s = sandi.getText().toString();

            if (s.equals(akun.getString(n, null))) {
                pesan("Login berhasil!");
                beranda();
            } else {
                pesan("Nama pengguna atau password salah!");
            }
        });

        tombol("KEMBALI").setOnClickListener(v -> beranda());
    }

    void pesan(String tulisan) {
        Toast.makeText(this, tulisan, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        akun = getSharedPreferences("akun_shinblox", MODE_PRIVATE);
        beranda();
    }
    }
