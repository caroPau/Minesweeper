package de.hsos.cp.newminesweeper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ToggleButton;

import java.util.Random;

public class Spielfeld {
    private Kachel kacheln[][];
    private final int BREITE = 800;

    private MyView imgView;
    private Canvas canvas;
    private Bitmap bitmap;
    private Paint paint;

    AttributeSet attr;

    public Spielfeld(){

    }

    public MyView getImageView() {
        return imgView;
    }

    public Canvas getCanvas() {
        return canvas;
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
        this.imgView = new MyView(context,this.bitmap,this.paint,50,50);
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
