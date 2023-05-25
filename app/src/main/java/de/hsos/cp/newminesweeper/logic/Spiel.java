package de.hsos.cp.newminesweeper.logic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.ArrayList;

import de.hsos.cp.newminesweeper.R;
import de.hsos.cp.newminesweeper.ui.Grafik;
import de.hsos.cp.newminesweeper.ui.Kachel;
import de.hsos.cp.newminesweeper.ui.Spielfeld;


public class Spiel {
    private final Spielfeld spielfeld;

    public Spiel(Spielfeld spielfeld){
        this.spielfeld = spielfeld;
    }

    public void spielzugHandler(Kachel kachel){
        if(kachel.isMine()){
           mineHandler();
        }
        else if(kachel.istLetzte(spielfeld)){
            kachel.setWurdeAufgedeckt();
            spielfeld.getBar().setBitmap_NewGame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.gamewin_smiley), (int)(Grafik.getBildschirmHoehe()*0.1), (int)(Grafik.getBildschirmHoehe()*0.1), true));
        }
        else {
            aufdecken(kachel, new ArrayList<Kachel>());
        }
        spielfeld.invalidate();
    }


    private void alleKachelnAufdecken(){
        for(int i = 0; i < spielfeld.getKachelSpalten(); i++){
            for(int j = 0; j < spielfeld.getKachelZeilen(); j++){
                spielfeld.getKacheln()[i][j].setWurdeAufgedeckt();
            }
        }
    }
    private void mineHandler(){
        alleKachelnAufdecken();
        spielfeld.getBar().setBitmap_NewGame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.gamelost_smiley), (int)(Grafik.getBildschirmHoehe()*0.1), (int)(Grafik.getBildschirmHoehe()*0.1), true));

    }

    private void aufdecken(Kachel kachel, ArrayList<Kachel>gesehen){
        kachel.setWurdeAufgedeckt();
        if(kachel.getMineCountNeighbours() == 0){
            for (Kachel k:spielfeld.getNachbarn(kachel)) {
                if(!gesehen.contains(k)) {
                    gesehen.add(k);
                    aufdecken(k, gesehen);
                }
            }
        }
    }


}
