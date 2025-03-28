package de.beisenkamp.schiffeVersenken.server;

public class Schiff {
    private int schiffslaenge;
    private int x;
    private int y;
    boolean getroffen[];
    boolean horizontal;
    boolean versenkt;


    public Schiff(int pX, int pY, int pLaenge, boolean pHorizontal)
    {
        schiffslaenge=pLaenge;
        x=pX;
        y=pY;
        horizontal=pHorizontal;
        getroffen = new boolean[pLaenge];
        versenkt= false;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getSchiffslaenge() {
        return schiffslaenge;
    }

    public void treffer(int pX, int pY)
    {
        if(horizontal)
        {
            int i= x-pX+1;
            getroffen[schiffslaenge-i] =true;
        }
        else
        {
            int i= y-pY+1;
            getroffen[schiffslaenge-i] =true;
        }
        versenkt = versenkt();
    }

    public boolean versenkt()
    {
        for(int i =0;  i < getroffen.length; i++ )
        {
            if(!getroffen[i])
            {
                return false;
            }
        }
        return true;
    }
}
