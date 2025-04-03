package de.beisenkamp.schiffeVersenken.server;

import sum.ereignis.EBAnwendung;
import sum.komponenten.Zeilenbereich;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SchiffeVersenkenServerView extends EBAnwendung {

    private SchiffeVersenkenServer server;
    private Zeilenbereich zeilenbereich;

    public static void main(String[] args) {
        new SchiffeVersenkenServerView(600,400);
    }

    public SchiffeVersenkenServerView(int pBreite, int pHoehe) {
        super(pBreite, pHoehe);
        zeilenbereich = new Zeilenbereich(0,0,600,400,"");
        this.server =  new SchiffeVersenkenServer(this, 9981);
        hatBildschirm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                zeilenbereich.haengeAn("Beende server!");
                System.out.println("Closing server");
                server.close();
            }
        });


    }

    public void melde(String pMessage) {
        zeilenbereich.haengeAn(pMessage);
    }

}
