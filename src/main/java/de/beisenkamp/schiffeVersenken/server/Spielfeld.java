package de.beisenkamp.schiffeVersenken.server;
import de.beisenkamp.schiffeVersenken.Protocol;
import nrw.abiturklassen.datenstruktur.linear.List;
public class Spielfeld
{
    public static final int groesse = 12;

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
        int pos1x = getPositionX(Position1);
        int pos1y = getPositionY(Position1);
        int pos2x = getPositionX(Position2);
        int pos2y = getPositionY(Position2);
        if(pos1x>pos2x)
        //1.Koordinate = kleinste
        {
            pos1x = getPositionX(Position2);
            pos2x = getPositionX(Position1);
        }
        if(pos1y>pos2y)
        {
             pos1y = getPositionY(Position2);
             pos2y = getPositionY(Position1);
        }
        if(pos1y==pos2y)
        {
            int pLaenge = pos2x-pos1x+1;
            //Länge muss <= 5 sein
            if(pLaenge <= 5)
            {
                schiffliste.toFirst();
                while(schiffliste.hasAccess())
                {
                    Schiff schiff = schiffliste.getContent();
                    if(!schiff.horizontal)
                    {
                        for(int i=pos1x;i<pos2x+1;i++) // i ist das Segment des neuen Schiffes, das überprüft wird
                        {
                            for(int j=schiff.getY(); j<schiff.getY()+schiff.getSchiffslaenge(); j++) //j ist die Y Koordinate des Schiffes, mit dem es verglichen werden soll
                            {
                                if (pos1y==j && i==schiff.getX())
                                {
                                    return 2; //Schiffe kreuzen sich
                                }

                            }
                        }
                    }
                    else
                    {           //Schiffe liegen übereienander                                          gleiche Höhe
                        if(!(schiff.getX()>pos2x||schiff.getX()+schiff.getSchiffslaenge()-1<pos1x) && schiff.getY()==pos1y)
                        {
                            return 2; //Schiffe liegen übereinander
                        }
                    }
                    schiffliste.next();
                }
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
            int pLaenge = pos2y - pos1y + 1;
            //Länge muss <= 5 sein
            if(pLaenge <= 5) {
                schiffliste.toFirst();
                while(schiffliste.hasAccess())
                {
                    Schiff schiff = schiffliste.getContent();
                    if(schiff.horizontal)
                    {
                        for(int i=pos1y;i<pos2y+1;i++) // i ist das Segment des neuen Schiffes, das überprüft wird
                        {
                            for(int j=schiff.getX(); j<schiff.getX()+schiff.getSchiffslaenge(); j++) //j ist die x Koordinate des Schiffes, mit dem es verglichen werden soll
                            {
                                if (pos1x==j && i==schiff.getY())
                                {
                                    return 2; //Schiffe kreuzen sich
                                }

                            }
                        }
                    }
                    else
                    {
                        if(!(schiff.getY()>pos2y||schiff.getY()+schiff.getSchiffslaenge()-1<pos1y) && schiff.getX()==pos1x)
                        {
                            return 2; //Schiffe liegen übereinander
                        }
                    }
                    schiffliste.next();
                }
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
            return 3;
        }

    }
    //verarbeitet den Schuss
    public int verarbeiteSchuss(int pX, int pY)
    {
        //Rückgabewert: 1=versnkt, 2= getroffen, 3= nicht getroffen
        System.out.println("Schuss bei"+pX+"//"+pY);
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
            schiffliste.next();

        }
        return 3;
    }

    public String kodiereSpielfeld(boolean pAllesSichtbar) {
        char[][] feld = new char[groesse][groesse];

        for(int i = 0; i < groesse; i++) {
            for(int j = 0; j < groesse; j++) {
                feld[i][j] = ' ';
            }
        }
        schussliste.toFirst();
        while(schussliste.hasAccess()){
            int x = getPositionX(schussliste.getContent());
            int y = getPositionX(schussliste.getContent());
            feld[x][y] = 'x';
            schussliste.next();
        }

        schiffliste.toFirst();
        while(schiffliste.hasAccess()) {
            Schiff schiff = schiffliste.getContent();
            int startX = schiff.getX();
            int startY = schiff.getY();
            System.out.println("x: "+startX+" y: "+startY+" Länge: "+schiff.getSchiffslaenge()+" Horizontal: "+schiff.horizontal);
            if(schiff.horizontal) {
                for(int i = 0; i < schiff.getSchiffslaenge(); i++) {
                    if(schiff.versenkt) {
                        feld[startY][startX+i] = 'X';
                    } else if(schiff.getroffen[i]) {
                        feld[startY][startX+i] = 216;
                    } else if(pAllesSichtbar){
                        feld[startY][startX+i] = 'O';
                    }
                }
            } else {
                for(int i = 0; i < schiff.getSchiffslaenge(); i++) {
                    if(schiff.versenkt) {
                        feld[startY+i][startX] = 'X';
                    } else if(schiff.getroffen[i]) {
                        feld[startY+i][startX] = 216;
                    } else if(pAllesSichtbar){
                        feld[startY+i][startX] = 'O';
                    }
                }
            }
            schiffliste.next();
        }
        String code = "";
        for(int i = 0; i < groesse; i++) {
            code = code + feld[i][0];
            for(int j = 1; j < groesse; j++) {
                code = code  + "|" + feld[i][j];
            }
            if(i < groesse - 1) {
                code = code + Protocol.SEPARATOR;
            }
        }
        System.out.println(code);
        return code;
    }

    public static int getPositionX(String pPosition) {
        String[] pos = pPosition.split("\\|");
        return Integer.parseInt(pos[0]);
    }

    public static int getPositionY(String pPosition) {
        String[] pos = pPosition.split("\\|");
        return Integer.parseInt(pos[1]);
    }

}
