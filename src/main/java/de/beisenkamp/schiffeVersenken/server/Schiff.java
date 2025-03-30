package de.beisenkamp.schiffeVersenken.server;

import javax.swing.*;

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

    /**
     * aktualisiert die Arrayfelder, falls das Schiff getroffen wurde
     */
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

    /**
     *Überprüft, ob das Schiff versenkt wurde
     */

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

    /**
     * Überprüft, ob das Schiff noch nicht getroffen wurde
     */
    public boolean unbeschaedigt()
    {
        for(int i =0; i<getroffen.length; i++)
        {
            if(getroffen[i])
            {
                return false;
            }
        }
        return true;
    }

    /**
     * pX, pY : Koordinaten für den zustand des Schiffteils
     * Outputs:
     * 1 = Schiff bereit versenkt
     * 2 = Schiffsteil getroffen
     * 3 = unbeschädigtes Schiffsteil
     */

    public int getZustand(int pX, int pY)
    {
        if(this.versenkt)
        {
            return 1;
        }
        else if(unbeschaedigt())
        {
            return 3;
        }
        else
        {
            if(this.horizontal)
            {
                if(getroffen[schiffslaenge-(x-pX+1)])
                {
                   return 2;
                }
                else
                {
                    return 3;
                }
            }
            else
            {
                if(getroffen[schiffslaenge-(y-pY+1)])
                {
                    return 2;
                }
                else
                {
                    return 3;
                }
            }
        }
    }
}
