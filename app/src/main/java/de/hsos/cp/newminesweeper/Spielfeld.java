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
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.Random;

/* Spielfeld-Klasse
 *  Custom View, auf dem die Kacheln abgebildet werden
 *  enthält eine Kachelmatrix und Funktionen zum initialisieren des Spielfelds
 *  zudem wird das TouchEvent und der onClick verarbeitet */
public class Spielfeld extends View {

    /* Matrix mit Kachelobjekten*/
    private Kachel [][] kacheln;
    /* Paint objekt für die onDraw()-Methode*/
    private Paint paint = new Paint();

    private final int MINEN = 20;

    /* onClickListener, um Clicks zu verarbeiten*/
    private OnClickListener onClickListener;

    private CountDownTimer countDownTimer;

    private boolean timerRunning = false;

    private boolean actionDownHappened = false;

    private long timeRemaining;

    private float clickXPos;

    private float clickYPos;

    /* Variablen für die Größe des Kachelfelds */

    private int KachelZeilen = 16;

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
    public Spielfeld(Context context) {
        super(context);
        initializeTimer(); //Initialisierung des Timers
        init(); //Initialisierung des Spielfelds
    }

    public Spielfeld(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initializeTimer();
        init(); //Initialisierung des Spielfelds
    }

    int kachelbreite() {
        return getBildschirmBreite() / KachelSpalten;
    }

    public Kachel[][] getKacheln() {
        return kacheln;
    }

    public int getMINEN() {
        return MINEN;
    }

    public float getClickXPos() {
        return clickXPos;
    }

    public float getClickYPos() {
        return clickYPos;
    }

    public int getKachelZeilen() {
        return KachelZeilen;
    }

    public int getKachelSpalten() {
        return KachelSpalten;
    }

    private void setMinen(){
        int minen = MINEN;
        Random rand = new Random();
        while(minen!=0){
            int x = rand.nextInt(KachelSpalten);
            int y = rand.nextInt(KachelZeilen);
            if(!kacheln[x][y].isMine()) {
                kacheln[x][y].setIstMine(true);
                minen--;
            }
        }
    }

    public int getAnzahlNachbarsminen(Kachel kachel, int x, int y) {
        int nachbarn = 0;
        if (x == 0) {
            if (kacheln[x + 1][y].isMine()) {
                nachbarn++;
            }
            if (y == 0) {
                if (kacheln[x][y + 1].isMine()) {
                    nachbarn++;
                }
                if (kacheln[x + 1][y + 1].isMine()) {
                    nachbarn++;
                }
            } else if (y == KachelZeilen - 1) {
                if (kacheln[x][y - 1].isMine()) {
                    nachbarn++;
                }
                if (kacheln[x + 1][y - 1].isMine()) {
                    nachbarn++;
                }
            } else {
                if (kacheln[x][y - 1].isMine()) {
                    nachbarn++;
                }
                if (kacheln[x + 1][y - 1].isMine()) {
                    nachbarn++;
                }
                if (kacheln[x + 1][y + 1].isMine()) {
                    nachbarn++;
                }
                if (kacheln[x][y + 1].isMine()) {
                    nachbarn++;
                }
            }
        } else if (x == KachelSpalten - 1) {
            if (kacheln[x - 1][y].isMine()) {
                nachbarn++;
            }
            if (y == 0) {
                if (kacheln[x - 1][y + 1].isMine()) {
                    nachbarn++;
                }
                if (kacheln[x][y + 1].isMine()) {
                    nachbarn++;
                }
            } else if (y == KachelZeilen - 1) {
                if (kacheln[x - 1][y - 1].isMine()) {
                    nachbarn++;
                }
                if (kacheln[x][y - 1].isMine()) {
                    nachbarn++;
                }
            } else {
                if (kacheln[x - 1][y - 1].isMine()) {
                    nachbarn++;
                }
                if (kacheln[x][y - 1].isMine()) {
                    nachbarn++;
                }
                if (kacheln[x - 1][y + 1].isMine()) {
                    nachbarn++;
                }
                if (kacheln[x][y + 1].isMine()) {
                    nachbarn++;
                }
            }
        }else if(y == 0 && x != KachelSpalten - 1 ) {
            if (kacheln[x - 1][y].isMine()) {
                nachbarn++;
            }
            if (kacheln[x - 1][y + 1].isMine()) {
                nachbarn++;
            }
            if(kacheln[x][y + 1].isMine()){
                nachbarn++;
            }
            if(kacheln[x + 1][y + 1].isMine()){
                nachbarn++;
            }
            if(kacheln[x + 1][y].isMine()){
                nachbarn++;
            }
        }else if(y == KachelZeilen - 1){
            if(kacheln[x - 1][y].isMine()){
                nachbarn++;
            }
            if(kacheln[x - 1][y - 1].isMine()){
                nachbarn++;
            }
            if(kacheln[x][y - 1].isMine()){
                nachbarn++;
            }
            if(kacheln[x + 1][y - 1].isMine()){
                nachbarn++;
            }
            if(kacheln[x + 1][y].isMine()){
                nachbarn++;
            }
        }
        else{
            if(kacheln[x - 1][y - 1].isMine()){
                nachbarn++;
            }
            if(kacheln[x][y - 1].isMine()){
                nachbarn++;
            }
            if(kacheln[x + 1][y - 1].isMine()){
                nachbarn++;
            }
            if(kacheln[x + 1][y].isMine()){
                nachbarn++;
            }
            if(kacheln[x + 1][y + 1].isMine()){
                nachbarn++;
            }
            if(kacheln[x][y + 1].isMine()){
                nachbarn++;
            }
            if(kacheln[x - 1][y + 1].isMine()){
                nachbarn++;
            }
            if(kacheln[x - 1][y].isMine()){
                nachbarn++;
            }
        }
        return nachbarn;
    }

