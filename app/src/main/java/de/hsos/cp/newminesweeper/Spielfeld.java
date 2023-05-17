package de.hsos.cp.newminesweeper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;


import java.util.Random;

public class Spielfeld extends View {
    private Kachel kacheln[][];
    private final int BREITE = 800;

    private Canvas canvas;
    private Bitmap bitmap;
    private Paint paint;

    AttributeSet attr;

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public static int getBildschirmBreite() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getBildschirmHoehe() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public Spielfeld(Context context){
        super(context);
    }
    public Spielfeld(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

    }


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint.setColor(Color.GRAY);
        this.setRight(10);
        this.setLeft(10);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // new width you want
        int newWid = getBildschirmBreite();

        // new height you want
        int newht = getBildschirmHoehe();

        int wM = MeasureSpec.getMode(widthMeasureSpec);
        int wS = MeasureSpec.getSize(widthMeasureSpec);
        int hM = MeasureSpec.getMode(heightMeasureSpec);
        int hS = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        // Measure Width custom view
        if (wM == MeasureSpec.EXACTLY) {
            // Must be of width size
            width = wS;
        } else if (wM == MeasureSpec.AT_MOST) {
            // Can't be bigger than new
            // width and width size
            width = Math.min(newWid, wS);
        } else {
            // Be whatever you want
            width = newWid;
        }

        // Measure Height of custom view
        if (hM == MeasureSpec.EXACTLY) {
            // Must be of height size
            height = hS;
        } else if (hM == MeasureSpec.AT_MOST) {
            // Can't be bigger than new
            // height and height size
            height = Math.min(newht, hS);
        } else {
            // Be whatever you want
            height = newht;
        }

        // for making the desired size
        setMeasuredDimension(width, height);
    }



    public Canvas getCanvas() {
        return canvas;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public Paint getPaint() {
        return paint;
    }

    int kachelbreite(){
        return BREITE/kacheln.length;
    }

    private void verteileMinen(){
        Random rand = new Random();
        for(int i = 0; i <= 20; i++){
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



    public void init(Context context){
        this.kacheln = new Kachel[20][20];
        for(int i=0; i<=19;++i){
            for(int j=0; j<=19;++j){
                kacheln[i][j] = new Kachel(false);
                kacheln[i][j].button(this);
                kacheln[i][j].setxPos(j*(kachelbreite()+10));
                kacheln[i][j].setyPos(i*(kachelbreite()+10));
            }
        }
        this.bitmap = Bitmap.createBitmap(BREITE, BREITE,
                Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(this.bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        this.paint=paint;
    }

    public void erstelleSpielfeld(){
        this.paint.setColor(Color.DKGRAY);
        kacheln[0][0].setxPos(0);
        kacheln[0][0].setyPos(0);
        for(int i = 0; i < kacheln.length; i++){
            for(int j = 0; j < kacheln[0].length; j++){
                this.canvas.drawRect(kacheln[i][j].button(this), this.paint);
            }
        }
    }


}
