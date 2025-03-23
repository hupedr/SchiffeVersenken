package de.beisenkamp.schiffeVersenken.server;

import de.beisenkamp.schiffeVersenken.Protocol;

public class SchiffeVersenkenServer {
    Spielfeld spielfeld;
    Protocol protocol;
    public static void main(String[] args)
    {


    }

    public SchiffeVersenkenServer()
    {
        protocol = new Protocol();
        spielfeld = new Spielfeld();
    }

    public void Zentrale(String message)
    {
        switch (message)
        {
            case "ajajaj":

                break;
        }

    }

    public void Anmeldung()
    {

    }

    public void Start()
    {

    }

    public void SchiffePlatzieren(String pos1, String pos2)
    {
        spielfeld.schiffPlatzieren(pos1,pos2);
    }

    public void Zug()
    {

    }

    public void ende()
    {

    }



}