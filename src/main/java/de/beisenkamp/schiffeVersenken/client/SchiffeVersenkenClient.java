package de.beisenkamp.schiffeVersenken.client;

import de.beisenkamp.schiffeVersenken.Protocol;
import nrw.abiturklassen.netz.Client;


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
                break;
            case Protocol.START:
                //todo
                break;
            //todo
        }
    }

}