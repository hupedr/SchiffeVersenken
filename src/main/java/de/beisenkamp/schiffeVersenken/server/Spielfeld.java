package de.beisenkamp.schiffeVersenken.server;

public class Spielfeld
{
    int[][] feld;
    public Spielfeld()
    {
        feld = new int[9][9];
        for(int i=0;i<feld.length;i++)
        {
            for(int j=0;j<feld.length;j++)
            {
                feld[i][j]=0;
            }
        }
    }

    // KEINE SCHIFF = 0    SCHIFF = 1
    // (x/y)   <- Position

    //                             x-Koo ; SEITE BUCHSTABEN
    public int schiffPlatzieren(String Position1, String Position2)
    {
        //Koordinaten in Int umwandeln ü
        int pos1x = (int)Position1.charAt(1);
        int pos1y = (int)Position1.charAt(0)-64;
        int pos2x = (int)Position1.charAt(1);
        int pos2y = (int)Position1.charAt(0)-64;
    }
}
