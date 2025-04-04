package de.beisenkamp.schiffeVersenken.server;

public class Schiff {
    private int schiffslaenge;
    private int x;
    private int y;
    boolean[] getroffen;
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
        for(int i = 0; i < pLaenge; i++) {
            getroffen[i] = false;
        }
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

    //setzt die Arrayfelder
    public void treffer(int pX, int pY)
    {
        if(horizontal)
        {
            int i= pX-x;
            getroffen[i] =true;
            System.out.println("treffer horizontal X: "+x+" pX: "+pX);
        }
        else
        {
            int i= pY-y;
            getroffen[i] =true;
            System.out.println("treffer vertikal Y: "+y+" pY: "+pY);
        }
        versenkt = versenkt();
    }

    public boolean versenkt()
    {
        System.out.println("Getroffen ARRAY: "+getroffen[0]);
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
