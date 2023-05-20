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
import android.widget.FrameLayout;
import android.widget.ImageView;


import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

public class Spielfeld extends View {
    private Kachel kacheln[][];
    private Paint paint = new Paint();


    public static int getBildschirmBreite() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getBildschirmHoehe() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public Spielfeld(Context context){
        super(context);
        init();
    }
    public Spielfeld(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        init();
    }

    int kachelbreite(){
        return getBildschirmBreite()/kacheln.length;
    }

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

    public void init(){
        this.kacheln = new Kachel[20][20];
        for(int i=0; i<=19;++i){
            for(int j=0; j<=19;++j){
                kacheln[i][j] = new Kachel(false, this);
                kacheln[i][j].setxPos(j*(kachelbreite()+10));
                kacheln[i][j].setyPos(i*(kachelbreite()+10));
                kacheln[i][j].button();
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        if(kacheln.length!=0) {
            for (int i = 0; i <= 19; ++i) {
                for (int j = 0; j <= 19; ++j) {
                    canvas.drawRect(this.kacheln[i][j].getButton(), paint);
                }
            }
        }
    }
}
