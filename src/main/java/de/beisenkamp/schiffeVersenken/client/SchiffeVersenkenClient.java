package de.beisenkamp.schiffeVersenken.client;

import nrw.abiturklassen.netz.Client;

public class SchiffeVersenkenClient extends Client {

    SchiffeVersenkenView view;

    public SchiffeVersenkenClient(String pServerIP, int pServerPort, SchiffeVersenkenView pView) {
        super(pServerIP, pServerPort);
        view = pView;
    }

    @Override
    public void processMessage(String pNachricht) {

    }

}