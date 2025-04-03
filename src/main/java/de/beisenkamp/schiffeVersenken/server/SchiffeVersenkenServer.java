package de.beisenkamp.schiffeVersenken.server;

import de.beisenkamp.schiffeVersenken.Protocol;
import nrw.abiturklassen.netz.Server;


public class SchiffeVersenkenServer extends Server {

    Spieler spieler1;

    Spieler spieler2;

    Boolean Spieler1Zug = false;

    private final SchiffeVersenkenServerView view;

    public SchiffeVersenkenServer(SchiffeVersenkenServerView view, int pPort)
    {
        super(pPort);
        this.view = view;
        System.out.println("Starting server on "+pPort);
    }

    public void User(String name, String ip, int port)
    {
        if((spieler1 != null && name.equals(spieler1.name))
                || (spieler2 != null && name.equals(spieler2.name))) {
            sende(ip, port, Protocol.ANMELDUNG + Protocol.SEPARATOR + Protocol.ERROR + Protocol.SEPARATOR + "Name bereits vergeben <" + name + ">.");
        } else {
            Spieler dieserSpieler;
            if (spieler1 != null && spieler1.port == port && ip.equals(spieler1.ip))
            {
                dieserSpieler = spieler1;
            }
            else
            {
                dieserSpieler = spieler2;
            }
            dieserSpieler.setName(name);
            sende(dieserSpieler.ip, dieserSpieler.port, Protocol.ANMELDUNG+Protocol.SEPARATOR+Protocol.OK+Protocol.SEPARATOR+"Angemeldet mit Benutzername <"+name+">.");
            Spielstart();
        }
    }

    public void Spielstart()
    {
        if(spieler1 != null && spieler2 != null && spieler1.name != null && spieler2.name != null)
        {
            sende(spieler1.ip,spieler1.port,Protocol.START + Protocol.SEPARATOR + spieler2.name);
            sende(spieler2.ip,spieler2.port,Protocol.START + Protocol.SEPARATOR + spieler1.name);
        }
    }


    public void SchiffePlatzieren(String pos1, String pos2, String pIP, int pPort)
    {

        Spieler dieserSpieler;
        if(pIP.equals(spieler1.ip) && spieler1.port == pPort)
        {
            dieserSpieler = spieler1;
        }
        else
        {
            dieserSpieler = spieler2;
        }
        if(dieserSpieler.spielfeld.alleSchiffe() < 10) {
            int i = dieserSpieler.spielfeld.schiffPlatzieren(pos1, pos2);
            if (i == 1) {
                System.out.println("Sending to client: SHIFF and SPIELFELD " + pIP + " " + pPort);
                sende(pIP, pPort, Protocol.SCHIFF + Protocol.SEPARATOR + Protocol.OK + Protocol.SEPARATOR + "Noch " + (10 - dieserSpieler.spielfeld.alleSchiffe()) + " Schiffe setzen.");
                sende(pIP, pPort, Protocol.SPIELFELD + Protocol.SEPARATOR + dieserSpieler.spielfeld.kodiereSpielfeld(true));
                System.out.println("DONE");
            }

            if (i == 2) {
                sende(pIP, pPort, Protocol.SCHIFF + Protocol.SEPARATOR + Protocol.ERROR + Protocol.SEPARATOR + "Platz bereits belegt.");
            }

            if (i == 3) {
                sende(pIP, pPort, Protocol.SCHIFF + Protocol.SEPARATOR + Protocol.ERROR + Protocol.SEPARATOR + "Größe nicht erlaubt");
            }
        }



        //Alle schiffe gesetzt? Dann geht es los!

        if(spieler1.spielfeld.alleSchiffe() == 10 && spieler2.spielfeld.alleSchiffe() == 10)
        {
            Zug();
        }


    }

    public void Zug()
    {
        if(!Spieler1Zug)
        {
            Spieler1Zug = true;
            sende(spieler1.ip,spieler1.port,Protocol.ZUG);
        }
        else
        {
            Spieler1Zug = false;
            sende(spieler2.ip,spieler2.port,Protocol.ZUG);
        }
    }

