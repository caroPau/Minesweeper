package de.hsos.cp.newminesweeper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import java.util.Random;

/* Spielfeld-Klasse
*  Custom View, auf dem die Kacheln abgebildet werden
*  enthält eine Kachelmatrix und Funktionen zum initialisieren des Spielfelds*/
public class Spielfeld extends View {

    /* Matrix mit Kachelobjekten*/
    private Kachel kacheln[][];
    /* Paint objekt für die onDraw()-Methode*/
    private Paint paint = new Paint();

    /* onClickListener, um Clicks zu verarbeiten*/
    private OnClickListener onClickListener;

    /* Variablen für die Größe des Kachelfelds */

    private int KachelZeilen = 19;

    private int KachelSpalten = 10;

    /* Gibt die Bildschirmbreite in Pixeln zurück*/
    public static int getBildschirmBreite() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    /* Gibt die Bildschirmhöhein Pixeln zurück*/
    public static int getBildschirmHoehe() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    /* Defaultkonstruktor */
    public Spielfeld(Context context){
        super(context);
        init(); //Initialisierung des Spielfelds
        verteileMinen();
    }
    public Spielfeld(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        init(); //Initialisierung des Spielfelds
        verteileMinen();
    }

    int kachelbreite(){
        return getBildschirmBreite()/kacheln[0].length;
    }
/* TODO: Funktion schreiben*/
    public void verteileMinen(){
        Random rand = new Random();
        for(int i = 0; i <= 19; i++){
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            if(!kacheln[x][y].isMine()){
                kacheln[x][y].setIstMine(true);
            }
            else{
                i--;
            }
        }
    }
    /* Initialisierung des Spielfeldes
    *  -> es werden Kachelobjekte angelegt und initialisiert*/
    public void init(){
        this.kacheln = new Kachel[KachelZeilen][KachelSpalten];
        for(int i=0; i<=KachelZeilen-1;++i){
            for(int j=0; j<=KachelSpalten-1;++j){
                kacheln[i][j] = new Kachel();
                kacheln[i][j].setxPos(j*(kachelbreite()));
                kacheln[i][j].setyPos(i*(kachelbreite()));
                kacheln[i][j].setBitmap_hidden(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.kachel),kachelbreite(),kachelbreite(),true));
                kacheln[i][j].setBitmap_flag(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.kachel),kachelbreite(),kachelbreite(),true));
                /*TODO: Randomisieren von anzahlMinenNachbarn und abhängig von der Zahl ein anderes Bitmap zeichnen*/
                kacheln[i][j].setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.exposed_1),kachelbreite(),kachelbreite(),true));
            }
        }
    }

    /* In der onDraw Methode werden die Kachelobjekte gezeichnet*/
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
            for (int i = 0; i <= KachelZeilen-1; ++i) {
                for (int j = 0; j <= KachelSpalten-1; ++j) {
                    canvas.drawBitmap(kacheln[i][j].getBitmap(),kacheln[i][j].getxPos(),kacheln[i][j].getyPos(),paint);
                }
            }
    }

    /* Methoden, um den OnClickListener zu unterstützen*/
    @Override
    public boolean performClick() {
        if (onClickListener != null) {
            onClickListener.onClick(this,0,0);
            return true;
        }
        return super.performClick();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(View view, float x, float y);
    }

    /* Event, das die Toucheingabe verwaltet */
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Koordinaten des Klicks abrufen
                float x = event.getX(); //X-Koordinate des Klicks
                float y = event.getY(); //Y-Koordinate des Klicks
                if(getClickedKachel(kacheln, x, y)!=null) {
                    getClickedKachel(kacheln, x, y).setWurdeAufgedeckt(true); //Angeklickte Kachel auf Aufgedeckt setzen
                    invalidate(); //Befehl, um das Ausführen der onDraw Methode zu erzwingen und neuzuzeichnen
                }
                    if (onClickListener != null) {
                    onClickListener.onClick(this, x, y); // Koordinaten an den OnClickListener übergeben
                }
                break;
        }
        return super.onTouchEvent(event);
    }
        /* Funktion, die das Kachelobjekt zurückgibt, dessen Lage den Klickkoordinaten entspricht
        *  -> wird gebraucht, damit beim Klicken auf eine Kachel eine entsprechende Aktion erfolgen kann*/

        public Kachel getClickedKachel(Kachel[][] kacheln, float x, float y ){
            for (int i = 0; i <= KachelZeilen-1; ++i) {
                for (int j = 0; j <= KachelSpalten-1; ++j) {
                    if(x>=kacheln[i][j].getxPos() && x<=kacheln[i][j].getxPos()+kachelbreite()
                    && y>=kacheln[i][j].getyPos() && y<=kacheln[i][j].getyPos()+kachelbreite()){    //Bounding Box
                    return kacheln[i][j];
                    }
                }
            }
            return null;
        }
    }
