import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Wiederholung {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filepath = "src/data.txt";

        System.out.println("Terminplaner");
        while (true) {
            System.out.println(
                    "[1] Neuer Eintrag \n" +
                    "[2] Eintrag bearbeiten \n" +
                    "[3] Eintrag löschen \n" +
                    "[4] Inhalt ausgeben \n" +
                    "[5] Beenden");
            int option = scanner.nextInt();
            switch (option) {               //Über den Switch die in Methoden ausgelagerten Funktionalitäten erreichen
                case 1:
                    neuerEintrag(filepath);
                    break;
                case 2:
                    eintragBearbeiten(filepath);
                    break;
                case 3:
                    eintragLoeschen(filepath);
                    break;
                case 4:
                    inhaltAusgeben(filepath);
                    break;
                case 5:
                    System.out.println("Beenden");
                    break;
            }
        }

    }

    public static void neuerEintrag(String filepath){
        Scanner scanner = new Scanner(System.in);
        if(!Files.exists(Paths.get(filepath))){     //Nur eine Nachricht für den Nutzer
            System.out.println("Datei wurde erstellt");
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))){
            System.out.println("Neuer Eintrag: [TT.MM.JJJJ: Name]");
            String eintrag = scanner.nextLine();
            if (!correctFormat(eintrag)){           //Eingabe im richtigen Format
                throw new Exception ("Auf korrektes Format achten");
            }
            bw.write(eintrag + "\n");
        }catch(IOException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void eintragBearbeiten(String filepath){

    }

    public static void eintragLoeschen(String filepath){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welchen Eintrag löschen?");
        String toRemove = scanner.nextLine();
        String filepathCopy = "src/data_copy.txt";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepathCopy, true))){    //Reader auf Original, Writer auf Kopie
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line=br.readLine()) != null){       //Zu löschenden Eintrag überspringen
                if(line.equals(toRemove)){
                    continue;
                }
                bw.write(line + "\n");              //Eintrag in die Kopie schreiben
            }
            bw.close();                                 //IO-Objekte wieder schließen
            br.close();
            Files.delete(Paths.get(filepath));          //Original löschen
            Files.move(Paths.get(filepathCopy), Paths.get(filepath));   //Kopie umbenennen
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void inhaltAusgeben(String filepath){                 //Einträge anzeigen
        try(BufferedReader br = new BufferedReader(new FileReader(filepath))){
            String line;
            while ((line=br.readLine()) != null){
                System.out.println(line);
            }
        }catch(IOException e){
            e.getMessage();
        }
    }

    public static boolean correctFormat(@NotNull String eintrag){       //Mehrere Kontrolldurchläufe für den eingegebenen Eintrag
        if(!(Integer.valueOf(eintrag.substring(0,2)) <= 31)){
            return false;
        }
        if(!(eintrag.charAt(2) == '.')){
            return false;
        }
        if(!(Integer.valueOf(eintrag.substring(3,5)) <= 12)){
            return false;
        }
        if(!(eintrag.charAt(5) == '.')){
            return false;
        }
         if((Integer.valueOf(eintrag.substring(6,10)) > 2100) || (Integer.valueOf(eintrag.substring(6,10)) < 1900)){
            return false;
        }
        if(!(eintrag.charAt(10) == ':') && !(eintrag.charAt(11) == ' ')){
            return false;
        }
        return true;
    }
}
