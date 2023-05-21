package de.hsos.cp.newminesweeper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class Kachel{
    private int xPos = 0;
    private int yPos = 0;
    Spielfeld spielfeld;
    private Rect button;

    private Bitmap Bitmap_hidden;
    private Bitmap Bitmap_exposed;

    public Bitmap getBitmap(){
        if(isWurdeAufgedeckt()){
            return Bitmap_exposed;
        }
        else{
            return Bitmap_hidden;
        }
    }
    public void setBitmap_exposed(Bitmap btmp){
        Bitmap_exposed = btmp;
    }

    public void setBitmap_hidden(Bitmap btmp){
        Bitmap_hidden = btmp;
    }

    private boolean istMine = false;
    private boolean wurdeAufgedeckt = false;
    private int anzahlMinenNachbarn = 0;

    public Kachel( boolean istMine, Spielfeld spielfeld){
        this.istMine = istMine;
        this.spielfeld = spielfeld;
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

    public int getxPos(){
        return xPos;
    }

    public int getyPos(){
        return yPos;
    }
    private void aufdecken(){}

    private void markierenAlsMine(){}

    private void entmarkierenAlsMine(){}

}
