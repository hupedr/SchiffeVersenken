package de.beisenkamp.schiffeVersenken.server;
import de.beisenkamp.schiffeVersenken.Protocol;
import nrw.abiturklassen.netz.Server;

import javax.swing.*;

public class SchiffeVersenkenServer extends Server {

    Spieler spieler1;

    Spieler spieler2;

    Boolean Spieler1Zug = false;

    public SchiffeVersenkenServer(int pPort)
    {
        super(pPort);
    }

    public static void main(String[] args)
    {

    }


    public void User(String name, String ip, int port)
    {
        if(spieler1.name != name && spieler2.name != name)
        {
            if (spieler1.port == port && spieler1.ip == ip)
            {
                spieler1.setName(name);
                send(ip, port, Protocol.ANMELDUNG+Protocol.SEPARATOR+Protocol.OK+Protocol.SEPARATOR+"Angemeldet mit Benutzername <"+name+">.");
                Spielstart();
            }
            else
            {
                spieler2.setName(name);
                send(ip, port, Protocol.ANMELDUNG + Protocol.SEPARATOR + Protocol.OK + Protocol.SEPARATOR + "Angemeldet mit Benutzername <"+name+">.");
                Spielstart();
            }
        }
        else
        {
            send(ip,port,Protocol.ANMELDUNG+Protocol.SEPARATOR+Protocol.ERROR+Protocol.SEPARATOR+"Name <"+name+"> ist bereits vergeben.");
        }
    }

    public void Spielstart()
    {
        if(spieler1.name != null && spieler2.name != null)
        {
            send(spieler1.ip,spieler1.port,Protocol.START + Protocol.SEPARATOR + spieler1.name);
        }
    }







    //TODO: - Feld weiter schicken???
    //      - Fehler suchen :(











    public void SchiffePlatzieren(String pos1, String pos2, String ip, int port)
    {
        if(spieler1.ip == ip && spieler1.port == port)
        {
            int i = spieler1.spielfeld.schiffPlatzieren(pos1,pos2);
            if(i == 1)
            {
                send(ip,port,Protocol.SCHIFF+Protocol.SEPARATOR+Protocol.OK+Protocol.SEPARATOR+"Noch <"+ (10-spieler1.spielfeld.anzahlSchiffe)+"> Schiffe setzen.");
            }

            if (i == 2)
            {
                send(ip, port, Protocol.SCHIFF + Protocol.SEPARATOR + Protocol.ERROR + Protocol.SEPARATOR + "Platz bereits belegt.");
            }

            if(i==3)
            {
                send(ip,port,Protocol.SCHIFF+Protocol.SEPARATOR+Protocol.ERROR+Protocol.SEPARATOR+"Größe nicht erlaubt");
            }
        }
        else
        {
            int i = spieler2.spielfeld.schiffPlatzieren(pos1,pos2);
            if(i == 1)
            {
                send(ip,port,Protocol.SCHIFF+Protocol.SEPARATOR+Protocol.OK+Protocol.SEPARATOR+"Noch <"+ (10-spieler2.spielfeld.anzahlSchiffe)+"> Schiffe setzen.");
            }

            if (i == 2)
            {
                send(ip, port, Protocol.SCHIFF + Protocol.SEPARATOR + Protocol.ERROR + Protocol.SEPARATOR + "Platz bereits belegt.");
            }

            if(i==3)
            {
                send(ip,port,Protocol.SCHIFF+Protocol.SEPARATOR+Protocol.ERROR+Protocol.SEPARATOR+"Größe nicht erlaubt");
            }
        }
        //Alle schiffe gesetzt? Dann geht es los!

        if(spieler1.spielfeld.anzahlSchiffe == 10 && spieler2.spielfeld.anzahlSchiffe == 10)
        {
            Zug();
        }


    }

    public void Zug()
    {
        if(!Spieler1Zug)
        {
            Spieler1Zug = true;
            send(spieler1.ip,spieler1.port,Protocol.ZUG);
        }
        else
        {
            Spieler1Zug = false;
            send(spieler2.ip,spieler2.port,Protocol.ZUG);
        }
    }

