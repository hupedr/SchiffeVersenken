package de.beisenkamp.schiffeVersenken.server;

import sum.ereignis.EBAnwendung;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SchiffeVersenkenServerView extends EBAnwendung {

    private SchiffeVersenkenServer server;

    public static void main(String[] args) {
        new SchiffeVersenkenServerView(200,200);
    }

    public SchiffeVersenkenServerView(int pBreite, int pHoehe) {
        super(pBreite, pHoehe);
        this.server = new SchiffeVersenkenServer(9981);
        hatBildschirm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Closing server");
                server.close();
            }
        });


    }

}
