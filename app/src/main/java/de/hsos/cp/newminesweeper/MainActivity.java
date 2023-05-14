package de.hsos.cp.newminesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    Scanner scanner = new Scanner(System.in);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spielfeld spielfeld = new Spielfeld();
        //System.out.println("Welche Groesse soll das Spielfeld haben?");
       // int groesse = scanner.nextInt();
        spielfeld.init(this);
        LinearLayout layout = new LinearLayout(this);
        layout.addView(spielfeld.getImageView());


        spielfeld.erstelleSpielfeld();
        //setContentView(R.layout.activity_main);
        setContentView(layout);
        //spielfeld.getCanvas().drawColor(Color.GRAY);
    }
}