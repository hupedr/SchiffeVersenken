package de.beisenkamp.schiffeVersenken.server;

import nrw.abiturklassen.datenstruktur.linear.List;

public class Spielfeld
{
    List<Schiff> schiffliste;
    List<String> schussliste;
    public Spielfeld()
    {
        schiffliste = new List<>();
        schussliste = new List<>();
    }


    // (x/y)   <- Position


    public int schiffPlatzieren(String Position1, String Position2)
    {
        //Koordinaten in Int umwandeln ü
        int pos1x = (int)Position1.charAt(0);
        int pos1y = (int)Position1.charAt(2);
        int pos2x = (int)Position2.charAt(0);
        int pos2y = (int)Position2.charAt(2);
        if(pos1x<pos2x)
        //1.Koordinate = größte
        {
            pos1x = (int)Position2.charAt(0);
            pos2x = (int)Position1.charAt(0);
        }
        if(pos1y<pos2y)
        {
             pos1y = (int)Position2.charAt(2);
             pos2y = (int)Position1.charAt(2);
        }
        if(pos1y==pos2y)
        {
            schiffliste.append(new Schiff(pos1x, pos1y, pos1x-pos2x+1, true));
            return 1;
        }
        else if(pos1x==pos2x)
        {
            schiffliste.append(new Schiff(pos1x, pos1y, pos1y-pos2y+1, false));
            return 1;
        }
        else
        {
            return 2;
        }

    }

    public boolean verarbeiteSchuss(int pX, int pY)
    {
        schiffliste.toFirst();
        schussliste.append(pX+"|"+pY);
        while(schiffliste.hasAccess())
        {
            Schiff schiff = schiffliste.getContent();
            if(schiff.horizontal)
            {
                if(schiff.getX()>=pX && schiff.getX()-(schiff.getSchiffslaenge()-1)<=pX && pY == schiff.getY())
                {
                    schiff.treffer(pX, pY);
                    return true;
                }
            }
            else
            {
                if(schiff.getY()>=pY && schiff.getY()-(schiff.getSchiffslaenge()-1)<=pY && pX == schiff.getX())
                {
                    schiff.treffer(pX, pY);
                    return true;
                }
            }

        }
        return false;
    }
}
