package de.beisenkamp.schiffeVersenken.server;

public class Spieler
{
    int port;
    String ip;
    String name;
    Spielfeld spielfeld;
    public Spieler(String pIP, int pPort)
    {
        port = pPort;
        ip = pIP;
        spielfeld = new Spielfeld();
    }

    public void setName(String pName)
    {
        name = pName;
    }
}
