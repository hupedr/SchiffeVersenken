package de.beisenkamp.schiffeVersenken.client;

import sum.ereignis.EBAnwendung;
import sum.komponenten.Knopf;

public class SchiffeVersenkenView extends EBAnwendung {

    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        SchiffeVersenkenView view = new SchiffeVersenkenView(800, 600, config);
    }

    private SchiffeVersenkenClient client;
    private final ClientConfig config;

    private final Knopf knopfVerbinden;
    private final Spielfeld spielfeld;

    public SchiffeVersenkenView(int pBreite, int pHoehe, ClientConfig pConfig) {
        super(pBreite, pHoehe);
        config = pConfig;

        knopfVerbinden = new Knopf(10, 10, 70, 20, "Verbinden");
        knopfVerbinden.setzeBearbeiterGeklickt("bearbeiteVerbindeMitServer");
        spielfeld = new Spielfeld(10, 40, 22, 12, 12, this);
        spielfeld.setzeBearbeiterMarkierungGeaendert("bearbeiteSpielfeldKlick");

    }
    /**********************
     * Ereignisbearbeiter *
     **********************/

    public void bearbeiteVerbindeMitServer() {
        SchiffeVersenkenClient client = new SchiffeVersenkenClient(config.getServerIp(), config.getServerPort(), this);
    }

    public void bearbeiteSpielfeldKlick() {
        // Ausgabe des angeklickten Feldes als Beispiel:
        System.out.println("Zeile1: "+spielfeld.getKlickZeile1()+" Spalte1: "+spielfeld.getKlickSpalte1());
        System.out.println("Zeile2: "+spielfeld.getKlickZeile2()+" Spalte2: "+spielfeld.getKlickSpalte2());
    }
}
