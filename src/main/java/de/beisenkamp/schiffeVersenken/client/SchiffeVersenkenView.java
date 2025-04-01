package de.beisenkamp.schiffeVersenken.client;

import sum.ereignis.EBAnwendung;
import sum.komponenten.Etikett;
import sum.komponenten.Knopf;
import sum.komponenten.Textfeld;
import sum.komponenten.Zeilenbereich;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SchiffeVersenkenView extends EBAnwendung {

    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        SchiffeVersenkenView view = new SchiffeVersenkenView(1200, 600, config);
    }

    private SchiffeVersenkenClient client;
    private final ClientConfig config;
    private final Knopf knopfVerbinden;
    private final Spielfeld spielfeld, spielfeldGegner;
    private final Zeilenbereich zeilenbereichMeldung;
    private final Etikett etikettBenutzername;
    private final Textfeld textfeldBenutzername;
    private final Knopf knopfAnmeldung;
    private final Etikett etikettÜberschrift1;
    private final Etikett etikettÜberschrift2;
    private boolean platziereSchiffe;

    public SchiffeVersenkenView(int pBreite, int pHoehe, ClientConfig pConfig) {
        super(pBreite, pHoehe);
        config = pConfig;
        hatBildschirm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Closing client");
                if(client != null && client.isConnected()) {
                    client.close();
                }
            }
        });


        knopfVerbinden = new Knopf(10, 10, 120, 30, "Verbinden");
        knopfVerbinden.setzeBearbeiterGeklickt("bearbeiteVerbindeMitServer");

        knopfAnmeldung = new Knopf(140, 10, 120,30, "Anmeldung");
        knopfAnmeldung.setzeBearbeiterGeklickt("bearbeiteAnmeldung");
        knopfAnmeldung.deaktiviere();
        etikettBenutzername = new Etikett(270, 10, 80, 30, "Benutzername:");
        textfeldBenutzername = new Textfeld(360,10,150, 30, "");

        zeilenbereichMeldung = new Zeilenbereich(10, 50, 300, 100, "");
        zeilenbereichMeldung.deaktiviere();

        etikettÜberschrift1 = new Etikett(235,55,200,50,"Eigenes Spielfeld");
        etikettÜberschrift2 = new Etikett(770,55,200,50,"Gegnerisches Spielfeld");


        spielfeld = new Spielfeld(20, 120, 40, 12, 12, this);
        spielfeld.setzeBearbeiterMarkierungGeaendert("bearbeiteSpielfeldKlick");
        spielfeld.deaktiviere();
        spielfeldGegner = new Spielfeld(605, 120, 40, 12, 12, this);
        spielfeldGegner.setzeBearbeiterMarkierungGeaendert("bearbeiteSpielfeldGegnerKlick");
        spielfeldGegner.deaktiviere();

    }
    /**********************
     * Ereignisbearbeiter *
     **********************/

    public void bearbeiteVerbindeMitServer() {
        if(client != null && client.isConnected()) {
            client.close();
        }
        client = new SchiffeVersenkenClient(config.getServerIp(), config.getServerPort(), this);
        if(client.isConnected())
        {
            knopfVerbinden.deaktiviere();
            knopfAnmeldung.aktiviere();
        }
    }

    public void bearbeiteAnmeldung()
    {
        client.meldeAn(textfeldBenutzername.inhaltAlsText());
    }

    public void bearbeiteSpielfeldKlick() {
        // Ausgabe des angeklickten Feldes als Beispiel:
        System.out.println("Zeile1: "+(spielfeld.getKlickZeile1()-1)+" Spalte1: "+(spielfeld.getKlickSpalte1()-2));
        System.out.println("Zeile2: "+(spielfeld.getKlickZeile2()-1)+" Spalte2: "+(spielfeld.getKlickSpalte2()-2));
        if(spielfeld.getKlickSpalte1() == spielfeld.getKlickSpalte2() || spielfeld.getKlickZeile1() == spielfeld.getKlickZeile2())
        {
            client.schiffEinfuegen(spielfeld.getKlickSpalte1()-2, spielfeld.getKlickZeile1()-1,
                    spielfeld.getKlickSpalte2()-2, spielfeld.getKlickZeile2()-1);
        }
        spielfeld.markiereNichts();
    }

    public void bearbeiteSpielfeldGegnerKlick()
    {
        if(spielfeldGegner.getKlickZeile1() == spielfeldGegner.getKlickZeile2() && spielfeldGegner.getKlickSpalte1() == spielfeldGegner.getKlickSpalte2())
        {
            client.schießen(spielfeldGegner.getKlickSpalte1()-2,spielfeldGegner.getKlickZeile1()-1);
        }
        else
        {
            spielfeldGegner.markiereNichts();
        }
        spielfeldGegner.markiereNichts();
    }

        /************
         * Methoden *
         ************/

    public void zeigeMeldung(String pMeldung)
    {
        zeilenbereichMeldung.haengeAn(pMeldung);
    }

    public void anmeldungErfolgreich()
    {
        knopfAnmeldung.deaktiviere();
    }

    public void startSchiffeSetzen()
    {
        platziereSchiffe = true;
        zeigeMeldung("Bitte platzieren Sie Ihre Schiffe.");
        spielfeld.aktiviere();
    }


    public void beginneZug()
    {
        platziereSchiffe = false;
        zeigeMeldung("Ihr Zug beginnt.");
        spielfeld.aktiviere();
    }

    public void ende()
    {
        zeigeMeldung("Das Spiel ist hiermit offiziell beendet.");
        spielfeld.deaktiviere();
    }

    public void zeigeSpielfeld(String [][] pSpielfeld)
    {
        for(int i=0; i < pSpielfeld.length; i++)
        {
            for(int j=0; j < pSpielfeld.length; j++)
            {
                spielfeld.setzeInhaltAn( pSpielfeld[i][j], i+1, j+2);
            }
        }
    }

    public void zeigeSpielfeldGegner(String [][] pSpielfeld)
    {
        for(int i=0; i < pSpielfeld.length; i++)
        {
            for(int j=0; j < pSpielfeld.length; j++)
            {
                spielfeldGegner.setzeInhaltAn( pSpielfeld[i][j], i+1, j+2);
            }
        }
    }

}
