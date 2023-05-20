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
    private Spielfeld spielfeld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spielfeld spielfeld = new Spielfeld(this);
        //System.out.println("Welche Groesse soll das Spielfeld haben?");
       // int groesse = scanner.nextInt();
        //LinearLayout layout = new LinearLayout(this);
        spielfeld.verteileMinen();
        setContentView(R.layout.activity_main);


        //spielfeld.getCanvas().drawColor(Color.GRAY);
    }
}