package de.hsos.cp.newminesweeper.ui;

import android.graphics.Bitmap;

public class Kachel {
    private final int x;
    private final int y;

    private int xPosDraw = 0;
    private int yPosDraw = 0;

    private Bitmap Bitmap_hidden;
    private Bitmap Bitmap_flag;
    private Bitmap Bitmap_exposed;

    private boolean isMine = false;
    private boolean isExposed = false;
    private boolean isFlag = false;
    private int mineCountNeighbours = 0;

    /* Konstruktor */
    public Kachel(int x, int y){
        this.x = x;
        this.y = y;
    }

    /* Getter */
    public int getX() {return x; }
    public int getY() {
        return y;
    }
    public boolean isMine() {
        return isMine;
    }
    public boolean isExposed() {
        return isExposed;
    }
    public boolean isFlag(){
        return this.isFlag;
    }
    public int getMineCountNeighbours() {
        return mineCountNeighbours;
    }
    public int getxPosDraw(){
        return xPosDraw;
    }
    public int getyPosDraw(){
        return yPosDraw;
    }

    /* Setter */
    public void setBitmap_exposed(Bitmap btmp){
        Bitmap_exposed = btmp;
    }
    public void setBitmap_hidden(Bitmap btmp){
        Bitmap_hidden = btmp;
    }
    public void setBitmap_flag(Bitmap btmp){
        Bitmap_flag = btmp;
    }
    public void setMine() {
        this.isMine = true;
    }
    public void setWurdeAufgedeckt() {
        this.isExposed = true;
    }
    public void setFlag(boolean isFlag){
        this.isFlag = isFlag;
    }
    public void setxPosDraw(int xPosDraw) {
        this.xPosDraw = xPosDraw;
    }
    public void setyPosDraw(int yPosDraw) {
        this.yPosDraw = yPosDraw;
    }
    public void setMineCountNeighbours(int mineCountNeighbours) {
        this.mineCountNeighbours = mineCountNeighbours;
    }

    public Bitmap getBitmap(){
        Bitmap drawBitmap;
        if(isExposed){
            drawBitmap = Bitmap_exposed;
        } else if (isFlag) {
            drawBitmap = Bitmap_flag;
        } else{
            drawBitmap = Bitmap_hidden;
        }
        return drawBitmap;
    }
    /* Methoden */
    public boolean istLetzte(Spielfeld spielfeld){
        int hidden = 0;
        for(int x = 0; x < spielfeld.getKachelSpalten(); x++){
            for (int y = 0; y < spielfeld.getKachelZeilen(); y++){
                if(!spielfeld.getKacheln()[x][y].isExposed && !spielfeld.getKacheln()[x][y].isFlag){
                    hidden++;
                }
            }
        }
       if(hidden > 1){
           return false;
       }
       return true;
    }

}
