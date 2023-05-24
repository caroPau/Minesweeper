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
    private Spiel spiel;

    /* Matrix mit Kachelobjekten*/
    private Kachel [][] kacheln;

    private Bar bar;
    /* Paint objekt für die onDraw()-Methode*/
    private Paint paint = new Paint();

    private final int MINEN = 20;

    private int minenleft = MINEN;

    /* onClickListener, um Clicks zu verarbeiten*/
    private OnTouchDownListener onTouchDownListener;
    private OnLongClickListener onLongClickListener;
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

    public Bar getBar() {
        return bar;
    }

    public int getMINEN() {
        return MINEN;
    }

    public int getMinenleft() {
        return minenleft;
    }

    public void setMinenleft(int minenleft) {
        this.minenleft = minenleft;
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

    public ArrayList<Kachel> getNachbarn(Kachel kachel){
        ArrayList<Kachel> nachbarn = new ArrayList<>();
        for(int x = kachel.getX() - 1; x <= kachel.getX() + 1; x++){
            if(x >= 0 && x < getKachelSpalten()) {
                for (int y = kachel.getY() - 1; y <= kachel.getY() + 1; y++) {
                    if(y >= 0 && y < getKachelZeilen() && kacheln[x][y] != kachel) {
                        nachbarn.add(kacheln[x][y]);
                    }
                }
            }
        }
        return nachbarn;
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

    private void initBar(){
        bar = new Bar(getContext());
        bar.getBombCountView().setText(String.valueOf(minenleft));
        bar.setxPosBombCount(0);
        bar.setyPosBombCount(0);
        bar.setxPosTimer((int)(getBildschirmBreite()*0.65));
        bar.setyPosTimer(0);
        bar.setxPosNewGame((int)(getBildschirmBreite()*0.4));
        bar.setyPosNewGame((int)(getBildschirmHoehe()*0.026));
        bar.setBitmap_BombCount(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.kachel), (int)(getBildschirmHoehe()*0.18), (int)(getBildschirmHoehe()*0.15), true));
        bar.setBitmap_NewGame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.newgame), (int)(getBildschirmHoehe()*0.1), (int)(getBildschirmHoehe()*0.1), true));
        bar.setBitmap_Background(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.kachel), getBildschirmBreite(), (int)(getBildschirmHoehe()*0.15), true));
        bar.setBitmap_Timer(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.kachel), (int)(getBildschirmHoehe()*0.18), (int)(getBildschirmHoehe()*0.15), true));
    }
    private void initKacheln(){
        kacheln = new Kachel[KachelSpalten][KachelZeilen];
        for (int x = 0; x <= KachelSpalten - 1; ++x) {
            for (int y = 0; y <= KachelZeilen - 1; ++y) {
                kacheln[x][y] = new Kachel(x, y);
                kacheln[x][y].setxPosDraw(x * (kachelbreite()));
                kacheln[x][y].setyPosDraw((int) (getBildschirmHoehe()*0.15) + y * (kachelbreite()));
                kacheln[x][y].setBitmap_hidden(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.kachel), kachelbreite(), kachelbreite(), true));
                kacheln[x][y].setBitmap_flag(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.flag), kachelbreite(), kachelbreite(), true));
            }
        }
        setMinen();
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

    public void init() {
        Spiel spiel = new Spiel();
        this.setOnTouchDownListener(new Spielfeld.OnTouchDownListener() {
            @Override
            public void onTouchDown(Spielfeld spielfeld, float x, float y) {

            }
        });
        this.setOnClickListener(new Spielfeld.OnClickListener(){
            @Override
            public void onClick(Spielfeld spielfeld, float x, float y) {
                if (getClickedKachel(kacheln, x, y) != null) {
                    if (!getClickedKachel(kacheln, x, y).isFlag()) {

                        spiel.whatToDo(spielfeld, spielfeld.getClickedKachel(kacheln, x, y));
                    } else {
                        getClickedKachel(kacheln, x, y).setFlag(false);
                    }

                    invalidate();
                }
            }
        });
        this.setOnLongClickListener(new Spielfeld.OnLongClickListener(){
            @Override
            public void onLongClick(Spielfeld spielfeld, float x, float y) {
                if (getClickedKachel(kacheln, x, y) != null) {
                    if (!getClickedKachel(kacheln, x, y).isWurdeAufgedeckt()) {
                        getClickedKachel(kacheln, x, y).setFlag(true);
                        minenleft--;
                        bar.getBombCountView().setText(String.valueOf(minenleft));
                        invalidate();
                    }
                }
            }
        });
        initBar();
        initKacheln();
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
        paint.setTextSize((int)(getBildschirmHoehe()*0.075));
        for (int x = 0; x <= KachelSpalten - 1; ++x) {
            for (int y = 0; y <= KachelZeilen - 1; ++y) {
                canvas.drawBitmap(kacheln[x][y].getBitmap(), kacheln[x][y].getxPosDraw(), kacheln[x][y].getyPosDraw(), paint);
            }
        }
        canvas.drawBitmap(bar.getBitmap_Background(), 0, 0, paint);
        canvas.drawBitmap(bar.getBitmap_NewGame(), bar.getxPosNewGame(), bar.getyPosNewGame(), paint);
        canvas.drawBitmap(bar.getBitmap_BombCount(), bar.getxPosBombCount(), bar.getyPosBombCount(), paint);
        canvas.drawBitmap(bar.getBitmap_Timer(), bar.getxPosTimer(), bar.getyPosTimer(), paint);
        canvas.drawText(bar.getBombCountView().getText().toString(),(int)(getBildschirmBreite()*0.095),(int)(getBildschirmHoehe()*0.098),paint);
    }

    /* Methoden, um den OnClickListener zu unterstützen*/


    public void setOnTouchDownListener(OnTouchDownListener onTouchDownListener) {
        this.onTouchDownListener = onTouchDownListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnTouchDownListener {
        void onTouchDown(Spielfeld spielfeld, float x, float y);
    }

    public interface OnClickListener{
        void onClick(Spielfeld spielfeld, float x, float y);
    }
    public interface OnLongClickListener{
        void onLongClick(Spielfeld spielfeld, float x, float y);
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
            if (onTouchDownListener != null) {
                onTouchDownListener.onTouchDown(this, clickXPos, clickYPos); // Koordinaten an den OnClickListener übergeben
            }
        }
        if (action == MotionEvent.ACTION_UP && timerRunning) {
            stopTimer();
            actionDownHappened = false;
            if(onClickListener != null){
                onClickListener.onClick(this, clickXPos, clickYPos);
            }
        }
        if (actionDownHappened && !timerRunning) {
            actionDownHappened = false;
            if(onLongClickListener != null){
                onLongClickListener.onLongClick(this, clickXPos, clickYPos);
            }
        }
        return true;
    }
    /* Funktion, die das Kachelobjekt zurückgibt, dessen Lage den Klickkoordinaten entspricht
     *  -> wird gebraucht, damit beim Klicken auf eine Kachel eine entsprechende Aktion erfolgen kann*/

    public Kachel getClickedKachel(Kachel[][] kacheln, float xpos, float ypos) {
        for (int x = 0; x <= KachelSpalten - 1; ++x) {
            for (int y = 0; y <= KachelZeilen - 1; ++y) {
                if (xpos >= kacheln[x][y].getxPosDraw() && xpos <= kacheln[x][y].getxPosDraw() + kachelbreite()
                        && ypos >= kacheln[x][y].getyPosDraw() && ypos <= kacheln[x][y].getyPosDraw() + kachelbreite()) {    //Bounding Box
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
