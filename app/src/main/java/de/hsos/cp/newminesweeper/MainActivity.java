package de.hsos.cp.newminesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import de.hsos.cp.newminesweeper.ui.Spielfeld;

public class MainActivity extends AppCompatActivity {
    private Spielfeld spielfeld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spielfeld = new Spielfeld(this);


        setContentView(R.layout.activity_main);
    }
}