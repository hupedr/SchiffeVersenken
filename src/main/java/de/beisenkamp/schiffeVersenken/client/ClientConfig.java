package de.beisenkamp.schiffeVersenken.client;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class ClientConfig {

    private static final String CONFIG_FILE = "config.txt";

    private String serverIp = "localhost";
    private int serverPort = 81;

    public ClientConfig() {
        File configFile = new File(CONFIG_FILE);

        // Prüfen, ob die Datei bereits existiert
        if (configFile.exists()) {
            readConfig();
        } else {
            // Datei aus dem JAR extrahieren
            try (InputStream inputStream = ClientConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
                if (inputStream == null) {
                    System.err.println("Fehler: Config-Datei ist nicht im JAR enthalten!");
                    return;
                }

                // Datei im aktuellen Verzeichnis speichern
                Files.copy(inputStream, Paths.get(CONFIG_FILE), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Standard Config-Datei wurde aus dem JAR extrahiert.");
                readConfig();
            } catch (IOException e) {
                System.err.println("Fehler beim Extrahieren der Config-Datei: " + e.getMessage());
            }
        }
    }

    public void readConfig() {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            System.err.println("Fehler: config.txt nicht gefunden!");
            return;
        }

        Map<String, String> configMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) { // Ignoriere leere Zeilen und Kommentare
                    continue;
                }

                String[] parts = line.split(":", 2); // Nur beim ersten ":" trennen
                if (parts.length == 2) {
                    configMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Config-Datei: " + e.getMessage());
        }

        // Werte aus der Map abrufen
        serverIp = configMap.getOrDefault("server-ip", "localhost");
        try {
            serverPort = Integer.parseInt(configMap.getOrDefault("server-port", "81"));
        } catch (NumberFormatException e) {
            System.err.println("Ungültige Port-Nummer! Standardwert (81) wird verwendet.");
            serverPort = 81;
        }
    }

    public String getServerIp() {
        return serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }
}
