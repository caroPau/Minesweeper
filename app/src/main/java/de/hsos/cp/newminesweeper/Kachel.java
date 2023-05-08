package de.hsos.cp.newminesweeper;

public class Kachel {
    private int xPos;
    private int yPos;
    private boolean istMine;
    private boolean wurdeAufgedeckt;
    private int anzahlMinenNachbarn;

    public Kachel(boolean istMine){
        this.istMine = istMine;
    }

    public boolean isIstMine() {
        return istMine;
    }

    public void setIstMine(boolean istMine) {
        this.istMine = istMine;
    }

    public boolean isWurdeAufgedeckt() {
        return wurdeAufgedeckt;
    }

    public void setWurdeAufgedeckt(boolean wurdeAufgedeckt) {
        this.wurdeAufgedeckt = wurdeAufgedeckt;
    }

    public int getAnzahlMinenNachbarn() {
        return anzahlMinenNachbarn;
    }

    public void setAnzahlMinenNachbarn(int anzahlMinenNachbarn) {
        this.anzahlMinenNachbarn = anzahlMinenNachbarn;
    }

    private void aufdecken(){}

    private void markierenAlsMine(){}

    private void entmarkierenAlsMine(){}
}
