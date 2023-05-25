package de.hsos.cp.newminesweeper.ui;

import android.content.Context;
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

import de.hsos.cp.newminesweeper.R;
import de.hsos.cp.newminesweeper.logic.Spiel;

public class Spielfeld extends View {

    private Kachel[][] kacheln;
    private Grafik grafik;
    private Bar bar;
    private final Paint paint = new Paint();
    private final int MINEN = 3;
    private int minenleft = MINEN;
    private OnTouchDownListener onTouchDownListener;
    private OnLongClickListener onLongClickListener;
    private OnClickListener onClickListener;
    private CountDownTimer countDownTimer;
    private boolean timerRunning = false;
    private boolean actionDownHappened = false;
    private long timeRemaining;
    private float clickXPos;
    private float clickYPos;
    private final int KachelZeilen = 8;
    private final int KachelSpalten = 5;

    public int getMinenleft() {
        return minenleft;
    }



    public Spielfeld(Context context) {
        super(context);
        initializeTimer();
        init();
    }

    public Spielfeld(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initializeTimer();
        init();
    }

    int kachelbreite() {
        return Grafik.getBildschirmBreite() / KachelSpalten;
    }

    public Kachel[][] getKacheln() {
        return kacheln;
    }

    public Bar getBar() {
        return bar;
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
    public int getAnzahlNachbarsminen(Kachel kachel) {
        int nachbarn = 0;
        ArrayList<Kachel> nachbarArr = new ArrayList<Kachel>();
        nachbarArr = getNachbarn(kachel);
        for(Kachel k : nachbarArr){
            if(k.isMine()){
                nachbarn++;
            }
        }
        return nachbarn;
    }

    private void initKacheln(){
        kacheln = new Kachel[KachelSpalten][KachelZeilen];
        for (int x = 0; x <= KachelSpalten - 1; ++x) {
            for (int y = 0; y <= KachelZeilen - 1; ++y) {
                kacheln[x][y] = new Kachel(x, y);
                kacheln[x][y].setxPosDraw(x * (kachelbreite()));
                kacheln[x][y].setyPosDraw((int) (Grafik.getBildschirmHoehe()*0.15) + y * (kachelbreite()));
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
                    kacheln[x][y].setAnzahlMinenNachbarn(getAnzahlNachbarsminen(kacheln[x][y]));
                    grafik.rightGraphic(kacheln[x][y]);
                }
            }
        }
    }

    public void init() {
        Spiel spiel = new Spiel(this);
        bar = new Bar(getContext());
        grafik = new Grafik(this);
        grafik.initBar(bar);
        initKacheln();

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

                        spiel.whatToDo(spielfeld.getClickedKachel(kacheln, x, y));
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
                        if(getClickedKachel(kacheln, x, y).istLetzte(spielfeld)){
                            bar.setBitmap_NewGame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.gamewin_smiley), (int)(Grafik.getBildschirmHoehe()*0.1), (int)(Grafik.getBildschirmHoehe()*0.1), true));
                        }
                        invalidate();
                    }
                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize((int)(Grafik.getBildschirmHoehe()*0.075));
        for (int x = 0; x <= KachelSpalten - 1; ++x) {
            for (int y = 0; y <= KachelZeilen - 1; ++y) {
                canvas.drawBitmap(kacheln[x][y].getBitmap(), kacheln[x][y].getxPosDraw(), kacheln[x][y].getyPosDraw(), paint);
            }
        }
        canvas.drawBitmap(bar.getBitmap_Background(), 0, 0, paint);
        canvas.drawBitmap(bar.getBitmap_NewGame(), bar.getxPosNewGame(), bar.getyPosNewGame(), paint);
        canvas.drawBitmap(bar.getBitmap_BombCount(), bar.getxPosBombCount(), bar.getyPosBombCount(), paint);
        canvas.drawBitmap(bar.getBitmap_Timer(), bar.getxPosTimer(), bar.getyPosTimer(), paint);
        canvas.drawText(bar.getBombCountView().getText().toString(),(int)(Grafik.getBildschirmBreite()*0.095),(int)(Grafik.getBildschirmHoehe()*0.098),paint);
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
            performClick();
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

    @Override
    public boolean performClick() {
        return super.performClick();
    }

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
