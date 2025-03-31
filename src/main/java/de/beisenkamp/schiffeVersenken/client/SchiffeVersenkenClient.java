package de.beisenkamp.schiffeVersenken.client;

import de.beisenkamp.schiffeVersenken.Protocol;
import nrw.abiturklassen.netz.Client;

import java.util.Arrays;


public class SchiffeVersenkenClient extends Client {

    private SchiffeVersenkenView view;

    private boolean debug = true;

    public SchiffeVersenkenClient(String pServerIP, int pServerPort, SchiffeVersenkenView pView) {
        super(pServerIP, pServerPort);
        view = pView;
    }

    @Override
    public void processMessage(String pNachricht) {
        if(debug) {
            view.zeigeMeldung(pNachricht);
        }
        String[] nachrichtTeile = pNachricht.split(":");
        switch (nachrichtTeile[0]) {
            case Protocol.ANMELDUNG:
                if(nachrichtTeile[1].equals(Protocol.OK)) {
                    view.zeigeMeldung(nachrichtTeile[2]);
                    view.anmeldungErfolgreich();
                }
                else if (nachrichtTeile[1].equals(Protocol.ERROR))
                {
                    view.zeigeMeldung(nachrichtTeile[2]);
                }
                break;
            case Protocol.START:
                view.startSchiffeSetzen();
                view.zeigeMeldung(nachrichtTeile[2]);
                break;
            case Protocol.SCHIFF:
                if (nachrichtTeile[1].equals(Protocol.OK))
                {
                    view.zeigeMeldung(nachrichtTeile[2]);
                }
                else if (nachrichtTeile[1].equals(Protocol.ERROR))
                {
                    view.zeigeMeldung(nachrichtTeile[2]);
                }
                break;
            case Protocol.ZUG:
                view.beginneZug();
                break;
            case Protocol.TREFFER:
                if(nachrichtTeile[1].equals(Protocol.JA))
                {
                    view.zeigeMeldung(nachrichtTeile[2]);
                }
                else if (nachrichtTeile[1].equals(Protocol.NEIN))
                {
                    view.zeigeMeldung(nachrichtTeile[2]);
                }
                else if (nachrichtTeile[1].equals(Protocol.VERSENKT))
                {
                    view.zeigeMeldung(nachrichtTeile[2]);
                }
                break;
            case Protocol.SCHUSS:
                view.zeigeMeldung(nachrichtTeile[1]);
                break;
            case Protocol.ENDE:
                view.ende();
                break;
            case Protocol.SPIELFELD:
                bearbeiteSendeSpielfeld(Arrays.copyOfRange(nachrichtTeile,1,nachrichtTeile.length));
                break;
        }
    }

    private void bearbeiteSendeSpielfeld(String [] pSpielfeld)
    {
        String [][] spielfeld = new String[pSpielfeld.length][pSpielfeld.length];
        for (int i = 0; i < pSpielfeld.length; i++)
        {
            String [] spielfeldZeile = pSpielfeld [i].split("|");
            for (int j = 0; j < spielfeldZeile.length; j++)
            {
                spielfeld [i][j] = spielfeldZeile[j];
            }

        }
        view.zeigeSpielfeld(spielfeld);
    }

    public void schießen(int pX, int pY)
    {
        this.send(Protocol.SCHUSS + Protocol.SEPARATOR + pX + "|" + pY);
    }

    public void schiffEinfuegen(int pXS, int pYS, int pXE, int pYE)
    {
        this.send(Protocol.SCHIFF + Protocol.SEPARATOR + pXS + "|" + pYS + Protocol.SEPARATOR + pXE + "|" + pYE);
    }

    public void meldeAn(String pName)
    {
        this.send(Protocol.USER + Protocol.SEPARATOR + pName);
    }

}