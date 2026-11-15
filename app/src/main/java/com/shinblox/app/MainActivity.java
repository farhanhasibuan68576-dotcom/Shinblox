
package com.shinblox.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {

    LinearLayout layout;

    void addText(String text, int size) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextSize(size);
        t.setTextColor(Color.WHITE);
        t.setGravity(Gravity.CENTER);
        t.setPadding(10, 20, 10, 20);
        layout.addView(t);
    }

    void addButton(String text) {
        Button b = new Button(this);
        b.setText(text);
        layout.addView(b);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = new LinearLayout(this);
        layout.setOrientation(1);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(20, 20, 20, 20);
        layout.setBackgroundColor(Color.rgb(25, 25, 35));

        addText("SHINBLOX", 34);
        addText("CREATE • PLAY • EXPLORE", 15);
        addText("Selamat datang di SHINBLOX!", 20);

        addButton("LOGIN");
        addButton("DAFTAR");
        addButton("JELAJAHI GAME");
        addButton("AVATAR");
        addButton("SHINBUX");

        setContentView(layout);
    }
}
