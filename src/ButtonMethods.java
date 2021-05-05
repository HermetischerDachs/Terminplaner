import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ButtonMethods{
    private String filepath = "src/data.txt";

    public static void neuerEintrag(String datum, String eintrag){
        BufferedWriter bw = null;

        try {
            File file = new File("src/data.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(datum + ", " + eintrag + "\n");
            System.out.println("Eintrag gespeichert");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Problem: Die Parameter sind vom unveränderten Eintrag-Objekt. Beim Auslesen soll auf diese geprüft werden, und geschrieben werden sollen die
    //neuen Daten. Das irgendwie lösen. Idee: Überladene Methode für eine Änderung von jeweils Datum und Eintrag
    //Datum
    public static void eintragAendern(Entry entryOld, Entry entryNew){
        try (BufferedReader br = new BufferedReader(new FileReader("src/data.txt"))) {
            BufferedWriter bw = null;

            File file = new File("src/dataNEW.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);

            String line;
            //Erstelle dataNEW.txt
            while ((line = br.readLine()) != null) {
                //Ausgewählte Zeile ersetzen
                if(line.equals(entryOld.toString())) { //entryOld.toString()
                    //Schreibe die Parameter ein
                    bw.write(entryNew.toString() + "\n");      ////entryNew.toString()
                    continue;
                }
                //Schreibe die Zeile ein
                bw.write(line + "\n");
            }
            //Original löschen
            br.close();
            bw.close();
            fw.close();
            Files.delete(Paths.get("src/data.txt"));
            //Neu umbenennen
            Files.move(Paths.get("src/dataNEW.txt"), Paths.get("src/data.txt"));
            System.out.println("Eintrag überschrieben");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


    public static void eintragLoeschen(Entry toDelete){
        try (BufferedReader br = new BufferedReader(new FileReader("src/data.txt"))) {
            BufferedWriter bw = null;

            File file = new File("src/dataNEW.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);

            String line;
            //Erstelle dataNEW.txt
            while ((line = br.readLine()) != null) {
                //Ausgewählte Zeile ersetzen
                if(line.equals(toDelete.toString())) { //entryOld.toString()
                    //Überspringe diese Zeile
                    continue;
                }
                //Schreibe die Zeile ein
                bw.write(line + "\n");
            }
            //Original löschen
            br.close();
            bw.close();
            fw.close();
            Files.delete(Paths.get("src/data.txt"));
            //Neu umbenennen
            Files.move(Paths.get("src/dataNEW.txt"), Paths.get("src/data.txt"));
            System.out.println("Eintrag überschrieben");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}