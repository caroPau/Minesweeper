package de.hsos.cp.newminesweeper;

import android.content.Context;

public class Spiel {
    private Spielfeld spielfeld;
    private Context context;

    public Spiel(Spielfeld _spielfeld, Context _context){
        spielfeld = _spielfeld;
        context = _context;
    }
    public void spielen(){
        spielfeld = new Spielfeld(context);
        while(true){
            Kachel kachel = spielfeld.getClickedKachel(spielfeld.getKacheln(), spielfeld.getClickXPos(), spielfeld.getClickYPos());

        }
    }
}
