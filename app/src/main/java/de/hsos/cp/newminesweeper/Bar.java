package de.hsos.cp.newminesweeper;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.TextView;

public class Bar {

    private int xPosBombCount;

    private int yPosBombCount;

    private int xPosNewGame;

    private int yPosNewGame;

    private int xPosTimer;

    private int yPosTimer;

    private TextView BombCountView;

    private TextView TimerView;

    private Bitmap Bitmap_BombCount;

    private Bitmap Bitmap_NewGame;

    private Bitmap Bitmap_Background;

    private Bitmap Bitmap_Timer;


    public Bar(Context context){
    BombCountView = new TextView(context);
    TimerView = new TextView(context);
    }

    public int getxPosBombCount(){
        return xPosBombCount;
    }

    public int getyPosBombCount(){
        return yPosBombCount;
    }

    public int getxPosNewGame(){
        return xPosNewGame;
    }

    public int getyPosNewGame(){
        return yPosNewGame;
    }

    public void setxPosBombCount(int xPosBombCount) {
        this.xPosBombCount = xPosBombCount;
    }

    public void setyPosBombCount(int yPosBombCount) {
        this.yPosBombCount = yPosBombCount;
    }

    public void setxPosNewGame(int xPosNewGame) {
        this.xPosNewGame = xPosNewGame;
    }

    public void setyPosNewGame(int yPosNewGame) {
        this.yPosNewGame = yPosNewGame;
    }

    public Bitmap getBitmap_BombCount() {
        return Bitmap_BombCount;
    }

    public void setBitmap_BombCount(Bitmap bitmap_BombCount) {
        Bitmap_BombCount = bitmap_BombCount;
    }

    public Bitmap getBitmap_NewGame() {
        return Bitmap_NewGame;
    }

    public void setBitmap_NewGame(Bitmap bitmap_NewGame) {
        Bitmap_NewGame = bitmap_NewGame;
    }

    public Bitmap getBitmap_Background() {
        return Bitmap_Background;
    }

    public void setBitmap_Background(Bitmap bitmap_Background) {
        Bitmap_Background = bitmap_Background;
    }

    public Bitmap getBitmap_Timer() {
        return Bitmap_Timer;
    }

    public void setBitmap_Timer(Bitmap bitmap_Timer) {
        Bitmap_Timer = bitmap_Timer;
    }

    public int getxPosTimer() {
        return xPosTimer;
    }

    public void setxPosTimer(int xPosTimer) {
        this.xPosTimer = xPosTimer;
    }

    public int getyPosTimer() {
        return yPosTimer;
    }

    public void setyPosTimer(int yPosTimer) {
        this.yPosTimer = yPosTimer;
    }

    public TextView getBombCountView() {
        return BombCountView;
    }

    public void setBombCountView(TextView bombCountView) {
        BombCountView = bombCountView;
    }

    public TextView getTimerView() {
        return TimerView;
    }

    public void setTimerView(TextView timerView) {
        TimerView = timerView;
    }
}