    /* Initialisierung des Spielfeldes
     *  -> es werden Kachelobjekte angelegt und initialisiert*/
    public void init() {
        kacheln = new Kachel[KachelSpalten][KachelZeilen];
        for (int x = 0; x <= KachelSpalten - 1; ++x) {
            for (int y = 0; y <= KachelZeilen - 1; ++y) {
                kacheln[x][y] = new Kachel();
                kacheln[x][y].setxPos(x * (kachelbreite()));
                kacheln[x][y].setyPos((int) (getBildschirmHoehe()*0.15) + y * (kachelbreite()));
                kacheln[x][y].setBitmap_hidden(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.kachel), kachelbreite(), kachelbreite(), true));
                kacheln[x][y].setBitmap_flag(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.flag), kachelbreite(), kachelbreite(), true));
            }
        }
        setMinen();
      //  Kachel[] minen = minenfelder(kacheln);
       // setzeMinen(kacheln, minen);
        for (int x = 0; x <= KachelSpalten - 1; ++x) {
            for (int y = 0; y <= KachelZeilen - 1; ++y) {
                if (kacheln[x][y].isMine()) {
                    kacheln[x][y].setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.exposed_bomb), kachelbreite(), kachelbreite(), true));

                } else {
                    kacheln[x][y].setAnzahlMinenNachbarn(getAnzahlNachbarsminen(kacheln[x][y], x, y));
                    rightGraphic(kacheln[x][y]);
                }
            }
        }
    }

    private void rightGraphic(Kachel kachel) {
        switch (kachel.getAnzahlMinenNachbarn()) {
            case 0:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.exposed_0), kachelbreite(), kachelbreite(), true));
                break;
            case 1:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.exposed_1), kachelbreite(), kachelbreite(), true));
                break;
            case 2:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.exposed_2), kachelbreite(), kachelbreite(), true));
                break;
            case 3:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.exposed_3), kachelbreite(), kachelbreite(), true));
                break;
            case 4:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.exposed_4), kachelbreite(), kachelbreite(), true));
                break;
            case 5:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.exposed_5), kachelbreite(), kachelbreite(), true));
                break;
            case 6:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.exposed_6), kachelbreite(), kachelbreite(), true));
                break;
            case 7:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.exposed_7), kachelbreite(), kachelbreite(), true));
                break;
            case 8:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.exposed_8), kachelbreite(), kachelbreite(), true));
                break;
            default:
        }
    }

    /* In der onDraw Methode werden die Kachelobjekte gezeichnet*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        for (int x = 0; x <= KachelSpalten - 1; ++x) {
            for (int y = 0; y <= KachelZeilen - 1; ++y) {
                canvas.drawBitmap(kacheln[x][y].getBitmap(), kacheln[x][y].getxPos(), kacheln[x][y].getyPos(), paint);
            }
        }
    }

    /* Methoden, um den OnClickListener zu unterstützen*/


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(View view, float x, float y);
    }



    /* Event, das die Toucheingabe verwaltet */
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            startTimer();
            actionDownHappened = true;
            // Koordinaten des Klicks abrufen
            clickXPos = event.getX(); //X-Koordinate des Klicks
            clickYPos = event.getY(); //Y-Koordinate des Klicks
            if (onClickListener != null) {
                onClickListener.onClick(this, clickXPos, clickYPos); // Koordinaten an den OnClickListener übergeben
            }
        }
        if (action == MotionEvent.ACTION_UP && timerRunning) {
            stopTimer();
            actionDownHappened = false;
            if (getClickedKachel(kacheln, clickXPos, clickYPos) != null) {
                if (!getClickedKachel(kacheln, clickXPos, clickYPos).isFlag()) {
                    getClickedKachel(kacheln, clickXPos, clickYPos).setWurdeAufgedeckt(true);
                } else {
                    getClickedKachel(kacheln, clickXPos, clickYPos).setFlag(false);
                }
                invalidate();
            }
        }
        if (actionDownHappened && !timerRunning) {
            actionDownHappened = false;
            if (getClickedKachel(kacheln, clickXPos, clickYPos) != null) {
                if (!getClickedKachel(kacheln, clickXPos, clickYPos).isWurdeAufgedeckt()) {
                    getClickedKachel(kacheln, clickXPos, clickYPos).setFlag(true);
                    invalidate();
                }
            }
        }
        return true;
    }
    /* Funktion, die das Kachelobjekt zurückgibt, dessen Lage den Klickkoordinaten entspricht
     *  -> wird gebraucht, damit beim Klicken auf eine Kachel eine entsprechende Aktion erfolgen kann*/

    public Kachel getClickedKachel(Kachel[][] kacheln, float xpos, float ypos) {
        for (int x = 0; x <= KachelSpalten - 1; ++x) {
            for (int y = 0; y <= KachelZeilen - 1; ++y) {
                if (xpos >= kacheln[x][y].getxPos() && xpos <= kacheln[x][y].getxPos() + kachelbreite()
                        && ypos >= kacheln[x][y].getyPos() && ypos <= kacheln[x][y].getyPos() + kachelbreite()) {    //Bounding Box
                    return kacheln[x][y];
                }
            }
        }
        return null;
    }

    /* Initialisieren des Timers*/
    private void initializeTimer() {
        countDownTimer = new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                timeRemaining = 0;
                timerRunning = false;
            }
        };
    }

    public void startTimer() {
        timerRunning = true;
        countDownTimer.start();
    }

    public void stopTimer() {
        timerRunning = false;
        countDownTimer.cancel();
    }
}
