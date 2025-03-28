package de.beisenkamp.schiffeVersenken.server;
import de.beisenkamp.schiffeVersenken.Protocol;
import nrw.abiturklassen.netz.Server;
public class SchiffeVersenkenServer extends Server {

    Spieler spieler1;

    Spieler spieler2;
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
                send(ip, port, "ANMELDUNG:OK:Angemeldet mit Benutzername <"+name+">.");
                Start();
            }
            else
            {
                    spieler2.setName(name);
                    send(ip, port, "ANMELDUNG:OK:Angemeldet mit Benutzername <"+name+">.");
                    Start();
            }
        }
        else
        {
            send(ip,port,"ANMELDUNG:ERR:Name <"+name+"> ist bereits vergeben.");
        }
    }

    public void Start()
    {
        if(spieler1.name != null && spieler2.name != null)
        {
            send(spieler1.ip,spieler1.port,Protocol.START + Protocol.SEPERATOR + spieler1.name);
        }
    }

    public void SchiffePlatzieren(String pos1, String pos2)
    {
        if(spieler1.ip == ip && spieler1.port == port)
        {
            int i = spieler1.spielfeld.schiffPlatzieren(pos1,pos2);
            if(i== 1)
            {
                send(ip,port,Protocol.SCHIFF+Protocol.SEPERATOR+Protocol.OK+Protocol.SEPERATOR+"Noch <"+ (10-spieler1.spielfeld.anzahlSchiffe)+"> Schiffe setzen.");
            }

            if (i == 2)
            {
                send(ip, port, Protocol.SCHIFF + Protocol.SEPERATOR + Protocol.ERROR + Protocol.SEPERATOR + "Platz bereits belegt.");
            }

            if(i==3)
            {

            }
        }
        else
        {
            spieler2.spielfeld.schiffPlatzieren(pos1,pos2);
        }
    }

    public void Zug()
    {

    }

    public void ende()
    {

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
                send(pIP, pPort, "ANMELDUNG:ERR:Server ist voll.");

            }
        }
    }


    public void processMessage(String pIP, int pPort, String message)
    {
        String[] nachricht = message.split(Protocol.SEPERATOR);
        switch (nachricht[0]) {
            case Protocol.USER:
                User(nachricht[1],pIP,pPort);
                break;
            case Protocol.SCHIFF:
                Schiff(nachricht[1],nachricht[2],pIP,pPort);
                break;
            case Protocol.SCHUSS:
                Schuss(nachricht[1],pIP,pPort);
                break;
        }
    }

    public void processClosingConnection(String s, int i) {

    }
}