package de.beisenkamp.schiffeVersenken.server;
import nrw.abiturklassen.datenstruktur.linear.List;
public class Spielfeld
{
    List<Schiff> schiffliste;
    List<String> schussliste;
    int anzahlSchiffe;
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
            int pLaenge = pos1x-pos2x+1;
            //Länge muss <= 5 sein
            if(pLaenge <= 5)
            {
                schiffliste.append(new Schiff(pos1x, pos1y, pLaenge, true));
                anzahlSchiffe++;
                return 1;
            }
            else
            {
                return 3;
            }
        }
        else if(pos1x==pos2x)
        {
            int pLaenge = pos1y - pos2y + 1;
            //Länge muss <= 5 sein
            if(pLaenge <= 5) {
                schiffliste.append(new Schiff(pos1x, pos1y, pLaenge, false));
                anzahlSchiffe++;
                return 1;
            }
            else
            {
                return 3;
            }
        }
        else
        {
            return 2;
        }

    }
    //verarbeitet den Schuss
    public int verarbeiteSchuss(int pX, int pY)
    {
        //Rückgabewert: 1=versnkt, 2= getroffen, 3= nicht getroffen

        schiffliste.toFirst();
        schussliste.append(pX+"|"+pY); //fügt den Schuss zur Schussliste hinzu
        while(schiffliste.hasAccess())
        {
            Schiff schiff = schiffliste.getContent();
            if(schiff.horizontal)
            {
                if(schiff.getX()>=pX && schiff.getX()-(schiff.getSchiffslaenge()-1)<=pX && pY == schiff.getY())
                {
                    schiff.treffer(pX, pY);
                    if(schiff.versenkt)
                    {
                        return 1;
                    }
                    return 2;
                }
            }
            else
            {
                if(schiff.getY()>=pY && schiff.getY()-(schiff.getSchiffslaenge()-1)<=pY && pX == schiff.getX())
                {
                    schiff.treffer(pX, pY);
                    if(schiff.versenkt)
                    {
                        return 1;
                    }
                    return 2;
                }
            }

        }
        return 3;
    }

}
