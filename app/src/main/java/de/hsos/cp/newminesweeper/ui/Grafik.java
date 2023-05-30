package de.hsos.cp.newminesweeper.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import de.hsos.cp.newminesweeper.R;

public class Grafik {
    private final Spielfeld spielfeld;

    public Grafik(Spielfeld _spielfeld){
        this.spielfeld = _spielfeld;
    }

    public static int getBildschirmBreite() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /* Gibt die Bildschirmhöhein Pixeln zurück*/
    public static int getBildschirmHoehe() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public void initBar(Bar bar){
        bar.getMineCountView().setText(String.valueOf(spielfeld.getMinenLeft()));
        bar.setxPosMineCount(0);
        bar.setyPosMineCount(0);
        bar.setxPosBarKachel((int)(getBildschirmBreite()*0.65));
        bar.setyPosBarKachel(0);
        bar.setxPosNewGame((int)(getBildschirmBreite()*0.4));
        bar.setyPosNewGame((int)(getBildschirmHoehe()*0.026));
        bar.setBitmap_MineCount(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.kachel), (int)(getBildschirmHoehe()*0.18), (int)(getBildschirmHoehe()*0.15), true));
        bar.setBitmap_NewGame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.newgame), (int)(getBildschirmHoehe()*0.1), (int)(getBildschirmHoehe()*0.1), true));
        bar.setBitmap_Background(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.kachel), getBildschirmBreite(), (int)(getBildschirmHoehe()*0.15), true));
        bar.setBitmap_BarKachel(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.kachel), (int)(getBildschirmHoehe()*0.18), (int)(getBildschirmHoehe()*0.15), true));
    }

    public void drawBar(Spielfeld spielfeld, Canvas canvas){
        canvas.drawBitmap(spielfeld.getBar().getBitmap_Background(), 0, 0, spielfeld.getPaint());
        canvas.drawBitmap(spielfeld.getBar().getBitmap_NewGame(), spielfeld.getBar().getxPosNewGame(), spielfeld.getBar().getyPosNewGame(), spielfeld.getPaint());
        canvas.drawBitmap(spielfeld.getBar().getBitmap_MineCount(), spielfeld.getBar().getxPosMineCount(), spielfeld.getBar().getyPosMineCount(), spielfeld.getPaint());
        canvas.drawBitmap(spielfeld.getBar().getBitmap_BarKachel(), spielfeld.getBar().getxPosBarKachel(), spielfeld.getBar().getyPosBarKachel(), spielfeld.getPaint());
        canvas.drawText(spielfeld.getBar().getMineCountView().getText().toString(),(int)(Grafik.getBildschirmBreite()*0.095),(int)(Grafik.getBildschirmHoehe()*0.098),spielfeld.getPaint());

    }

    public void drawKacheln(Spielfeld spielfeld, Canvas canvas){
        for (int x = 0; x <= spielfeld.getKachelSpalten() - 1; ++x) {
            for (int y = 0; y <= spielfeld.getKachelZeilen() - 1; ++y) {
                canvas.drawBitmap(spielfeld.getKacheln()[x][y].getBitmap(), spielfeld.getKacheln()[x][y].getxPosDraw(), spielfeld.getKacheln()[x][y].getyPosDraw(), spielfeld.getPaint());
            }
        }
    }

    public void rightGraphic(Kachel kachel) {
        switch (kachel.getMineCountNeighbours()) {
            case 0:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.exposed_0), spielfeld.kachelbreite(), spielfeld.kachelbreite(), true));
                break;
            case 1:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.exposed_1), spielfeld.kachelbreite(), spielfeld.kachelbreite(), true));
                break;
            case 2:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.exposed_2), spielfeld.kachelbreite(), spielfeld.kachelbreite(), true));
                break;
            case 3:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.exposed_3), spielfeld.kachelbreite(), spielfeld.kachelbreite(), true));
                break;
            case 4:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.exposed_4), spielfeld.kachelbreite(), spielfeld.kachelbreite(), true));
                break;
            case 5:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.exposed_5), spielfeld.kachelbreite(), spielfeld.kachelbreite(), true));
                break;
            case 6:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.exposed_6), spielfeld.kachelbreite(), spielfeld.kachelbreite(), true));
                break;
            case 7:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.spielfeld.getResources(), R.drawable.exposed_7), spielfeld.kachelbreite(), spielfeld.kachelbreite(), true));
                break;
            case 8:
                kachel.setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.spielfeld.getResources(), R.drawable.exposed_8), spielfeld.kachelbreite(), spielfeld.kachelbreite(), true));
                break;
            default:
        }
    }

    public void initKacheln(Spielfeld spielfeld){
        spielfeld.setKacheln(new Kachel[spielfeld.getKachelSpalten()][spielfeld.getKachelZeilen()]);
        for (int x = 0; x <= spielfeld.getKachelSpalten() - 1; ++x) {
            for (int y = 0; y <= spielfeld.getKachelZeilen() - 1; ++y) {
                spielfeld.getKacheln()[x][y] = new Kachel(x, y);
                spielfeld.getKacheln()[x][y].setxPosDraw(x * (spielfeld.kachelbreite()));
                spielfeld.getKacheln()[x][y].setyPosDraw((int) (Grafik.getBildschirmHoehe()*0.15) + y * (spielfeld.kachelbreite()));
                spielfeld.getKacheln()[x][y].setBitmap_hidden(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.kachel), spielfeld.kachelbreite(), spielfeld.kachelbreite(), true));
                spielfeld.getKacheln()[x][y].setBitmap_flag(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.flag), spielfeld.kachelbreite(), spielfeld.kachelbreite(), true));
            }
        }
        spielfeld.verteileMinen();
        for (int x = 0; x <= spielfeld.getKachelSpalten() - 1; ++x) {
            for (int y = 0; y <= spielfeld.getKachelZeilen() - 1; ++y) {
                if (spielfeld.getKacheln()[x][y].isMine()) {
                    spielfeld.getKacheln()[x][y].setBitmap_exposed(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(spielfeld.getResources(), R.drawable.exposed_bomb), spielfeld.kachelbreite(), spielfeld.kachelbreite(), true));

                } else {
                    spielfeld.getKacheln()[x][y].setMineCountNeighbours(spielfeld.getAnzahlNachbarsminen(spielfeld.getKacheln()[x][y]));
                    rightGraphic(spielfeld.getKacheln()[x][y]);
                }
            }
        }
    }


}
