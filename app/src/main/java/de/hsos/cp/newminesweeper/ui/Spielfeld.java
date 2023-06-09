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
    private int minenLeft = MINEN;
    private OnTouchDownListener onTouchDownListener;
    private OnLongClickListener onLongClickListener;
    private OnClickListener onClickListener;
    private CountDownTimer countDownTimer;
    private boolean timerRunning = false;

    private boolean actionDownHappened = false;
    private float xPosClick;
    private float yPosClick;
    private final int KachelZeilen = 8;
    private final int KachelSpalten = 5;

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

    public int kachelbreite() {
        return Grafik.getBildschirmBreite() / KachelSpalten;
    }

    public Kachel[][] getKacheln() {
        return kacheln;
    }

    public int getMinenLeft() {
        return minenLeft;
    }

    public Bar getBar() {
        return bar;
    }

    public Paint getPaint() {
        return paint;
    }

    public int getKachelZeilen() {
        return KachelZeilen;
    }

    public int getKachelSpalten() {
        return KachelSpalten;
    }

    public void setKacheln(Kachel[][] kacheln) {
        this.kacheln = kacheln;
    }

    void verteileMinen(){
        int minen = MINEN;
        Random rand = new Random();
        while(minen!=0){
            int x = rand.nextInt(KachelSpalten);
            int y = rand.nextInt(KachelZeilen);
            if(!kacheln[x][y].isMine()) {
                kacheln[x][y].setMine();
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
        ArrayList<Kachel> nachbarArr;
        nachbarArr = getNachbarn(kachel);
        for(Kachel k : nachbarArr){
            if(k.isMine()){
                nachbarn++;
            }
        }
        return nachbarn;
    }

    public void init() {
        Spiel spiel = new Spiel(this);
        bar = new Bar(getContext());
        grafik = new Grafik(this);
        grafik.initBar(bar);
        grafik.initKacheln(this);
        setListener(spiel);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize((int)(Grafik.getBildschirmHoehe()*0.075));
        grafik.drawKacheln(this, canvas);
        grafik.drawBar(this, canvas);
    }
    public void setListener(Spiel spiel){
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
                        spiel.spielzugHandler(spielfeld.getClickedKachel(kacheln, x, y));
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
                    if (!getClickedKachel(kacheln, x, y).isExposed()) {
                        getClickedKachel(kacheln, x, y).setFlag(true);
                        minenLeft--;
                        bar.getMineCountView().setText(String.valueOf(minenLeft));
                        if(getClickedKachel(kacheln, x, y).istLetzte(spielfeld)){
                            bar.setBitmap_NewGame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.gamewin_smiley), (int)(Grafik.getBildschirmHoehe()*0.1), (int)(Grafik.getBildschirmHoehe()*0.1), true));
                        }
                        invalidate();
                    }
                }
            }
        });
    }


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

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            performClick();
            startTimer();
            actionDownHappened = true;
            xPosClick = event.getX();
            yPosClick = event.getY();
            if (onTouchDownListener != null) {
                onTouchDownListener.onTouchDown(this, xPosClick, yPosClick);
            }
        }
        if (action == MotionEvent.ACTION_UP && timerRunning) {
            stopTimer();
            actionDownHappened = false;
            if(onClickListener != null){
                onClickListener.onClick(this, xPosClick, yPosClick);
            }
        }
        if (actionDownHappened && !timerRunning) {
            actionDownHappened = false;
            if(onLongClickListener != null){
                onLongClickListener.onLongClick(this, xPosClick, yPosClick);
            }
        }
        return true;
    }
    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public Kachel getClickedKachel(Kachel[][] kacheln, float xPos, float yPos) {
        for (int x = 0; x <= KachelSpalten - 1; ++x) {
            for (int y = 0; y <= KachelZeilen - 1; ++y) {
                if (xPos >= kacheln[x][y].getxPosDraw() && xPos <= kacheln[x][y].getxPosDraw() + kachelbreite()
                        && yPos >= kacheln[x][y].getyPosDraw() && yPos <= kacheln[x][y].getyPosDraw() + kachelbreite()) {    //Bounding Box
                    return kacheln[x][y];
                }
            }
        }
        return null;
    }

    private void initializeTimer() {
        countDownTimer = new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
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