    public void Schuss(String pos, String ip, int port)
    {
        if(spieler1.ip == ip && spieler1.port == port)
        {
            int i = spieler1.spielfeld.verarbeiteSchuss(pos.charAt(0),pos.charAt(2));
            if(i == 1)
            {
                send(ip,port,Protocol.TREFFER+Protocol.SEPARATOR+Protocol.VERSENKT+Protocol.SEPARATOR+"Noch <"+(10-spieler1.spielfeld.anzahlSchiffe)+"> Schiffe übrig.");
                send(spieler2.ip,spieler2.port,Protocol.SCHUSS+Protocol.SEPARATOR+"<"+pos+">");
                endeTesten();
            }
            else
            {
                if(i==2)
                {
                    send(ip,port,Protocol.TREFFER+Protocol.SEPARATOR+Protocol.JA+Protocol.SEPARATOR+"Noch <"+(10-spieler1.spielfeld.anzahlSchiffe)+"> Schiffe übrig.");
                    send(spieler2.ip,spieler2.port,Protocol.SCHUSS+Protocol.SEPARATOR+"<"+pos+">");
                }
                else
                {
                    if(i==3)
                    {
                        send(ip,port,Protocol.TREFFER+Protocol.SEPARATOR+Protocol.NEIN+Protocol.SEPARATOR+"Noch <"+(10-spieler1.spielfeld.anzahlSchiffe)+"> Schiffe übrig.");
                        send(spieler2.ip,spieler2.port,Protocol.SCHUSS+Protocol.SEPARATOR+"<"+pos+">");
                    }
                }
            }

        }
        else
        {
            int i = spieler2.spielfeld.verarbeiteSchuss(pos.charAt(0),pos.charAt(2));
            if(i == 1)
            {
                send(ip,port,Protocol.TREFFER+Protocol.SEPARATOR+Protocol.VERSENKT+Protocol.SEPARATOR+"Noch <"+(10-spieler2.spielfeld.anzahlSchiffe)+"> Schiffe übrig.");
                send(spieler1.ip,spieler1.port,Protocol.SCHUSS+Protocol.SEPARATOR+"<"+pos+">");
                endeTesten();
            }
            else
            {
                if(i==2)
                {
                    send(ip,port,Protocol.TREFFER+Protocol.SEPARATOR+Protocol.JA+Protocol.SEPARATOR+"Noch <"+(10-spieler2.spielfeld.anzahlSchiffe)+"> Schiffe übrig.");
                    send(spieler1.ip,spieler1.port,Protocol.SCHUSS+Protocol.SEPARATOR+"<"+pos+">");
                }
                else
                {
                    if(i==3)
                    {
                        send(ip,port,Protocol.TREFFER+Protocol.SEPARATOR+Protocol.NEIN+Protocol.SEPARATOR+"Noch <"+(10-spieler2.spielfeld.anzahlSchiffe)+"> Schiffe übrig.");
                        send(spieler1.ip,spieler1.port,Protocol.SCHUSS+Protocol.SEPARATOR+"<"+pos+">");
                    }
                }
            }
        }
        //Zug beendet
        Zug();

    }

    public void endeTesten()
    {
        int versenkteSchiffe = 0;
        spieler1.spielfeld.schiffliste.toFirst();
        spieler2.spielfeld.schiffliste.toFirst();
        //Spieler1
        while(spieler1.spielfeld.schiffliste.hasAccess())
        {
            if (spieler1.spielfeld.schiffliste.getContent().versenkt)
            {
                versenkteSchiffe++;
            }
            spieler1.spielfeld.schiffliste.next();
        }
        if(versenkteSchiffe == 10)
        {
            ende(spieler1);
        }
        //Spieler 2
        versenkteSchiffe = 0;
        while(spieler2.spielfeld.schiffliste.hasAccess())
        {
            if (spieler2.spielfeld.schiffliste.getContent().versenkt)
            {
                versenkteSchiffe++;
            }
            spieler2.spielfeld.schiffliste.next();
        }
        if(versenkteSchiffe == 10)
        {
            ende(spieler2);
        }
    }

    public void ende(Spieler pSpieler)
    {
        sendToAll(Protocol.ENDE+Protocol.SEPARATOR+" "+pSpieler.name + " hat gewonnen.");
    }


    public void processNewConnection(String pIP, int pPort)
    {
        if (spieler1 == null) {
            spieler1 = new Spieler(pIP, pPort);
        } else {
            if (spieler2 == null) {
                spieler2 = new Spieler(pIP, pPort);
            } else {
                //ERROR MESSAGE
                send(pIP, pPort, Protocol.ANMELDUNG+Protocol.SEPARATOR+Protocol.ERROR+Protocol.SEPARATOR+"Server ist voll.");
            }
        }
    }


    public void processMessage(String pIP, int pPort, String message)
    {
        String[] nachricht = message.split(Protocol.SEPARATOR);
        switch (nachricht[0]) {
            case Protocol.USER:
                User(nachricht[1],pIP,pPort);
                break;
            case Protocol.SCHIFF:
                SchiffePlatzieren(nachricht[1],nachricht[2],pIP,pPort);
                break;
            case Protocol.SCHUSS:
                Schuss(nachricht[1],pIP,pPort);
                break;
        }
    }

    public void processClosingConnection(String pIP, int pPort)
    {
        if (spieler1.ip==pIP && spieler1.port == pPort)
        {
            spieler1 = null;
        }
        if (spieler2.ip==pIP && spieler2.port == pPort)
        {
            spieler2 = null;
        }
    }
}