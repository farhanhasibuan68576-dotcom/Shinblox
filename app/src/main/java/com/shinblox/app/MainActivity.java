package com.shinblox.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;
import android.content.SharedPreferences;

public class MainActivity extends Activity {
    LinearLayout layout;
    SharedPreferences db;
    String pengguna = "";

    void setup() {
        layout = new LinearLayout(this);
        layout.setOrientation(1);
        layout.setPadding(24,24,24,24);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.rgb(25,25,35));

        ScrollView scroll = new ScrollView(this);
        scroll.addView(layout);
        setContentView(scroll);
    }

    void judul(String s, int ukuran) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(ukuran);
        t.setTextColor(Color.WHITE);
        t.setGravity(Gravity.CENTER);
        t.setPadding(8,16,8,16);
        layout.addView(t);
    }

    Button tombol(String s) {
        Button b = new Button(this);
        b.setText(s);
        layout.addView(b);
        return b;
    }

    EditText kolom(String s, boolean password) {
        EditText e = new EditText(this);
        e.setHint(s);
        e.setSingleLine(true);
        e.setTextColor(Color.WHITE);
        e.setHintTextColor(Color.LTGRAY);
        if(password) e.setInputType(129);
        layout.addView(e);
        return e;
    }

    void pesan(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    boolean admin() {
        return db.getBoolean(pengguna+"_admin",false);
    }

    void awal() {
        setup();
        judul("SHINBLOX",36);
        judul("CREATE • PLAY • EXPLORE",16);
        tombol("DAFTAR").setOnClickListener(v -> daftar());
        tombol("LOGIN").setOnClickListener(v -> login());
    }

    void daftar() {
        setup();
        judul("BUAT AKUN",28);
        EditText nama=kolom("Nama pengguna",false);
        EditText sandi=kolom("Password",true);

        tombol("DAFTAR SEKARANG").setOnClickListener(v -> {
            String n=nama.getText().toString().trim();
            String p=sandi.getText().toString();

            if(n.isEmpty() || p.isEmpty()) {
                pesan("Isi semua kolom!");
                return;
            }

            if(db.contains(n+"_password")) {
                pesan("Nama sudah dipakai!");
                return;
            }

            int nomor=db.getInt("jumlah_akun",0)+1;

            db.edit()
                .putString(n+"_password",p)
                .putBoolean(n+"_admin",nomor<=2)
                .putBoolean(n+"_ban",false)
                .putInt(n+"_uang",0)
                .putInt("jumlah_akun",nomor)
                .putString("admin_pertama",
                    nomor==1 ? n : db.getString("admin_pertama",""))
                .apply();

            pengguna=n;

            if(nomor<=2) pesan("Kamu Admin SHINBLOX!");
            else pesan("Akun berhasil dibuat!");

            beranda();
        });

        tombol("KEMBALI").setOnClickListener(v -> awal());
    }

    void login() {
        setup();
        judul("LOGIN",28);
        EditText nama=kolom("Nama pengguna",false);
        EditText sandi=kolom("Password",true);

        tombol("MASUK").setOnClickListener(v -> {
            String n=nama.getText().toString().trim();
            String p=sandi.getText().toString();

            if(!p.equals(db.getString(n+"_password",null))) {
                pesan("Nama atau password salah!");
                return;
            }

            if(db.getBoolean(n+"_ban",false)) {
                pesan("Akun terkena BAN!");
                return;
            }

            pengguna=n;
            beranda();
        });

        tombol("KEMBALI").setOnClickListener(v -> awal());
    }

    void beranda() {
        setup();
        judul("SHINBLOX",32);
        judul("Halo, "+pengguna,20);
        judul("Shinbux: "+db.getInt(pengguna+"_uang",0),18);

        if(admin()) {
            tombol("PANEL ADMIN").setOnClickListener(v -> panelAdmin());
        }

        tombol("LOGOUT").setOnClickListener(v -> {
            pengguna="";
            awal();
        });
    }

    void panelAdmin() {
        setup();
        judul("PANEL ADMIN",30);

        EditText target=kolom("Nama pemain",false);
        EditText jumlah=kolom("Jumlah Shinbux",false);

        tombol("BAN PEMAIN").setOnClickListener(v -> {
            String n=target.getText().toString().trim();

            if(!db.contains(n+"_password")) {
                pesan("Pemain tidak ditemukan!");
            } else if(n.equals(db.getString("admin_pertama",""))) {
                pesan("Akun pertama kebal BAN!");
            } else if(db.getBoolean(n+"_admin",false)) {
                pesan("Admin tidak bisa di-Ban!");
            } else {
                db.edit().putBoolean(n+"_ban",true).apply();
                pesan("Pemain di-Ban!");
            }
        });

        tombol("UNBAN PEMAIN").setOnClickListener(v -> {
            String n=target.getText().toString().trim();

            if(!db.contains(n+"_password")) {
                pesan("Pemain tidak ditemukan!");
            } else {
                db.edit().putBoolean(n+"_ban",false).apply();
                pesan("Pemain di-Unban!");
            }
        });

        tombol("GIVE SHINBUX").setOnClickListener(v -> {
            String n=target.getText().toString().trim();

            if(!db.contains(n+"_password")) {
                pesan("Pemain tidak ditemukan!");
                return;
            }

            try {
                int uang=Integer.parseInt(jumlah.getText().toString());

                if(uang<=0) {
                    pesan("Jumlah tidak valid!");
                    return;
                }

                int lama=db.getInt(n+"_uang",0);
                db.edit().putInt(n+"_uang",lama+uang).apply();
                pesan("Shinbux berhasil diberikan!");
            } catch(Exception e) {
                pesan("Masukkan jumlah yang benar!");
            }
        });

        tombol("KEMBALI").setOnClickListener(v -> beranda());
    }

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        db=getSharedPreferences("SHINBLOX_DATA",MODE_PRIVATE);
        awal();
    }
                          }
