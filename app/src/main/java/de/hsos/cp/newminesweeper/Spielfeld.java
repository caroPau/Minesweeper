package de.hsos.cp.newminesweeper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    private Context context;


    public static int getBildschirmBreite() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getBildschirmHoehe() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public Spielfeld(Context context){
        super(context);
        this.context = context;
        init();
    }
    public Spielfeld(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        init();
    }

    int kachelbreite(){
        return getBildschirmBreite()/kacheln[0].length;
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
        this.kacheln = new Kachel[40][20];
        for(int i=0; i<=39;++i){
            for(int j=0; j<=19;++j){
                kacheln[i][j] = new Kachel(false, this);
                kacheln[i][j].setxPos(j*(kachelbreite()));
                kacheln[i][j].setyPos(i*(kachelbreite()));
                kacheln[i][j].setBitmap_hidden(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.kachel),kachelbreite(),kachelbreite(),true));
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        if(kacheln.length!=0) {
            for (int i = 0; i <= 39; ++i) {
                for (int j = 0; j <= 19; ++j) {
                    canvas.drawBitmap(kacheln[i][j].getBitmap(),kacheln[i][j].getxPos(),kacheln[i][j].getyPos(),paint);
                }
            }
        }
    }
}
