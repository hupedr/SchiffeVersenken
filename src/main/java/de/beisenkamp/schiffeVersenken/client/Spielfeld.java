package de.beisenkamp.schiffeVersenken.client;

import sum.ereignis.Ereignisanwendung;
import sum.komponenten.Tabelle;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Spielfeld extends Tabelle {

    private int klickZeile1, klickSpalte1, klickZeile2, klickSpalte2;
    private SchiffeVersenkenView view;

    public Spielfeld(double pLinks, double pOben, int pZellGroesse, int pZeilen, int pSpalten, SchiffeVersenkenView view) {
        super(pLinks, pOben, pZellGroesse*(pSpalten+2), pZellGroesse*(pZeilen+2), pZeilen, pSpalten+1);
        this.view = view;
        for(int i = 1; i <= spaltenanzahl(); i++) {
            setzeSpaltenbreite(pZellGroesse);
            if(i == 1) {
                setzeSpaltentitelAn("",i);
            } else {
                setzeSpaltentitelAn(" "+((char)(63+i)),i);
            }
        }
        for(int i = 1; i <= zeilenanzahl(); i++) {
            setzeZeilenhoehe(pZellGroesse);
            setzeInhaltAn((i<10?"  ":" ")+i,i,1);
        }
    }

    @Override
    protected void markierungGeaendert() {
        System.out.println("Markierung Geändert");
        klickZeile1 = 0;
        klickSpalte1 = 0;
        klickZeile2 = 0;
        klickSpalte2 = 0;
        for(int i = 1; i <= zeilenanzahl(); i++) {
            if(istZelleMarkiert(i,1)) {
                markiereNichts();
                return;
            }
        }
        for (int i = 1; i <= zeilenanzahl(); i++) {
            for (int j = 2; j <= spaltenanzahl(); j++) {
                if (istZelleMarkiert(i, j)) {
                    klickZeile2 = i;
                    klickSpalte2 = j;
                    if (klickSpalte1 == 0 && klickZeile1 == 0) {
                        klickZeile1 = i;
                        klickSpalte1 = j;
                    }
                }
            }
        }
    }

    public int getKlickZeile1() {
        return klickZeile1;
    }

    public int getKlickSpalte1() {
        return klickSpalte1;
    }

    public int getKlickZeile2() {
        return klickZeile2;
    }

    public int getKlickSpalte2() {
        return klickSpalte2;
    }

    @Override
    public void setzeBearbeiterMarkierungGeaendert(String pBearbeiter) {
        System.out.println("Setzte Bearbeiter: "+pBearbeiter);
        hatComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if(istAktiv() && klickZeile1>=1 && klickZeile2>=1 && klickSpalte1>=2 && klickSpalte2>=2) {
                    try {
                        Class sumEreignis = view.getClass();
                        Method methode = sumEreignis.getMethod(pBearbeiter, (Class[]) null);
                        methode.invoke(view, (Object[]) null);
                    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
}
