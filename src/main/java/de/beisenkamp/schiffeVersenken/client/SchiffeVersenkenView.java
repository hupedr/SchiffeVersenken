package de.beisenkamp.schiffeVersenken.client;

import sum.ereignis.EBAnwendung;
import sum.komponenten.Etikett;
import sum.komponenten.Knopf;
import sum.komponenten.Textfeld;
import sum.komponenten.Zeilenbereich;

public class SchiffeVersenkenView extends EBAnwendung {

    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        SchiffeVersenkenView view = new SchiffeVersenkenView(800, 600, config);
    }

    private SchiffeVersenkenClient client;
    private final ClientConfig config;

    private final Knopf knopfVerbinden;
    private final Spielfeld spielfeld;
    private final Zeilenbereich zeilenbereichMeldung;
    private final Etikett etikettBenutzername;
    private final Textfeld textfeldBenutzername;
    private final Knopf knopfAnmeldung;

    private boolean platziereSchiffe;

    public SchiffeVersenkenView(int pBreite, int pHoehe, ClientConfig pConfig) {
        super(pBreite, pHoehe);
        config = pConfig;

        knopfVerbinden = new Knopf(10, 10, 120, 30, "Verbinden");
        knopfVerbinden.setzeBearbeiterGeklickt("bearbeiteVerbindeMitServer");

        knopfAnmeldung = new Knopf(140, 10, 120,30, "Anmeldung");
        knopfAnmeldung.setzeBearbeiterGeklickt("bearbeiteAnmeldung");
        knopfAnmeldung.deaktiviere();
        etikettBenutzername = new Etikett(270, 10, 80, 30, "Benutzername:");
        textfeldBenutzername = new Textfeld(360,10,150, 30, "");

        zeilenbereichMeldung = new Zeilenbereich(10, 50, 300, 100, "");
        zeilenbereichMeldung.deaktiviere();

        spielfeld = new Spielfeld(320, 50, 22, 12, 12, this);
        spielfeld.setzeBearbeiterMarkierungGeaendert("bearbeiteSpielfeldKlick");
        //spielfeld.deaktiviere();

    }
    /**********************
     * Ereignisbearbeiter *
     **********************/

    public void bearbeiteVerbindeMitServer() {
        if(client != null && client.isConnected()) {
            client.close();
        }
        SchiffeVersenkenClient client = new SchiffeVersenkenClient(config.getServerIp(), config.getServerPort(), this);
        if(client.isConnected()) {
            knopfVerbinden.deaktiviere();
            knopfAnmeldung.aktiviere();
        }
    }

    public void bearbeiteSpielfeldKlick() {
        // Ausgabe des angeklickten Feldes als Beispiel:
        System.out.println("Zeile1: "+spielfeld.getKlickZeile1()+" Spalte1: "+spielfeld.getKlickSpalte1());
        System.out.println("Zeile2: "+spielfeld.getKlickZeile2()+" Spalte2: "+spielfeld.getKlickSpalte2());
        if(platziereSchiffe)
        {
            if(spielfeld.getKlickSpalte1() = spielfeld.getKlickSpalte2())
            {
                
            }
        }

        if()
    }

    /************
     * Methoden *
     ************/

    public void zeigeMeldung(String pMeldung) {
        zeilenbereichMeldung.haengeAn(pMeldung);
    }

    public void anmeldungErfolgreich() {
        //todo
    }

    public void startSchiffeSetzen()
    {
        platziereSchiffe = true;
        zeigeMeldung("Bitte platzieren Sie Ihre Schiffe.");
        spielfeld.aktiviere();
    }


    public void beginneZug()
    {

    }

    public void ende()
    {
        zeigeMeldung("Ihr  Zug ist zu Ende.");
        spielfeld.deaktiviere();
    }

    public void zeigeSpielfeld(String [][] pSpielfeld)
    {
        for(int i=0; i < spielfeld.hoehe(); i++)
        {
            for(int j=0; j < spielfeld.breite(); j++)
            {
                spielfeld.setzeInhaltAn( pSpielfeld[i][j], i, j);
            }
        }
    }
}
