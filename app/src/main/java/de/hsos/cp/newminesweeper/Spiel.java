package de.hsos.cp.newminesweeper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Spiel {

    public Spiel(){}

    public void whatToDo(Spielfeld spielfeld, Kachel kachel){
        if(kachel.isMine()){
           mineHandler(spielfeld);
        }
        else {
            aufdecken(kachel, spielfeld, new ArrayList<Kachel>());
        }
        spielfeld.invalidate();
    }


    private void kachelnAufdecken(Spielfeld spielfeld){
        for(int i = 0; i < spielfeld.getKachelSpalten(); i++){
            for(int j = 0; j < spielfeld.getKachelZeilen(); j++){
                spielfeld.getKacheln()[i][j].setWurdeAufgedeckt(true);
            }
        }
    }
    private void mineHandler(Spielfeld spielfeld){
        kachelnAufdecken(spielfeld);
        if(spielfeld.getMinenleft() != 0){
            spielfeld.getBar().setBitmap_NewGame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.gamelost_smiley), (int)(spielfeld.getBildschirmHoehe()*0.1), (int)(spielfeld.getBildschirmHoehe()*0.1), true));
        }
        else{
            spielfeld.getBar().setBitmap_NewGame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.gamewin_smiley), (int)(spielfeld.getBildschirmHoehe()*0.1), (int)(spielfeld.getBildschirmHoehe()*0.1), true));
        }
    }

    private void aufdecken(Kachel kachel, Spielfeld spielfeld, ArrayList<Kachel>gesehen){
        kachel.setWurdeAufgedeckt(true);
        if(kachel.getAnzahlMinenNachbarn() == 0){
            for (Kachel k:spielfeld.getNachbarn(kachel)) {
                if(!gesehen.contains(k)) {
                    gesehen.add(k);
                    aufdecken(k, spielfeld, gesehen);
                }
            }
        }
    }
}