    public void Schuss(String pos, String ip, int port)
    {
        Spieler dieserSpieler;
        Spieler andererSpieler;
        System.out.println(pos);
        if(spieler1.ip.equals(ip) && spieler1.port == port)
        {
            dieserSpieler = spieler1;
            andererSpieler = spieler2;
        }
        else
        {
           dieserSpieler = spieler2;
           andererSpieler = spieler1;
        }
        int i = andererSpieler.spielfeld.verarbeiteSchuss(Spielfeld.getPositionX(pos),Spielfeld.getPositionY(pos));
        System.out.println("Schuss Ergebnis: "+i);
        if(i == 1)
        {
            sende(ip,port,Protocol.TREFFER+Protocol.SEPARATOR+Protocol.VERSENKT+Protocol.SEPARATOR+"Noch "+(andererSpieler.spielfeld.uebrigeSchiffe())+" Schiffe übrig.");
            sende(andererSpieler.ip,andererSpieler.port,Protocol.SCHUSS+Protocol.SEPARATOR+"<"+pos+">");
        }
        else
        {
            if(i==2)
            {
                sende(ip,port,Protocol.TREFFER+Protocol.SEPARATOR+Protocol.JA+Protocol.SEPARATOR+"Noch "+(andererSpieler.spielfeld.uebrigeSchiffe())+" Schiffe übrig.");
                sende(andererSpieler.ip,andererSpieler.port,Protocol.SCHUSS+Protocol.SEPARATOR+"<"+pos+">");
            }
            else
            {
                if(i==3)
                {
                    sende(ip,port,Protocol.TREFFER+Protocol.SEPARATOR+Protocol.NEIN+Protocol.SEPARATOR+"Noch "+(andererSpieler.spielfeld.uebrigeSchiffe())+" Schiffe übrig.");
                    sende(andererSpieler.ip,andererSpieler.port,Protocol.SCHUSS+Protocol.SEPARATOR+""+pos+"");
                }
            }
        }
        sende(andererSpieler.ip,andererSpieler.port, Protocol.SPIELFELD + Protocol.SEPARATOR + andererSpieler.spielfeld.kodiereSpielfeld(true));
        sende(ip,port,Protocol.SPIELFELD_GEGNER + Protocol.SEPARATOR + andererSpieler.spielfeld.kodiereSpielfeld(false));
        //Zug beendet
        if(!endeTesten()) {
            Zug();
        }
    }

    public boolean endeTesten()
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
            ende(spieler2);
            return true;
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
            ende(spieler1);
            return true;
        }
        return false;
    }

    public void ende(Spieler pSpieler)
    {
        sendToAll(Protocol.ENDE+Protocol.SEPARATOR+" "+pSpieler.name + " hat gewonnen.");
    }


    public void processNewConnection(String pIP, int pPort)
    {
        System.out.println("New Connection: "+pIP+" : "+pPort);
        System.out.println("New Connection: "+spieler1+" : "+spieler2);
        if (spieler1 == null) {
            spieler1 = new Spieler(pIP, pPort);
        } else {
            if (spieler2 == null) {
                spieler2 = new Spieler(pIP, pPort);
            } else {
                //ERROR MESSAGE
                sende(pIP, pPort, Protocol.ANMELDUNG+Protocol.SEPARATOR+Protocol.ERROR+Protocol.SEPARATOR+"Server ist voll.");
                closeConnection(pIP, pPort);
            }
        }
    }


    public void processMessage(String pIP, int pPort, String message)
    {
        System.out.println("New Message: "+pIP+" : "+pPort+" - "+message);
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
        view.melde("Beende Client-Verbindung: "+pIP+" : "+pPort);
        System.out.println("Closing Connection: "+pIP+" : "+pPort);
        if (spieler1 != null && spieler1.ip.equals(pIP) && spieler1.port == pPort)
        {
            spieler1 = null;
        }
        if (spieler2 != null && spieler2.ip.equals(pIP) && spieler2.port == pPort)
        {
            spieler2 = null;
        }
    }

    private void sende(String pIP, int pPort, String pMessage) {
        send(pIP, pPort, pMessage);
        System.out.println("send ("+pIP+":"+pPort+") -> "+pMessage);
        view.melde("Sende ("+pIP+":"+pPort+") -> "+pMessage);
    }
}