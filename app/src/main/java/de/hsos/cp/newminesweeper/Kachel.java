package de.hsos.cp.newminesweeper;

import android.graphics.Rect;
import android.widget.ToggleButton;

public class Kachel {
    private int xPos = 0;
    private int yPos = 0;
    private Rect button;

    private boolean istMine = false;
    private boolean wurdeAufgedeckt = false;
    private int anzahlMinenNachbarn = 0;

    public Kachel(boolean istMine){
        this.istMine = istMine;
    }

    public Rect getButton() {
        return button;
    }

    public boolean isMine() {
        return istMine;
    }

    public void setIstMine(boolean istMine) {
        this.istMine = istMine;
    }

    public boolean isWurdeAufgedeckt() {
        return wurdeAufgedeckt;
    }

    public void setWurdeAufgedeckt(boolean wurdeAufgedeckt) {
        this.wurdeAufgedeckt = wurdeAufgedeckt;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getAnzahlMinenNachbarn() {
        return anzahlMinenNachbarn;
    }

    public void setAnzahlMinenNachbarn(int anzahlMinenNachbarn) {
        this.anzahlMinenNachbarn = anzahlMinenNachbarn;
    }

    private void aufdecken(){}

    private void markierenAlsMine(){}

    private void entmarkierenAlsMine(){}

     Rect button(Spielfeld spielfeld){
        return button = new Rect(this.xPos, this.yPos, this.xPos + spielfeld.kachelbreite(), this.yPos + spielfeld.kachelbreite());
    }
}
