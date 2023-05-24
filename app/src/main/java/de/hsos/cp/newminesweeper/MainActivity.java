package de.hsos.cp.newminesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private Spielfeld spielfeld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Spielfeld spielfeld = new Spielfeld(this);
        /* OnClickListener registrieren */
        spielfeld = new Spielfeld(this);


        setContentView(R.layout.activity_main);
    }
}