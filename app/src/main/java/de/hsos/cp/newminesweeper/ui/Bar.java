package de.hsos.cp.newminesweeper.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.TextView;


public class Bar {

    private int xPosMineCount;

    private int yPosMineCount;

    private int xPosNewGame;

    private int yPosNewGame;

    private int xPosBarKachel;

    private int yPosBarKachel;

    private final TextView MineCountView;


    private Bitmap Bitmap_MineCount;

    private Bitmap Bitmap_NewGame;

    private Bitmap Bitmap_Background;

    private Bitmap Bitmap_BarKachel;


    public Bar(Context context){
        MineCountView = new TextView(context);
    }

    public int getxPosMineCount(){
        return xPosMineCount;
    }

    public int getyPosMineCount(){
        return yPosMineCount;
    }

    public int getxPosNewGame(){
        return xPosNewGame;
    }

    public int getyPosNewGame(){
        return yPosNewGame;
    }

    public void setxPosMineCount(int xPosMineCount) {
        this.xPosMineCount = xPosMineCount;
    }

    public void setyPosMineCount(int yPosMineCount) {
        this.yPosMineCount = yPosMineCount;
    }

    public void setxPosNewGame(int xPosNewGame) {
        this.xPosNewGame = xPosNewGame;
    }

    public void setyPosNewGame(int yPosNewGame) {
        this.yPosNewGame = yPosNewGame;
    }

    public Bitmap getBitmap_MineCount() {
        return Bitmap_MineCount;
    }

    public void setBitmap_MineCount(Bitmap bitmap_MineCount) {
        Bitmap_MineCount = bitmap_MineCount;
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

    public Bitmap getBitmap_BarKachel() {
        return Bitmap_BarKachel;
    }

    public void setBitmap_BarKachel(Bitmap bitmap_BarKachel) {
        Bitmap_BarKachel = bitmap_BarKachel;
    }

    public int getxPosBarKachel() {
        return xPosBarKachel;
    }

    public void setxPosBarKachel(int xPosBarKachel) {
        this.xPosBarKachel = xPosBarKachel;
    }

    public int getyPosBarKachel() {
        return yPosBarKachel;
    }

    public void setyPosBarKachel(int yPosBarKachel) {
        this.yPosBarKachel = yPosBarKachel;
    }

    public TextView getMineCountView() {
        return MineCountView;
    }



}
