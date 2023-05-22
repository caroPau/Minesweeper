package de.hsos.cp.newminesweeper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
/* Kachel Klasse
*  enthält Koordinaten, Zustandsvariablen und Bitmaps des Kachelobjekts,
*  die je nach Zustand gezeichnet werden
*  Das Zeichnen erfolgt in der onDraw()-Methode der Spielfeldklasse */
public class Kachel {

    /* Koordinaten der Kachel */
    private int xPos = 0;
    private int yPos = 0;

    /* Bitmaps
    *  für jeden Zustand (nicht aufgedeckt, flag gesetzt oder aufgedeckt) */
    private Bitmap Bitmap_hidden;
    private Bitmap Bitmap_flag;
    private Bitmap Bitmap_exposed;
    /* Bitmap, das im Endeffekt gezeichnet wird*/
    private Bitmap drawBitmap;

    /* Zustandsvariablen */

    private boolean istMine = false;
    private boolean wurdeAufgedeckt = false;

    private boolean isFlag = false;
    private int anzahlMinenNachbarn = 0;

    /* Konstruktor */
    public Kachel(){

    }
    /* getBitmap()
    *  -> gibt abhängig vom Zustand des Objekts das Bitmap zurück,
    *     das dargestellt werden soll*/
    public Bitmap getBitmap(){

        if(wurdeAufgedeckt){
            drawBitmap = Bitmap_exposed;
        } else if (isFlag) {
            drawBitmap = Bitmap_flag;
        } else{
            drawBitmap = Bitmap_hidden;
        }
        return drawBitmap;
    }
    /* Initialisieren des Bitmaps, das dargestellt wird, wenn aufgedeckt*/
    public void setBitmap_exposed(Bitmap btmp){
        Bitmap_exposed = btmp;
    }
    /* Initialisieren des Bitmaps, das dargestellt wird, wenn nicht aufgedeckt*/
    public void setBitmap_hidden(Bitmap btmp){
        Bitmap_hidden = btmp;
    }
    /* Initialisieren des Bitmaps, das dargestellt wird, wenn Flag gesetzt*/
    public void setBitmap_flag(Bitmap btmp){
        Bitmap_flag = btmp;
    }
    /* Gibt zurück, ob sich hinter der Kachel eine Mine versteckt */
    public boolean isMine() {
        return istMine;
    }
    /* Setzt, ob sich hinter der Kachel eine Mine versteckt */
    public void setIstMine(boolean istMine) {
        this.istMine = istMine;
    }
    /* Gibt zurück, ob die Kachel aufgedeckt wurde */
    public boolean isWurdeAufgedeckt() {
        return wurdeAufgedeckt;
    }
    /* Setzt den Zustand, dass die Kachel aufgedeckt wurde*/
    public void setWurdeAufgedeckt(boolean wurdeAufgedeckt) {
        this.wurdeAufgedeckt = wurdeAufgedeckt;
    }
    /* Gibt zurück, ob für die Kachel eine Flagge gesetzt wurde*/
    public boolean isFlag(){
        return this.isFlag;
    }
    /* Setzt eine Flagge*/
    public void setFlag(boolean isFlag){
        this.isFlag = isFlag;
    }
    /* Setzt x-Koordinate der Kachel auf dem Bildschirm */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }
    /* Setzt y-Koordinate der Kachel auf dem Bildschirm*/
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
    /* Gibt die Anzahl der Minen auf den Nachbarfeldern zurück*/
    public int getAnzahlMinenNachbarn() {
        return anzahlMinenNachbarn;
    }
    /* Setzt die Anzahl der Minen auf den Nachbarfeldern */
    public void setAnzahlMinenNachbarn(int anzahlMinenNachbarn) {
        this.anzahlMinenNachbarn = anzahlMinenNachbarn;
    }
    /* Gibt die x-Koordinate der Kachel auf dem Bildschirm zurück*/
    public int getxPos(){
        return xPos;
    }
    /* Gibt die y-Koordinate der Kachel auf dem Bildschirm zurück*/
    public int getyPos(){
        return yPos;
    }
    private void aufdecken(){}

    private void markierenAlsMine(){}

    private void entmarkierenAlsMine(){}

}
