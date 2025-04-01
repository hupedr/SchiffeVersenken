package de.beisenkamp.schiffeVersenken;

/**    Client                       |     Server
 * ------------------------------------------------------------------------------------------------------
 * USER:<name>                      | ANMELDUNG:OK:Angemeldet mit Benutzername <name>.
 *                                  | ANMELDUNG:ERR:Name <name> ist bereits vergeben.
 *                                  | ANMELDUNG:ERR:Server ist voll.
 * ------------------------------------------------------------------------------------------------------
 *                                  | START:<anderer Spielername>
 * ------------------------------------------------------------------------------------------------------
 * SCHIFF:<Position1>:<Position2>   | SCHIFF:OK:Noch <anzahl> Schiffe setzen.
 *                                  | SCHIFF:ERR:Platz bereits belegt.
 *                                  | SCHIFF:ERR:Größe nicht erlaubt
 * (Eine Position besteht aus zwei Zahlen, getrennt mit einem senkrechten Strich, z.B. "2|5")
 * ------------------------------------------------------------------------------------------------------
 *                                  | ZUG
 * ------------------------------------------------------------------------------------------------------
 * SCHUSS:<Position>                | (Antwort) TREFFER:{JA|NEIN|VERSENKT}:Noch <anzahl> Schiffe übrig.
 *                                  | (Anderer Spieler) SCHUSS: <Position>
 * ------------------------------------------------------------------------------------------------------
 *                                  | ENDE: <name> hat gewonnen.
 * ------------------------------------------------------------------------------------------------------
 *                                  | SPIELFELD:_|...|_:_|...|_:_|...|_:...:_|...|_:
 *                                  | SPIELFELD_GEGNER:_|...|_:_|...|_:_|...|_:...:_|...|_:
 */

public class Protocol
{
    public static final String OK = "OK";
    public static final String ERROR = "ERR";
    /**
     * Servernachrichten
     */
    public static final String ANMELDUNG = "ANMELDUNG";

    public static final String SEPARATOR = ":";

    public static final String START = "START";

    public static final String SCHIFF = "SCHIFF";

    public static final String ZUG = "ZUG";

    public static final String TREFFER = "TREFFER";

    public static final String JA = "JA";

    public static final String NEIN = "NEIN";

    public static final String VERSENKT = "VERSENKT";

    public static final String SCHUSS = "SCHUSS";

    public static final String ENDE = "ENDE";

    public static final String SPIELFELD = "SPIELFELD";

    public static final String SPIELFELD_GEGNER = "SPIELFELD_GEGNER";

    /**
     * Clientnachrichten
     */
    public static final String USER = "USER";

}
