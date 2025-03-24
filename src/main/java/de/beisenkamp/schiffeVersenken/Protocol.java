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
 */

public class Protocol
{
    public static final String OK = "OK";
    public static final String ERROR = "ERR";
    /**
     * Servernachrichten
     */
    public static final String ANMELDUNG = "ANMELDUNG";

    public static final String START = "START";

    /**
     * Clientnachrichten
     */
    public static final String USER = "USER";
}
